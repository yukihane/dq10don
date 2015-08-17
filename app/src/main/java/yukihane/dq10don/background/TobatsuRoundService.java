package yukihane.dq10don.background;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import yukihane.dq10don.R;
import yukihane.dq10don.Utils;
import yukihane.dq10don.account.BgService;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.db.TobatsuListDao;
import yukihane.dq10don.settings.view.TwitterPrefUtils;
import yukihane.dq10don.tobatsu.model.TobatsuListService;
import yukihane.dq10don.tobatsu.model.TobatsuListServiceFactory;
import yukihane.dq10don.tobatsu.view.TobatsuActivity;

/**
 * 登録されているアカウントの討伐情報をサーバーへリクエストし、DBを更新します.
 * また, ステータスバーへの通知も行います.
 */
public class TobatsuRoundService extends IntentService {

    public static final String KEY_WEBPCNO = "webPcNo";
    public static final String KEY_POINT = "tobatsu_point";
    public static final String KEY_TEXT = "tobatsu_text";
    public static final String KEY_RETRY = "tobatsu_retry";
    public static final String KEY_EXISTS_INVALID = "tobatsu_invalid";
    public static final String KEY_ISSUED_DATE = "tobatsu_issued_date";
    private static final int MAX_RETRY = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuRoundService.class);

    public TobatsuRoundService() {
        super("yukihane.dq10don.background.TobatsuRoundService");
    }

    private String getText(TobatsuItem item) {
        return getString(R.string.text_notification,
                item.getMonsterName(), item.getCount(), item.getArea());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LOGGER.info("tobatsu round service start");
        try {
            exec(intent);
        } finally {
            TobatsuReceiver.completeWakefulIntent(intent);
        }
    }

    private void exec(Intent intent) {

        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelperFactory(this).create();
            Result res = executeInternal(dbHelper, intent);
            final int retry = intent.getIntExtra(KEY_RETRY, 0);
            if (!res.getRemains().isEmpty() && retry < MAX_RETRY) {
                // リトライ回数の上限に達しておらず、かつ未取得のものがある場合
                // リトライを行う
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_RETRY, retry + 1);
                bundle.putInt(KEY_POINT, res.getMaxPoint());
                bundle.putString(KEY_TEXT, res.getText());
                bundle.putStringArrayList(KEY_WEBPCNO, res.getRemains());
                setRetryAlarm(bundle);
                return;
            } else if (res.getRemains().isEmpty() && res.getMaxPoint() > 0 && !res.existsInvalid) {
                // 全要求が全て正常終了している
                sendNotification(res.getMaxPoint(), res.getText());
            } else {
                if (res.getRemains().isEmpty() && !res.existsInvalid) {
                    LOGGER.info("取得対象がありません");
                } else {
                    sendNotification(res.getMaxPoint(),
                            getString(R.string.text_notification_error));
                }
            }
            LOGGER.debug("end process");
            setNextDateAlarm(dbHelper);
            sendTwitterIfNeeded(dbHelper, res);
        } catch (SQLException e) {
            LOGGER.error("DB error", e);
        } finally {
            if (dbHelper != null) {
                OpenHelperManager.releaseHelper();
                dbHelper = null;
            }
        }
    }

    private Result executeInternal(DbHelper dbHelper, Intent intent) {
        TobatsuListService service = new TobatsuListServiceFactory().getService(dbHelper);
        ArrayList<String> webPcNos = intent.getStringArrayListExtra(KEY_WEBPCNO);

        List<String> remains = new ArrayList<>();

        int maxPoint = intent.getIntExtra(KEY_POINT, 0);
        String text = intent.getStringExtra(KEY_TEXT);
        String issuedDate = intent.getStringExtra(KEY_ISSUED_DATE);
        boolean existsInvalid = intent.getBooleanExtra(KEY_EXISTS_INVALID, false);
        if (webPcNos == null) {
            try {
                // webPcNosが設定されていない場合は, 初回要求であり、
                // 全キャラクター分の討伐リストを取得する
                Map<Character, TobatsuList> lists = service.getContentsFromServer();

                Set<Character> cs = lists.keySet();
                for (Character c : cs) {
                    TobatsuList l = lists.get(c);
                    if (l == null) {
                        if (c.isTobatsuInvalid()) {
                            existsInvalid = true;
                        } else {
                            remains.add(Long.toString(c.getWebPcNo()));
                        }
                    } else {
                        for (TobatsuItem i : l.getListItems()) {
                            if (i.getPoint() > maxPoint) {
                                maxPoint = i.getPoint();
                                text = getText(i);
                                issuedDate = l.getIssuedDate();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("初回要求で失敗したため何も行われません", e);
            }
        } else {
            for (String no : webPcNos) {
                long webPcNo = Long.parseLong(no);
                Character c = null;
                try {
                    AccountDao accountDao = AccountDao.create(dbHelper);
                    c = accountDao.findCharacterByWebPcNo(webPcNo);
                    TobatsuList l = service.getContentFromServer(webPcNo);
                    for (TobatsuItem ti : l.getListItems()) {
                        if (ti.getPoint() > maxPoint) {
                            maxPoint = ti.getPoint();
                            text = getText(ti);
                            issuedDate = l.getIssuedDate();
                        }
                    }
                } catch (Exception e) {
                    if (c != null && c.isTobatsuInvalid()) {
                        existsInvalid = true;
                    } else {
                        remains.add(no);
                    }
                    LOGGER.error("TobatsuList request error: ", e);
                }
            }
        }

        return new Result(maxPoint, text, issuedDate, remains, existsInvalid);
    }

    private void setNextDateAlarm(DbHelper dbHelper) throws SQLException {
        BgServiceDao dao = BgServiceDao.create(dbHelper);
        BgService srv = dao.get();
        TobatsuAlarm.setAlarm(getApplication(), srv.getNextAlarmTime());
    }

    public void setRetryAlarm(Bundle bundle) {
        long now = Calendar.getInstance().getTimeInMillis();
        int offset = (10 + new Random(now).nextInt(15)) * 1000;
        TobatsuAlarm.setAlarm(getApplication(), now + offset, bundle);
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(int maxPoint, String msg) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        String title = getString(R.string.text_notification_header, nf.format(maxPoint));

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, TobatsuActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notify);
        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setTicker(title);
        builder.setAutoCancel(true);

        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(Utils.TOBATSU_NOTIFICATION_ID, builder.build());
    }

    private void sendTwitterIfNeeded(DbHelper dbHelper, Result res) throws SQLException {
        TwitterPrefUtils twitterPrefUtils = new TwitterPrefUtils(this);

        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return;
        }
        if (!twitterPrefUtils.getTweetTobatsu()) {
            // tweetしない設定になっている
            return;
        }
        if (res.getMaxPoint() <= 0 || res.getIssuedDate() == null) {
            // 今回取得できていない
            LOGGER.info("no tweet for tobatsu request error: {}", res);
            return;
        }

        final Collection<Long> charas;
        if (twitterPrefUtils.isTweetTobatsuAllChara()) {
            charas = null;
        } else {
            charas = twitterPrefUtils.getTobatsuTweetCharacters();
            if (charas.isEmpty()) {
                // 1人もtweet対象になっていない
                return;
            }
        }

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthAccessToken(accessToken);

        TobatsuListDao dao = TobatsuListDao.create(dbHelper);
        TobatsuItem maxItem = dao.max(res.getIssuedDate(), charas);

        try {
            String maxPoint = NumberFormat.getNumberInstance().format(maxItem.getPoint());
            twitter.updateStatus("#本日の最高額討伐依頼 #dq10\n"
                    + maxPoint + " P\n"
                    + maxItem.getArea() + " "
                    + maxItem.getMonsterName() + " "
                    + maxItem.getCount() + "匹");
            LOGGER.debug("tweet succeeded");
        } catch (TwitterException e) {
            LOGGER.error("tweet failed", e);
        }
    }

    public AccessToken getAccessToken() {
        TwitterPrefUtils pu = new TwitterPrefUtils(this);
        long userId = pu.getUserId();
        String token = pu.getToken();
        String tokenSecret = pu.getTokenSecret();
        if (userId < 0 || token.isEmpty() || tokenSecret.isEmpty()) {
            return null;
        }
        return new AccessToken(token, tokenSecret, userId);
    }

    private static class Result {

        private final List<String> remains;

        @Getter
        @Setter
        private int maxPoint;

        @Getter
        @Setter
        private String text;

        @Getter
        @Setter
        private String issuedDate;

        @Getter
        @Setter
        private boolean existsInvalid;

        Result(int maxPoint, String text, String issuedDate, List<String> remains, boolean existsInvalid) {
            this.maxPoint = maxPoint;
            this.text = text;
            this.issuedDate = issuedDate;
            this.remains = new ArrayList<>(remains);
            this.existsInvalid = existsInvalid;
        }

        public void addWebPcNo(String webPcNo) {
            remains.add(webPcNo);
        }

        public ArrayList<String> getRemains() {
            return new ArrayList<>(remains);
        }

        @Override
        public String toString() {
            return "Result{" +
                    "remains=" + remains +
                    ", maxPoint=" + maxPoint +
                    ", text='" + text + '\'' +
                    ", issuedDate='" + issuedDate + '\'' +
                    ", existsInvalid=" + existsInvalid +
                    '}';
        }
    }
}
