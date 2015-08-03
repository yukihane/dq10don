package yukihane.dq10don.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by yuki on 15/08/03.
 */
public class TobatsuAlarm {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuAlarm.class);

    /**
     * @param context アプリケーションコンテキスト.
     */
    public static void setAlarm(Context context, long timeInMillis, Bundle bundle) {

        PendingIntent alarmIntent = getPendingIntent(context, bundle);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, timeInMillis, alarmIntent);
        Date date = new Date(timeInMillis);
        LOGGER.info("TobatsuReceiver set {}", date);

        // 起動時にアラームをセットできるようにする
        setBootReceivable(context, true);
    }

    /**
     * @param context アプリケーションコンテキスト.
     */
    public static void setAlarm(Context context, long timeInMillis) {
        setAlarm(context, timeInMillis, null);
    }

    public static void cancelAlarm(Context context) {

        PendingIntent alarmIntent = getPendingIntent(context, null);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmIntent);
        LOGGER.info("TobatsuReceiver cancelled");

        // 起動時に自動的にアラームをセットしない
        setBootReceivable(context, false);
    }

    private static PendingIntent getPendingIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, TobatsuReceiver.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static void setBootReceivable(Context context, boolean enabled) {

        ComponentName receiver = new ComponentName(context, TobatsuRestartReceiver.class);
        PackageManager pm = context.getPackageManager();

        int status = enabled
                ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

        pm.setComponentEnabledSetting(receiver, status, PackageManager.DONT_KILL_APP);
    }
}
