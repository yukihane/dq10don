package yukihane.dq10don.background;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuki on 15/08/03.
 */
public class BossCardReceiver extends WakefulBroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("onReceive called");
        ComponentName component = new ComponentName(context, BossCardRoundService.class);
        intent.setComponent(component);

        startWakefulService(context, intent);

    }
}
