package yukihane.dq10don.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by yuki on 15/07/13.
 */
public class DbHelperFactory {
    private final Context context;

    public DbHelperFactory(Context context) {
        this.context = context;
    }

    public DbHelper create() {
        return OpenHelperManager.getHelper(context, DbHelper.class);
    }
}
