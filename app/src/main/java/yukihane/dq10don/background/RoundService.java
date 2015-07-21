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
import java.util.Map;

import yukihane.dq10don.account.BgService;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
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
    public static final String KEY_CONDITION = "tobatsu_conditon";
    public static final int NOTIFICATION_ID = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundService.class);

    public RoundService() {
        super("yukihane.dq10don.background.RoundService");
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
            executeInternal(dbHelper, intent);
            setNexAlarm(dbHelper);
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

    private void executeInternal(DbHelper dbHelper, Intent intent) {
        TobatsuService service = new TobatsuServiceFactory().getService(dbHelper);
        ArrayList<CharSequence> webPcNos = intent.getCharSequenceArrayListExtra(KEY_WEBPCNO);

        if (webPcNos == null) {
            // webPcNosが設定されていない場合は, 初回要求であり、
            // 全キャラクター分の討伐リストを取得する
            Map<Character, TobatsuList> lists = service.getTobatsuListsFromServer();
            sendNotification("上");
        } else {
            for (CharSequence no : webPcNos) {
                long webPcNo = Long.parseLong(no.toString());
                service.getTobatsuListFromServer(webPcNo);
            }
            sendNotification("下");
        }
    }

    private void setNexAlarm(DbHelper dbHelper) throws SQLException {
        BgServiceDao dao = BgServiceDao.create(dbHelper);
        BgService srv = dao.get();
        Alarm.setAlarm(getApplication(), srv.getNextAlarmTime());
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("丼")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("test1"))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
