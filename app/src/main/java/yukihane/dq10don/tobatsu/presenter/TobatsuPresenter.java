package yukihane.dq10don.tobatsu.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.tobatsu.model.TobatsuService;
import yukihane.dq10don.tobatsu.model.TobatsuServiceFactory;

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

        updateList(false);

    }


    public void onUpdateClick() {
        updateList(true);
    }

    public void onDestroyView() {
        view = null;
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }

    /**
     * @param forceUpdate false の場合, (DB上に)キャッシュが有ればそれを返します.
     *                    trueのの場合, DBデータの有無にかかわらずサーバへリクエストします.
     */
    private void updateList(boolean forceUpdate) {

        Observable<TobatsuList> observable = Observable.create(new Observable.OnSubscribe<TobatsuList>() {
            @Override
            public void call(Subscriber<? super TobatsuList> subscriber) {
                subscriber.onStart();

                TobatsuService service = new TobatsuServiceFactory().getService(dbHelper);
                try {
                    if (forceUpdate) {
                        TobatsuList tl = service.getTobatsuListFromServer(character.getWebPcNo());
                        subscriber.onNext(tl);
                    } else {
                        TobatsuList tl = service.getTobatsuList(character.getWebPcNo());
                        subscriber.onNext(tl);
                    }
                } catch (AppException e) {
                    LOGGER.error("tobatsu list query error", e);
                    subscriber.onError(e);
                } catch (RetrofitError e) {
                    LOGGER.error("retrofit error: url: {}, message: {}, type: {}, body: {}",
                            e.getUrl(), e.getMessage(), e.getSuccessType(), e.getBody());

                    LOGGER.error("", e);
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());


        view.bindToList(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TobatsuList>() {
            private TobatsuList tobatsuList;

            @Override
            public void onCompleted() {
                LOGGER.debug("onCompleted");
                if (tobatsuList != null) {
                    view.tobatsuListUpdate(tobatsuList);
                }
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("error occured", e);
                if (e instanceof AppException) {
                    AppException ex = (AppException) e;
                    view.showMessage(ex);
                }
            }

            @Override
            public void onNext(TobatsuList tobatsuList) {
                LOGGER.debug("onNext");
                this.tobatsuList = tobatsuList;
            }
        });
    }

    public interface View {
        void bindToList(Observable observable);

        void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list);

        void setHeader(String sqexid, String smileUniqNo);

        void showMessage(AppException ex);
    }
}
