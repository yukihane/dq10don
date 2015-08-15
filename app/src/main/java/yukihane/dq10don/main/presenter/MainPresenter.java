package yukihane.dq10don.main.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tos.model.TosPrefUtils;

/**
 * Created by yuki on 15/08/06.
 */
public class MainPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPresenter.class);

    private static final View NULL_VIEW = new NullView();

    private View view;
    private DbHelper dbHelper;
    private TosPrefUtils prefUtils;

    public MainPresenter(View view, DbHelperFactory dbHFactory, TosPrefUtils prefUtils) {
        this.view = view;
        this.dbHelper = dbHFactory.create();
        this.prefUtils = prefUtils;
    }

    public void onCreate(boolean firstBoot) {

        if (notYetAgreed()) {
            if (firstBoot) {
                view.startTos();
            } else {
                LOGGER.info("disagreed, exit");
                view.endApplication();
            }
        } else {

            try {
                showWelcomeDialogIfNeeded();
            } catch (SQLException e) {
                LOGGER.error("welcome dialog open error", e);
            }
        }
    }

    private boolean notYetAgreed() {
        return prefUtils.getAgreedVersion() < prefUtils.getCurrentVersion();
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        view = NULL_VIEW;
        dbHelper = null;
        prefUtils = null;
    }


    private void showWelcomeDialogIfNeeded() throws SQLException {
        AccountDao dao = AccountDao.create(dbHelper);
        if (!dao.exists()) {
            view.showWelcomeDialog();
        }
    }

    public interface View {
        void showWelcomeDialog();

        void startTos();

        void endApplication();
    }

    private static final class NullView implements View {
        @Override
        public void showWelcomeDialog() {
        }

        @Override
        public void startTos() {
        }

        @Override
        public void endApplication() {
        }
    }

}
