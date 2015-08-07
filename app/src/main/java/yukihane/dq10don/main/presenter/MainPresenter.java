package yukihane.dq10don.main.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/08/06.
 */
public class MainPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPresenter.class);

    private static final View NULL_VIEW = new NullView();

    private View view;
    private DbHelper dbHelper;

    public MainPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        this.dbHelper = dbHFactory.create();
    }

    public void onCreate(boolean firstBoot) {
        try {
            showWelcomeDialogIfNeeded(firstBoot);
        } catch (SQLException e) {
            LOGGER.error("welcome dialog open error", e);
        }
    }

    public void onDestroy() {
        view = NULL_VIEW;
        dbHelper = null;
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
    }

    private static final class NullView implements View {
        @Override
        public void showWelcomeDialog() {
        }
    }

}
