package yukihane.dq10don.bosscard.presenter;

import java.util.List;

import yukihane.dq10don.account.Storage;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.bosscard.model.BossCardListService;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/29.
 */
public class BossCardListPresenter extends BasePresenter<List<Storage>, BossCardListService> {

    public BossCardListPresenter(
            BaseServiceFactory<List<Storage>, BossCardListService> serviceFactory,
            DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }
}
