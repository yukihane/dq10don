package yukihane.dq10don.tobatsu.presenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.communication.dto.login.LoginDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

import static yukihane.dq10don.Utils.RESULTCODE_OK;

/**
 * Created by yuki on 15/07/13.
 */
public class MainPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPresenter.class);
    private final DbHelper dbHelper;
    private View view;


    public MainPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        dbHelper = dbHFactory.create();

    }

    public void onCreate() {

        try {
            setAlarmIfNeeded();
            AccountDao dao = AccountDao.create(dbHelper);
            List<Account> accounts = dao.queryAll();
            view.setAccounts(accounts);
        } catch (SQLException e) {
            LOGGER.error("account load error", e);
        }
    }

    private void setAlarmIfNeeded() throws SQLException {
        BgServiceDao bgDao = BgServiceDao.create(dbHelper);
        if (!bgDao.exists()) {
            // 初回起動時ならいつでも設定
            view.setAlarm(bgDao.get().getNextAlarmTime());
        } else {
            // きわどい時刻(5:50-6:59)でなければ再設定する
            // (もしかしたらアラームが解除されているかもしれないので).
            // 5:55 以降だとアラームは翌日にセットされてしまう.
            // 6時になったばかりだと今日の処理を実行中の可能性がある.
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN);
            int h = cal.get(Calendar.HOUR_OF_DAY);
            int m = cal.get(Calendar.MINUTE);
            if (h == 6 || (h == 5 && m >= 50)) {
                // きわどい時間なのでアラーム再セットしない
                return;
            }
            view.cancelAlarm();
            view.setAlarm(bgDao.get().getNextAlarmTime());

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
            LOGGER.error("login failed: {}", dto.getResultCode());
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

        void setAlarm(long time);

        void cancelAlarm();
    }
}
