package yukihane.dq10don.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import yukihane.dq10don.Application;


/**
 * https://developer.android.com/training/scheduling/alarms.html を参考に作成.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmReceiver.class);

    /**
     * @param context アプリケーションコンテキスト.
     */
    public static void setAlarm(Context context, long timeInMillis) {
        LOGGER.info("setAlarm called");

        PendingIntent alarmIntent = getPendingIntent(context);

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

    public static void cancelAlarm(Context context) {
        LOGGER.info("cancelAlarm called");

        PendingIntent alarmIntent = getPendingIntent(context);
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

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("onReceive called");
        ComponentName component = new ComponentName(context, RoundService.class);
        intent.setComponent(component);

        startWakefulService(context, intent);
    }
}
