package yukihane.dq10don.bosscard.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

public class BossCardPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardPresenter.class);

    private View view;
    private DbHelper dbHelper;

    public BossCardPresenter(View view, DbHelperFactory dbHelperFactory) {
        this.view = view;
        this.dbHelper = dbHelperFactory.create();
    }

    /**
     * @param firstBoot (Activityが復元されたのではなく)起動された場合にtrue.
     */
    public void onCreate(boolean firstBoot) {
        try {
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
        view = null;
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
    }
}
