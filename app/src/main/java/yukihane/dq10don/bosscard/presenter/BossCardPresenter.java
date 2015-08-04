package yukihane.dq10don.bosscard.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.background.BossCardAlarm;
import yukihane.dq10don.bosscard.view.BossCard;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.view.BossCardPrefUtils;

public class BossCardPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardPresenter.class);

    private View view;
    private DbHelper dbHelper;
    private BossCardPrefUtils prefUtils;

    public BossCardPresenter(View view, DbHelperFactory dbHelperFactory, BossCardPrefUtils prefUtils) {
        this.view = view;
        this.dbHelper = dbHelperFactory.create();
        this.prefUtils = prefUtils;
    }

    /**
     * @param firstBoot (Activityが復元されたのではなく)起動された場合にtrue.
     */
    public void onCreate(boolean firstBoot) {
        try {
            if (prefUtils.isAutoPilotEnabled()) {
                view.setAlarmIfNothing();
            }
            showWelcomeDialogIfNeeded(firstBoot);
        } catch (SQLException e) {
            LOGGER.error("initial process error", e);
        }
    }

    public void onStart() {
        try {
            AccountDao dao = AccountDao.create(dbHelper);
            List<Account> accounts = dao.queryAll();
            view.setAccounts(accounts);
        } catch (SQLException e) {
            LOGGER.error("account load error", e);
        }
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        view = new NullView();
    }

    private void showWelcomeDialogIfNeeded(boolean boot) throws SQLException {
        if (!boot) {
            return;
        }

        AccountDao dao = AccountDao.create(dbHelper);
        if (!dao.exists()) {
            view.showWelcomeDialog();
        }
    }

    public interface View {

        void showWelcomeDialog();

        void setAccounts(List<Account> accounts);

        void setAlarmIfNothing();
    }

    private static class NullView implements View {
        @Override
        public void showWelcomeDialog() {
        }

        @Override
        public void setAccounts(List<Account> accounts) {
        }

        @Override
        public void setAlarmIfNothing() {
        }
    }
}
