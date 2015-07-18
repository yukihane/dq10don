package yukihane.dq10don.tobatsu.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDataList;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuPresenter.class);

    /**
     * このインスタンスが表示対象としているキャラクター
     */
    private final CharacterDto character;

    private View view = null;
    private DbHelper dbHelper = null;

    public TobatsuPresenter(View view, DbHelperFactory dbHFactory, CharacterDto character) {
        this.view = view;
        this.dbHelper = dbHFactory.create();
        this.character = character;
    }

    public void onViewCreated() {
        view.setHeader(character.getSqexid(), character.getSmileUniqNo());
    }

    public void onUpdateClick() {

        Observable observable = Observable.create(new Observable.OnSubscribe<TobatsuDto>() {
            @Override
            public void call(Subscriber<? super TobatsuDto> subscriber) {
                subscriber.onStart();
                if (character == null) {
                    LOGGER.error("need login");
                    subscriber.onError(new NullPointerException("need login"));
                } else {
                    String sessionId = character.getSessionId();
                    LOGGER.info("update target character: {}", character);
                    HappyService service = HappyServiceFactory.getService(sessionId);
                    service.characterSelect(character.getWebPcNo());
                    TobatsuDto res = service.getTobatsuList();
                    subscriber.onNext(res);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

        view.bindToList(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TobatsuDto>() {

            private final yukihane.dq10don.account.TobatsuList list = new yukihane.dq10don.account.TobatsuList();

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("onError", e);
            }

            @Override
            public void onNext(TobatsuDto dto) {
                LOGGER.info("onNext");

                LOGGER.info("getCountryTobatsuDataList size: {}", dto.getCountryTobatsuDataList().size());
                for (TobatsuDataList data : dto.getCountryTobatsuDataList()) {
                    LOGGER.info("getTobatsuList size: {}", data.getTobatsuList().size());
                    for (yukihane.dq10don.communication.dto.tobatsu.TobatsuList tl : data.getTobatsuList()) {
                        LOGGER.info("monster: {}", tl.getMonsterName());
                        TobatsuItem item = new TobatsuItem(tl.getMonsterName(),
                                tl.getArea() + "," + tl.getCount(), tl.getPoint());
                        list.addListItem(item);
                    }
                }

                if (view != null) {
                    view.tobatsuListUpdate(list);
                }
            }
        });
    }

    public void onDestroyView() {
        view = null;
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }


    public interface View {
        void bindToList(Observable observable);

        void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list);

        void setHeader(String sqexid, String smileUniqNo);
    }
}
