package yukihane.dq10don.background;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.bosscard.model.BossCardListService;
import yukihane.dq10don.bosscard.model.BossCardListServiceFactory;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/08/03.
 */
public class BossCardRoundService extends IntentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardRoundService.class);

    public BossCardRoundService() {
        super("BossCardRoundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            exec(intent);
        } finally {
            BossCardReceiver.completeWakefulIntent(intent);
        }
    }

    private void exec(Intent intent) {
        DbHelper dbHelper = null;

        try {
            dbHelper = new DbHelperFactory(this).create();
            BossCardListService service = new BossCardListServiceFactory().getService(dbHelper);
            service.getTobatsuListsFromServer();
        } catch (SQLException e) {
            LOGGER.error("bosscardroundservice error", e);
        } finally {
            if (dbHelper != null) {
                OpenHelperManager.releaseHelper();
            }
        }
    }
}
