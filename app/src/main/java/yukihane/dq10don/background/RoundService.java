package yukihane.dq10don.background;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.account.BgService;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.tobatsu.model.TobatsuService;
import yukihane.dq10don.tobatsu.model.TobatsuServiceFactory;
import yukihane.dq10don.tobatsu.view.MainActivity;

/**
 * 登録されているアカウントの討伐情報をサーバーへリクエストし、DBを更新します.
 * また, ステータスバーへの通知も行います.
 */
public class RoundService extends IntentService {

    public static final String KEY_WEBPCNO = "webPcNo";
    public static final String KEY_POINT = "tobatsu_point";
    public static final String KEY_TEXT = "tobatsu_text";
    public static final String KEY_RETRY = "tobatsu_retry";
    public static final int NOTIFICATION_ID = 1;
    private static final int MAX_RETRY = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundService.class);

    public RoundService() {
        super("yukihane.dq10don.background.RoundService");
    }

    private static String getText(TobatsuItem item) {
        return item.getMonsterName() + "[" + item.getCount() + "匹]" + "@" + item.getArea();
    }

    @Override
    public void onCreate() {
        LOGGER.debug("RoundService#onCreate called");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LOGGER.debug("RoundService#onDestroy called");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            exec(intent);
        } finally {
            Alarm.completeWakefulIntent(intent);
        }
    }

    private void exec(Intent intent) {
        LOGGER.info("onHandleIntent called");

        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelperFactory(this).create();
            Result res = executeInternal(dbHelper, intent);
            if (res.getRemains().isEmpty() && res.getMaxPoint() > 0) {
                sendNotification(res.getMaxPoint(), res.getText());
                setNextDateAlarm(dbHelper);
            } else if (!res.getRemains().isEmpty()) {
                int retry = intent.getIntExtra(KEY_RETRY, 0);
                if (retry < MAX_RETRY) {
                    intent.putExtra(KEY_RETRY, retry + 1);
                    intent.putExtra(KEY_POINT, res.getMaxPoint());
                    intent.putExtra(KEY_TEXT, res.getText());
                    intent.putStringArrayListExtra(KEY_WEBPCNO, res.getRemains());
                    setRetryAlarm(intent);
                } else {
                    sendNotification(res.getMaxPoint(), "取得に失敗したキャラクターがあります");
                }
            }
            LOGGER.debug("end process");
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
        TobatsuService service = new TobatsuServiceFactory().getService(dbHelper);
        ArrayList<String> webPcNos = intent.getStringArrayListExtra(KEY_WEBPCNO);

        List<String> remains = new ArrayList<>();

        int maxPoint = intent.getIntExtra(KEY_POINT, 0);
        String text = intent.getStringExtra(KEY_TEXT);
        if (webPcNos == null) {
            // webPcNosが設定されていない場合は, 初回要求であり、
            // 全キャラクター分の討伐リストを取得する
            Map<Character, TobatsuList> lists = service.getTobatsuListsFromServer();

            Set<Character> cs = lists.keySet();
            for (Character c : cs) {
                TobatsuList l = lists.get(c);
                if (l == null) {
                    remains.add(Long.toString(c.getWebPcNo()));
                } else {
                    for (TobatsuItem i : l.getListItems()) {
                        if (i.getPoint() > maxPoint) {
                            maxPoint = i.getPoint();
                            text = getText(i);
                        }
                    }
                }
            }
        } else {
            for (String no : webPcNos) {
                long webPcNo = Long.parseLong(no);
                try {
                    TobatsuList list = service.getTobatsuListFromServer(webPcNo);
                    for (TobatsuItem ti : list.getListItems()) {
                        if (ti.getPoint() > maxPoint) {
                            maxPoint = ti.getPoint();
                            text = getText(ti);
                        }
                    }
                } catch (AppException e) {
                    remains.add(no);
                    LOGGER.error("TobatsuList request error: ", e);
                }
            }
        }

        return new Result(maxPoint, text, remains);
    }

    private void setNextDateAlarm(DbHelper dbHelper) throws SQLException {
        BgServiceDao dao = BgServiceDao.create(dbHelper);
        BgService srv = dao.get();
        Alarm.setAlarm(getApplication(), srv.getNextAlarmTime());
    }

    public void setRetryAlarm(Intent intent) {
        long now = Calendar.getInstance().getTimeInMillis();
        int offset = (10 + new Random(now).nextInt(10)) * 1000;
        Alarm.setAlarm(getApplication(), now + offset, intent);
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(int maxPoint, String msg) {
        String title = "" + maxPoint + "Pの討伐依頼があります";

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private static class Result {

        private final List<String> remains;

        @Getter
        @Setter
        private int maxPoint;

        @Getter
        @Setter
        private String text;

        Result(int maxPoint, String text, List<String> remains) {
            this.maxPoint = maxPoint;
            this.text = text;
            this.remains = new ArrayList<>(remains);
        }

        public void addWebPcNo(String webPcNo) {
            remains.add(webPcNo);
        }

        public ArrayList<String> getRemains() {
            return new ArrayList<>(remains);
        }
    }
}
