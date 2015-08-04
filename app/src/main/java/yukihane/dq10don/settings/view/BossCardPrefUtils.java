package yukihane.dq10don.settings.view;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuki on 15/08/04.
 */
public final class BossCardPrefUtils {

    public static final String PREF_NAME = "bosscard";

    public static final String AUTO_PILOT = "autopilot";
    public static final String NOTIFICATION = "notification";
    public static final String NOTIFY_TIME = "notification_left_time";

    private final Context context;

    public BossCardPrefUtils(Context context) {
        this.context = context;
    }

    public boolean isAutoPilotEnabled() {
        return getPrefs().getBoolean(AUTO_PILOT, true);
    }

    public boolean isNotification() {
        return getPrefs().getBoolean(NOTIFICATION, true);
    }

    public int getLeftTimeLimit() {
        String v = getPrefs().getString(NOTIFY_TIME, "4");
        return Integer.parseInt(v);
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
