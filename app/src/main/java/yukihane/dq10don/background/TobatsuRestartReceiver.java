package yukihane.dq10don.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * アラームを自動再設定するためのレシーバー
 */
public class TobatsuRestartReceiver extends BroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuRestartReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("onReceive called {}", intent.getAction());
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            DbHelper dbHelper = null;
            try {
                dbHelper = new DbHelperFactory(context).create();
                BgServiceDao dao = BgServiceDao.create(dbHelper);
                TobatsuAlarm.setAlarm(context.getApplicationContext(), dao.get().getNextAlarmTime());
            } catch (SQLException e) {
                LOGGER.error("DB error", e);
            } finally {
                OpenHelperManager.releaseHelper();
            }
        }
    }
}
