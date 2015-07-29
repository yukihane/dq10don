package yukihane.dq10don.tobatsu.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import yukihane.dq10don.DonSchedulers;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;
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

        Observable<TobatsuList> observable
                = Observable.create((Subscriber<? super TobatsuList> subscriber) -> {
            subscriber.onStart();

            TobatsuService service = new TobatsuServiceFactory().getService(dbHelper);
            try {
                if (useCache) {
                    TobatsuList tl = service.getTobatsuListFromDB(character.getWebPcNo());
                    if (tl != null) {
                        subscriber.onNext(tl);
                        subscriber.onCompleted();
                        return;
                    }
                }

                if (useInvalidFlag) {
                    AccountDao dao = AccountDao.create(dbHelper);
                    Character c = dao.findCharacterByWebPcNo(character.getWebPcNo());
                    if (c.isTobatsuInvalid()) {
                        return;
                    }
                }

                TobatsuList tl = service.getTobatsuListFromServer(character.getWebPcNo());
                subscriber.onNext(tl);
            } catch (AppException | SQLException e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).subscribeOn(DonSchedulers.happyServer());


        view.bind(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TobatsuList>() {
            private TobatsuList tobatsuList;

            @Override
            public void onNext(TobatsuList tobatsuList) {
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

    public interface View {
        void bind(Observable<?> observable);

        void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list);

        void setHeader(String sqexid, String smileUniqNo);

        void showMessage(HappyServiceException ex);

        void showMessage(String message);
    }
}
