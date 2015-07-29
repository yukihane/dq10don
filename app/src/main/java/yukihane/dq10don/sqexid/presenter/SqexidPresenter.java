package yukihane.dq10don.sqexid.presenter;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.SignUpEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.communication.dto.login.LoginDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

import static yukihane.dq10don.Utils.RESULTCODE_OK;

/**
 * Created by yuki on 15/07/14.
 */
public class SqexidPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqexidPresenter.class);

    private final View view;
    private final DbHelper dbHelper;

    public SqexidPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        this.dbHelper = dbHFactory.create();
    }

    public void onCreate() {
        loadAccounts();
    }

    private void loadAccounts() {
        Observable observable = Observable.create(new Observable.OnSubscribe<List<Account>>() {
            @Override
            public void call(Subscriber<? super List<Account>> subscriber) {
                subscriber.onStart();

                try {
                    AccountDao dao = AccountDao.create(dbHelper);
                    List<Account> accounts = dao.queryAll();
                    subscriber.onNext(accounts);
                } catch (SQLException e) {
                    LOGGER.error("load account error", e);
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

        view.bindToList(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Account>>() {

            private List<Account> results = new ArrayList<Account>();

            @Override
            public void onCompleted() {
                view.displayAccount(results);
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("onError", e);
            }

            @Override
            public void onNext(List<Account> list) {
                results.addAll(list);
            }
        });
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
    }

    public void onLogin(String userId) {
        String id = (userId != null) ? userId : "";
        view.showLogin(id);
    }

    public void onRemove(String userId) {

        try {
            AccountDao dao = AccountDao.create(dbHelper);
            dao.deleteById(userId);
            loadAccounts();
        } catch (SQLException e) {
            LOGGER.error("delete error", e);
        }
    }

    public void onActivityResult(String sqexid, String json) {

        try {
            if(sqexid != null && json != null) {
                LoginDto dto = new ObjectMapper().readValue(json, LoginDto.class);
                if (dto.getResultCode() != RESULTCODE_OK) {
                    // TODO ログインが成功していない
                    LOGGER.error("login failed: {}", dto.getResultCode());
                }
                Account account = Account.from(dto, sqexid);

                AccountDao dao = AccountDao.create(dbHelper);
                dao.persist(account);

                Answers.getInstance().logSignUp(new SignUpEvent());
            } else {
                LOGGER.debug("cancelled login process");
            }

            loadAccounts();
        } catch (IOException | SQLException e) {
            LOGGER.error("login account info persist error", e);
        }
    }


    public interface View {
        void bindToList(Observable observable);

        void displayAccount(List<Account> accounts);

        void showLogin(String userId);
    }
}
