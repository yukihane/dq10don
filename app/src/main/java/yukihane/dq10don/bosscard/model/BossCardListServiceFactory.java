package yukihane.dq10don.bosscard.model;

/**
 * Created by yuki on 15/07/31.
 */

import java.util.List;

import yukihane.dq10don.account.Storage;
import yukihane.dq10don.base.model.BaseServiceFactory;
import yukihane.dq10don.db.DbHelper;

/**
 * Created by yuki on 15/07/18.
 */
public class BossCardListServiceFactory implements BaseServiceFactory<List<Storage>, BossCardListService> {

    @Override
    public BossCardListService getService(DbHelper dbHelper) {
        return new BossCardListServiceImpl(dbHelper);
    }
}
