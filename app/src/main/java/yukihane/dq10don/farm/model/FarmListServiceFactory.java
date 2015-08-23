package yukihane.dq10don.farm.model;

import yukihane.dq10don.account.Farm;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.tobatsu.model.TobatsuListService;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListServiceFactory implements BaseServiceFactory<Farm, FarmListService> {
    @Override
    public FarmListService getService(DbHelper dbHelper) {
        return new FarmListServiceImpl(dbHelper);
    }
}
