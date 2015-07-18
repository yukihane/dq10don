package yukihane.dq10don;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.communication.dto.login.LoginDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

import static yukihane.dq10don.Utils.RESULTCODE_OK;

/**
 * Created by yuki on 15/07/13.
 */
public class MainPresenter {

    private static final Logger logger = LoggerFactory.getLogger(MainPresenter.class);

    private View view;
    private final DbHelper dbHelper;


    public MainPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        dbHelper = dbHFactory.create();

    }

    public void onCreate() {

        try {
            AccountDao dao = AccountDao.create(dbHelper);
            List<Account> accounts = dao.queryAll();
            view.setAccounts(accounts);
        } catch (SQLException e) {
            logger.error("account load error", e);
        }
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        view = null;
    }

    public void onActivityResult(String sqexid, String json) throws IOException {

        LoginDto dto = new ObjectMapper().readValue(json, LoginDto.class);
        if (dto.getResultCode() != RESULTCODE_OK) {
            // TODO ログインが成功していない
            logger.error("login failed: {}", dto.getResultCode());
        }
        Account account = Account.from(dto, sqexid);
        try {
            AccountDao dao = AccountDao.create(dbHelper);
            dao.persist(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public interface View {
        void setAccounts(List<Account> accounts);
    }
}
