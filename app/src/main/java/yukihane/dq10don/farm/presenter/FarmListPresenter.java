package yukihane.dq10don.farm.presenter;

import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.model.FarmListService;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListPresenter extends BasePresenter<Farm, FarmListService> {
    public FarmListPresenter(BaseServiceFactory serviceFactory, DbHelperFactory dbHFactory, CharacterDto character) {
        super(serviceFactory, dbHFactory, character);
    }
}
