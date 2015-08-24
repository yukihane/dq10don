package yukihane.dq10don.farm.presenter;

import rx.Observable;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.HappyServiceException;
import yukihane.dq10don.farm.model.FarmListService;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListPresenter extends BasePresenter<Farm, FarmListPresenter.View, FarmListService> {

    private static final NullView NULL_VIEW = new NullView();

    public FarmListPresenter(BaseServiceFactory serviceFactory,
                             DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }


    @Override
    protected View getNullView() {
        return NULL_VIEW;
    }

    public interface View extends BasePresenter.View<Farm> {
    }

    private static final class NullView implements View {
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
    }

}
