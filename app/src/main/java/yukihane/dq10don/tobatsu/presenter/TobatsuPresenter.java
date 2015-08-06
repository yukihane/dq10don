package yukihane.dq10don.tobatsu.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.base.presenter.BaseActivityPresenter;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.view.TobatsuPrefUtils;

/**
 * Created by yuki on 15/07/13.
 */
public class TobatsuPresenter implements BaseActivityPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuPresenter.class);
    private final DbHelper dbHelper;
    private final TobatsuPrefUtils prefUtils;
    private View view;

    public TobatsuPresenter(View view, DbHelperFactory dbHFactory, TobatsuPrefUtils prefUtils) {
        this.view = view;
        dbHelper = dbHFactory.create();
        this.prefUtils = prefUtils;
    }

    /**
     * @param firstBoot (Activityが復元されたのではなく)起動された場合にtrue.
     */
    @Override
    public void onCreate(boolean firstBoot) {
        try {
            setAlarmIfNeeded();
        } catch (SQLException e) {
            LOGGER.error("initial process error", e);
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

    private void setAlarmIfNeeded() throws SQLException {

        if (!prefUtils.isAutoPilotEnabled()) {
            return;
        }

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

    public interface View extends BaseActivityPresenter.View {

        void setAlarm(long time);

        void cancelAlarm();
    }

    private static class NullView implements View {
        @Override
        public void setAccounts(List<Account> accounts) {
        }

        @Override
        public void setAlarm(long time) {
        }

        @Override
        public void cancelAlarm() {
        }
    }
}
