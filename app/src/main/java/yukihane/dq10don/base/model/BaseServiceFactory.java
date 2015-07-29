package yukihane.dq10don.base.model;

import yukihane.dq10don.db.DbHelper;

/**
 * Created by yuki on 15/07/30.
 */
public interface BaseServiceFactory<T, S extends BaseService<T>> {
    S getService(DbHelper dbHelper);
}
