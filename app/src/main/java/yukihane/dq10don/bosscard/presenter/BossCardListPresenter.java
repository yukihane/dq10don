package yukihane.dq10don.bosscard.presenter;

import java.util.List;

import rx.Observable;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.bosscard.model.BossCardListService;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/29.
 */
public class BossCardListPresenter extends BasePresenter<List<Storage>, BossCardListPresenter.View, BossCardListService> {

    private static final NullView NULL_VIEW = new NullView();

    public BossCardListPresenter(
            BaseServiceFactory<List<Storage>, BossCardListService> serviceFactory,
            DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }

    @Override
    protected View getNullView() {
        return NULL_VIEW;
    }

    public interface View extends BasePresenter.View<List<Storage>> {
    }

    private static final class NullView implements View {
        @Override
        public void setHeader(String sqexid, String smileUniqNo) {
        }

        @Override
        public void onDataUpdated(List<Storage> list) {
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
