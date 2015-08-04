package yukihane.dq10don.tobatsu.model;

import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.db.DbHelper;

/**
 * Created by yuki on 15/07/18.
 */
public class TobatsuListServiceFactory implements BaseServiceFactory<TobatsuList, TobatsuListService> {

    @Override
    public TobatsuListService getService(DbHelper dbHelper) {
        return new TobatsuListServiceImpl(dbHelper);
    }
}
