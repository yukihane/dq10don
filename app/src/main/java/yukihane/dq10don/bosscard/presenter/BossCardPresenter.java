package yukihane.dq10don.bosscard.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.base.presenter.BaseActivityPresenter;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.view.BossCardPrefUtils;

public class BossCardPresenter implements BaseActivityPresenter {

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
    @Override
    public void onCreate(boolean firstBoot) {
        if (prefUtils.isAutoPilotEnabled()) {
            view.setAlarmIfNothing();
        }
    }

    @Override
    public void onStart() {
        try {
            AccountDao dao = AccountDao.create(dbHelper);
            List<Account> accounts = dao.queryAll();
            view.setAccounts(accounts);
        } catch (SQLException e) {
            LOGGER.error("account load error", e);
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        view = new NullView();
    }

    public interface View extends BaseActivityPresenter.View {
        void setAlarmIfNothing();
    }

    private static class NullView implements View {
        @Override
        public void setAccounts(List<Account> accounts) {
        }

        @Override
        public void setAlarmIfNothing() {
        }
    }
}
