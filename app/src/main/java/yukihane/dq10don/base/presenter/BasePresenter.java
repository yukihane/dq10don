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
import yukihane.dq10don.exception.ApplicationException;
import yukihane.dq10don.exception.ErrorCode;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * サービスへ表示対象を要求し, キャラクターごとに結果をキャラクターごとに表示する機能のプレセンターのベース.
 *
 * @param <T> 表示対象とするデータの型.
 * @param <S> 表示対象を要求するサービスの型.
 */
public abstract class BasePresenter<T, S extends BaseService<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);

    /**
     * このインスタンスが表示対象としているキャラクター
     */
    private final CharacterDto character;

    private View<T> view = null;
    private BaseServiceFactory<T, S> serviceFactory;
    private DbHelper dbHelper = null;

    public BasePresenter(BaseServiceFactory<T, S> serviceFactory, DbHelperFactory dbHFactory, CharacterDto character) {
        this.view = new NullView();
        this.serviceFactory = serviceFactory;
        this.dbHelper = dbHFactory.create();
        this.character = character;
    }

    public void onCreate() {
    }

    public void onCreateView(View<T> view) {
        this.view = view;
    }

    public void onViewCreated() {
        String sqexid = character.getSqexid();
        String smileUniqNo = character.getSmileUniqNo();
        view.setHeader(sqexid, smileUniqNo);

        updateList(true, true);

    }

    public void onDestroyView() {
        view = new NullView();
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        serviceFactory = null;
        dbHelper = null;
    }

    public void onUpdateClick() {
        updateList(false, false);
    }

    /**
     * @param useCache       true の場合, (DB上に)キャッシュが有ればそれを返します.
     *                       false の場合, DBデータの有無にかかわらずサーバへリクエストします.
     * @param useInvalidFlag 対象キャラクターのinvalid状況にかかわらず,
     *                       (必要に応じて)サーバーリクエストを行うならfalse.
     */
    private void updateList(boolean useCache, boolean useInvalidFlag) {

        view.setLoadingState(true);

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
            private T resultList;

            @Override
            public void onNext(T list) {
                this.resultList = list;
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("list query error", e);
                if (e instanceof HappyServiceException) {
                    HappyServiceException ex = (HappyServiceException) e;
                    view.showMessage(ex);
                } else if (e instanceof ApplicationException) {
                    ApplicationException ex = (ApplicationException) e;
                    view.showMessage(ex.getErrorCode());
                } else {
                    view.showMessage(ErrorCode.ERROR);
                }
            }

            @Override
            public void onCompleted() {
                if (resultList != null) {
                    view.onListUpdated(resultList);
                }
                view.setLoadingState(false);
            }
        });
    }

    /**
     * サービスへ表示対象を要求し, キャラクターごとに結果をキャラクターごとに表示する機能のView.
     *
     * @param <T> 表示対象とするデータの型.
     */
    public interface View<T> {

        void setHeader(String sqexid, String smileUniqNo);

        void onListUpdated(T list);

        void bind(Observable<?> observable);

        void showMessage(HappyServiceException ex);

        void showMessage(int errorCode);

        /**
         * ロード中(作業中)状態とそうでない状態を切り替えます.
         *
         * @param loading ロード中状態に切り替えるのであればtrue.
         *                非ロード中であればfalse.
         */
        void setLoadingState(boolean loading);
    }

    private class NullView implements View<T> {
        @Override
        public void setHeader(String sqexid, String smileUniqNo) {
        }

        @Override
        public void onListUpdated(T list) {
        }

        @Override
        public void bind(Observable<?> observable) {
        }

        @Override
        public void showMessage(HappyServiceException ex) {
        }

        @Override
        public void showMessage(int errorCode) {
        }

        @Override
        public void setLoadingState(boolean load) {
        }
    }

}
