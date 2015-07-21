package yukihane.dq10don.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アラームを自動再設定するためのレシーバー
 */
public class AutoSetReceiver extends BroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoSetReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("onReceive called {}", intent.getAction());
    }
}
