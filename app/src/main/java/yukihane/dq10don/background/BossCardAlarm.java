package yukihane.dq10don.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Created by yuki on 15/08/03.
 */
public class BossCardAlarm {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardAlarm.class);

    public static void set(Context context) {
        PendingIntent alarmIntent = getPendingIntent(context);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 初回は5分後, 以後半日(12時間)ごとにチェックする.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 5);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_HALF_DAY, alarmIntent);
        LOGGER.info("BossCardAlarm set {}", cal.getTime());

        setBootReceivable(context, true);
    }

    /**
     * 設定されていないのなら設定します.
     * 既に設定が有れば何もしません.
     */
    public static void setIfNothing(Context context) {
        PendingIntent intent = getPendingIntent(context, false);
        if (intent != null) {
            // 既に設定してある
            LOGGER.debug("alarm already exists");
            return;
        }

        set(context);
    }


    public static void cancel(Context context) {
        PendingIntent alarmIntent = getPendingIntent(context);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmMgr.cancel(alarmIntent);
        LOGGER.info("BossCardAlarm cancelled");

        // 起動時に自動的にアラームをセットしない
        setBootReceivable(context, false);
    }

    private static void setBootReceivable(Context context, boolean enabled) {

        // 起動時にアラームをセットできるようにする
        ComponentName receiver = new ComponentName(context, BossCardRestartReceiver.class);
        PackageManager pm = context.getPackageManager();

        int status = (enabled)
                ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

        pm.setComponentEnabledSetting(receiver, status, PackageManager.DONT_KILL_APP);

    }

    private static PendingIntent getPendingIntent(Context context) {
        return getPendingIntent(context, true);
    }

    private static PendingIntent getPendingIntent(Context context, boolean update) {
        Intent intent = new Intent(context, BossCardReceiver.class);

        int flag = update ? PendingIntent.FLAG_UPDATE_CURRENT : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(context, 0, intent, flag);
    }
}
