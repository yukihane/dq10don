package yukihane.dq10don.settings.view;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuki on 15/08/04.
 */
public final class TobatsuPrefUtils {

    public static final String PREF_NAME = "tobatsu";

    public static final String AUTO_PILOT = "autopilot";

    private final Context context;

    public TobatsuPrefUtils(Context context) {
        this.context = context;
    }

    public boolean isAutoPilotEnabled() {
        return getPrefs().getBoolean(AUTO_PILOT, true);
    }


    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
