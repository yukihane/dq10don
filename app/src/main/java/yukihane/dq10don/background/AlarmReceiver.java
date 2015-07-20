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

import yukihane.dq10don.Application;


/**
 * https://developer.android.com/training/scheduling/alarms.html を参考に作成.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmReceiver.class);

    /**
     * @param context アプリケーションコンテキスト.
     */
    public static void setAlarm(Application application, long timeInMillis) {
        LOGGER.info("setAlarm called");
        Context context = application.getApplicationContext();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 30);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
        LOGGER.info("Alarm set");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("onReceive called");
        ComponentName component = new ComponentName(context, RoundService.class);
        intent.setComponent(component);

        startWakefulService(context, intent);
    }
}
