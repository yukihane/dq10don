package yukihane.dq10don.tobatsu.presenter;

import rx.Observable;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.HappyServiceException;
import yukihane.dq10don.tobatsu.model.TobatsuListService;
import yukihane.dq10don.tobatsu.model.TobatsuListServiceFactory;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuListPresenter extends BasePresenter<TobatsuList, TobatsuListPresenter.View, TobatsuListService> {

    private static final NullView NULL_VIEW = new NullView();

    public TobatsuListPresenter(TobatsuListServiceFactory serviceFactory,
                                DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }

    @Override
    protected View getNullView() {
        return NULL_VIEW;
    }

    public interface View extends BasePresenter.View<TobatsuList> {
    }

    private static final class NullView implements View {
        @Override
        public void setHeader(String sqexid, String smileUniqNo) {
        }

        @Override
        public void onDataUpdated(TobatsuList list) {
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
    }
}
