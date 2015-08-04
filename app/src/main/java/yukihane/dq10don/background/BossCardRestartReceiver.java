package yukihane.dq10don.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.settings.view.BossCardPrefUtils;

/**
 * Created by yuki on 15/08/03.
 */
public class BossCardRestartReceiver extends BroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardRestartReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("onReceive called {}", intent.getAction());

        BossCardPrefUtils prefUtils = new BossCardPrefUtils(context);
        if (!prefUtils.isAutoPilotEnabled()) {
            return;
        }

        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            BossCardAlarm.set(context.getApplicationContext());
        }
    }
}
