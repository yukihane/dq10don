package yukihane.dq10don;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import io.fabric.sdk.android.Fabric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/18.
 */
public class Application extends android.app.Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Getter
    private DbHelperFactory dbHelper;

    @Override
    public void onCreate() {
        LOGGER.debug("onCreate called");
        super.onCreate();

        // http://stackoverflow.com/a/30489601
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        dbHelper = new DbHelperFactory(this);
    }

    @Override
    public void onTerminate() {
        LOGGER.debug("onTerminate called");
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
        super.onTerminate();
    }
}
