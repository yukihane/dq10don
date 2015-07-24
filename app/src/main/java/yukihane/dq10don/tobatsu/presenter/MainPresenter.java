package yukihane.dq10don.tobatsu.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import yukihane.dq10don.Utils;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

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
            showWelcomeDialogIfNeeded();
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

    private void setAlarmIfNeeded() throws SQLException {
        BgServiceDao bgDao = BgServiceDao.create(dbHelper);
        if (!bgDao.exists()) {
            // 初回起動時ならいつでも設定
            LOGGER.info("first time launch");
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
                LOGGER.info("not reset alarm");
                return;
            }
            LOGGER.debug("reset alarm");
            view.cancelAlarm();
            view.setAlarm(bgDao.get().getNextAlarmTime());

        }
    }


    private void showWelcomeDialogIfNeeded() throws SQLException {
        AccountDao dao = AccountDao.create(dbHelper);
        if (!dao.exists()) {
            view.showWelcomeDialog();
        }
    }

    public void exportLog(File fromDir, File toDir) {

        // TODO 本当は非同期でやったほうがいい
        File[] logfiles = fromDir.listFiles((File dir, String filename) -> {
            if (filename.endsWith("log") || filename.endsWith("log.txt")) {
                return true;
            }
            return false;
        });
        LOGGER.info("export log file(s): from:{} to:{}", logfiles, toDir);

        for (File f : logfiles) {
            try {
                Utils.copy(f, toDir);
            } catch (IOException e) {
                LOGGER.error("file copy failed", e);
            }
        }
    }

    public interface View {
        void setAccounts(List<Account> accounts);

        void setAlarm(long time);

        void cancelAlarm();

        void showWelcomeDialog();
    }
}
