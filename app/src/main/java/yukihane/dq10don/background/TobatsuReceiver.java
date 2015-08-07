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
public class TobatsuReceiver extends WakefulBroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("onReceive called");
        ComponentName component = new ComponentName(context, TobatsuRoundService.class);
        intent.setComponent(component);

        startWakefulService(context, intent);
    }

}
