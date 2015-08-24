package yukihane.dq10don.farm.presenter;

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
import yukihane.dq10don.main.presenter.MainPresenter;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmPresenter implements BaseActivityPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmPresenter.class);

    private View view;
    private DbHelper dbHelper;

    public FarmPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        this.dbHelper = dbHFactory.create();
    }

    @Override
    public void onCreate(boolean firstBoot) {
        // do nothing
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
    }

    public static class NullView implements View {
        @Override
        public void setAccounts(List<Account> accounts) {
        }
    }
}
