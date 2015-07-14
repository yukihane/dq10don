package yukihane.dq10don;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

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
        // TODO DBのsqexアカウント情報を取得して表示

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
                LOGGER.info("onCompleted");
                view.displayAccount(results);
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("onError", e);
            }

            @Override
            public void onNext(List<Account> list) {
                LOGGER.info("onNext");
                results.addAll(list);
            }
        });

    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
    }

    public interface View {
        void bindToList(Observable observable);

        void displayAccount(List<Account> accounts);
    }
}
