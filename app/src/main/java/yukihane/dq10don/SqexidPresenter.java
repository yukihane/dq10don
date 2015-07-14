package yukihane.dq10don;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;

/**
 * Created by yuki on 15/07/14.
 */
public class SqexidPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqexidPresenter.class);

    private final View view;

    public SqexidPresenter(View view) {
        this.view = view;
    }

    public void onCreate() {
        // TODO DBのsqexアカウント情報を取得して表示

        Observable observable = Observable.create(new Observable.OnSubscribe<List<Account>>() {
            @Override
            public void call(Subscriber<? super List<Account>> subscriber) {
                subscriber.onStart();
                List<Account> results = new ArrayList<>();
                // TODO DBロード
                for (int i = 0; i < 10; i++) {
                    Account a = new Account("sqexid" + i, "sessionid" + i, new ArrayList<Character>(0));
                    results.add(a);
                }
                subscriber.onNext(results);
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

    public interface View {
        void bindToList(Observable observable);

        void displayAccount(List<Account> accounts);
    }
}
