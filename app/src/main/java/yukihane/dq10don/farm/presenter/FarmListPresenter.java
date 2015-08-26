package yukihane.dq10don.farm.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import yukihane.dq10don.DonSchedulers;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.db.FarmDao;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;
import yukihane.dq10don.farm.model.FarmListService;
import yukihane.dq10don.farm.model.MowResult;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListPresenter extends BasePresenter<Farm, FarmListPresenter.View, FarmListService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmListPresenter.class);

    private static final NullView NULL_VIEW = new NullView();


    public FarmListPresenter(BaseServiceFactory serviceFactory,
                             DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }


    @Override
    protected View getNullView() {
        return NULL_VIEW;
    }

    public void mowGrasses() {

        getView().setLoadingState(true);

        Observable<MowResult> observable
                = Observable.create((Subscriber<? super MowResult> subscriber) -> {
            subscriber.onStart();

            FarmListService service = getService();
            try {

                MowResult res = service.mowAllGrasses(getCharacter().getWebPcNo());
                FarmDao farmDao = FarmDao.create(getDbHelper());

                // 刈り終わったのでデータベースから削除しておく
                farmDao.deleteGrasses(getCharacter().getWebPcNo());

                subscriber.onNext(res);
                subscriber.onCompleted();
            } catch (AppException | SQLException e) {
                subscriber.onError(e);
            }
        }).subscribeOn(DonSchedulers.happyServer());


        getView().bind(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MowResult>() {
            private MowResult data;

            @Override
            public void onNext(MowResult data) {
                this.data = data;
            }

            @Override
            public void onError(Throwable e) {
                handleServiceError(e);
            }

            @Override
            public void onCompleted() {
                if (data != null) {
                    getView().onGrassMowed(data);
                }
                getView().setLoadingState(false);
            }
        });
    }

    public void openBoxes() {
    }

    public interface View extends BasePresenter.View<Farm> {
        /**
         * 草が刈られた
         *
         * @param res 刈った草から得られたもの
         */
        void onGrassMowed(MowResult res);
    }

    private static class NullView implements View {
        @Override
        public void setHeader(String sqexid, String smileUniqNo) {
        }

        @Override
        public void onDataUpdated(Farm list) {
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
        public void setLoadingState(boolean loading) {
        }

        @Override
        public void onGrassMowed(MowResult res) {
        }
    }
}
