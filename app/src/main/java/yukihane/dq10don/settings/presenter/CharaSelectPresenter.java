package yukihane.dq10don.settings.presenter;


import com.j256.ormlite.dao.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.twitter.view.PrefUtils;

public class CharaSelectPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharaSelectPresenter.class);

    private DbHelper dbHelper;
    private PrefUtils prefUtils;
    private View view;

    public CharaSelectPresenter(View view, DbHelperFactory dbHelperFactory, PrefUtils prefUtils) {
        this.view = view;
        this.dbHelper = dbHelperFactory.create();
        this.prefUtils = prefUtils;
    }

    public void onCreate() {

        Observable<List<Character>> observable
                = Observable.create((Subscriber<? super List<Character>> subscriber) -> {
            subscriber.onStart();
            try {
                Dao<Character, Long> characterDao = dbHelper.getDao(Character.class);
                List<Character> res = characterDao.queryForAll();
                subscriber.onNext(res);
                subscriber.onCompleted();
            } catch (SQLException e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io());

        view.bind(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Character>>() {
            @Override
            public void call(List<Character> characters) {
                Collection<Long> checked = prefUtils.getTobatsuTweetCharacters();
                List<CheckableCharacter> checkableCharacters = new ArrayList<>(characters.size());
                for (Character c : characters) {
                    CheckableCharacter cc = new CheckableCharacter(c, checked.contains(c.getWebPcNo()));
                    checkableCharacters.add(cc);
                }

                view.setData(checkableCharacters);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LOGGER.error("character load error", throwable);
            }
        });
    }

    public void onCheckChange(int position, boolean checked) {
    }

    public interface View {
        void bind(Observable<?> observable);

        void setData(List<CheckableCharacter> checkableCharacters);
    }
}