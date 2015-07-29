package yukihane.dq10don.base.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import yukihane.dq10don.DonSchedulers;
import yukihane.dq10don.base.model.BaseService;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

public abstract class BasePresenter<T, S extends BaseService<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);

    /**
     * このインスタンスが表示対象としているキャラクター
     */
    private final CharacterDto character;

    private View<T> view = null;
    private BaseServiceFactory<T, S> serviceFactory;
    private DbHelper dbHelper = null;

    public BasePresenter(View<T> view, BaseServiceFactory<T, S> serviceFactory, DbHelperFactory dbHFactory, CharacterDto character) {
        this.view = view;
        this.serviceFactory = serviceFactory;
        this.dbHelper = dbHFactory.create();
        this.character = character;
    }

    public void onCreate() {
    }

    public void onViewCreated() {
        String sqexid = character.getSqexid();
        String smileUniqNo = character.getSmileUniqNo();
        view.setHeader(sqexid, smileUniqNo);

        updateList(true, true);

    }

    public void onUpdateClick() {
        updateList(false, false);
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        serviceFactory = null;
        dbHelper = null;
        view = null;
    }


    /**
     * @param useCache       true の場合, (DB上に)キャッシュが有ればそれを返します.
     *                       false の場合, DBデータの有無にかかわらずサーバへリクエストします.
     * @param useInvalidFlag 対象キャラクターのinvalid状況にかかわらず,
     *                       (必要に応じて)サーバーリクエストを行うならfalse.
     */
    private void updateList(boolean useCache, boolean useInvalidFlag) {

        Observable<T> observable
                = Observable.create((Subscriber<? super T> subscriber) -> {
            subscriber.onStart();

            BaseService<T> service = serviceFactory.getService(dbHelper);
            try {
                if (useCache) {
                    T tl = service.getTobatsuListFromDB(character.getWebPcNo());
                    if (tl != null) {
                        subscriber.onNext(tl);
                        subscriber.onCompleted();
                        return;
                    }
                }

                if (useInvalidFlag) {
                    AccountDao dao = AccountDao.create(dbHelper);
                    yukihane.dq10don.account.Character c = dao.findCharacterByWebPcNo(character.getWebPcNo());
                    if (c.isTobatsuInvalid()) {
                        return;
                    }
                }

                T tl = service.getTobatsuListFromServer(character.getWebPcNo());
                subscriber.onNext(tl);
            } catch (AppException | SQLException e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).subscribeOn(DonSchedulers.happyServer());


        view.bind(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<T>() {
            private T tobatsuList;

            @Override
            public void onNext(T tobatsuList) {
                this.tobatsuList = tobatsuList;
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("tobatsu list query error", e);
                if (e instanceof HappyServiceException) {
                    HappyServiceException ex = (HappyServiceException) e;
                    view.showMessage(ex);
                } else if (e instanceof AppException) {
                    // TODO getMessage()じゃなくてもっとちゃんとしたい
                    view.showMessage(e.getMessage());
                } else {
                    view.showMessage((HappyServiceException) null);
                }
            }

            @Override
            public void onCompleted() {
                if (tobatsuList != null) {
                    view.tobatsuListUpdate(tobatsuList);
                }
            }
        });
    }

    public interface View<T> {

        void setHeader(String sqexid, String smileUniqNo);

        void tobatsuListUpdate(T list);

        void bind(Observable<?> observable);

        void showMessage(HappyServiceException ex);

        void showMessage(String message);

    }
}
