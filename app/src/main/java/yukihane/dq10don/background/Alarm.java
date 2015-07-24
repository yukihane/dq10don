package yukihane.dq10don.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * https://developer.android.com/training/scheduling/alarms.html を参考に作成.
 */
public class Alarm extends WakefulBroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Alarm.class);


    /**
     * @param context アプリケーションコンテキスト.
     */
    public static void setAlarm(Context context, long timeInMillis, Bundle bundle) {

        PendingIntent alarmIntent = getPendingIntent(context, bundle);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, timeInMillis, alarmIntent);
        Date date = new Date(timeInMillis);
        LOGGER.info("Alarm set {}", date);

        // 起動時にアラームをセットできるようにする
        ComponentName receiver = new ComponentName(context, AutoSetReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
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
        LOGGER.info("Alarm cancelled");


        // 起動時に自動的にアラームをセットしない
        ComponentName receiver = new ComponentName(context, AutoSetReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private static PendingIntent getPendingIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, Alarm.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("onReceive called");
        ComponentName component = new ComponentName(context, RoundService.class);
        intent.setComponent(component);

        startWakefulService(context, intent);
    }

}
