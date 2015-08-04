package yukihane.dq10don.background;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.R;
import yukihane.dq10don.Utils;
import yukihane.dq10don.bosscard.model.BossCardListService;
import yukihane.dq10don.bosscard.model.BossCardListServiceFactory;
import yukihane.dq10don.bosscard.view.BossCardActivity;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.db.StorageDao;
import yukihane.dq10don.settings.view.BossCardPrefUtils;

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
            notifyIfNeed(dbHelper);

        } catch (SQLException e) {
            LOGGER.error("bosscardroundservice error", e);
        } finally {
            if (dbHelper != null) {
                OpenHelperManager.releaseHelper();
            }
        }
    }

    private void notifyIfNeed(DbHelper dbHelper) throws SQLException {
        BossCardPrefUtils prefUtils = new BossCardPrefUtils(this);

        if (!prefUtils.isNotification()) {
            return;
        }
        int leftTimeLimit = prefUtils.getLeftTimeLimit();
        int leftMinitesLimit = leftTimeLimit * 24 * 60;
        StorageDao dao = StorageDao.create(dbHelper);
        boolean exists = dao.existsLimitLessThan(leftMinitesLimit);
        if (exists) {
            sendNotification();
        }
    }

    private void sendNotification() {
        String title = getString(R.string.title_activity_boss_card);
        String msg = getString(R.string.text_exits_limit_items);

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, BossCardActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notify)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(Utils.BOSS_CARD_NOTIFICATION_ID, mBuilder.build());
    }
}
