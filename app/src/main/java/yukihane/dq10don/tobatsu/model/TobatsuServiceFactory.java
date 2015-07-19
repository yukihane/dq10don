package yukihane.dq10don.tobatsu.model;

import yukihane.dq10don.db.DbHelper;

/**
 * Created by yuki on 15/07/18.
 */
public class TobatsuServiceFactory {
    public TobatsuService getService(DbHelper dbHelper) {
        return new TobatsuServiceImpl(dbHelper);
    }
}
