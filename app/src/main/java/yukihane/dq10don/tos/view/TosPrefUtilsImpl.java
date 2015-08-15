package yukihane.dq10don.tos.view;

import android.content.Context;
import android.content.SharedPreferences;

import yukihane.dq10don.tos.model.TosPrefUtils;

/**
 * Created by yuki on 2015/08/15.
 */
public class TosPrefUtilsImpl implements TosPrefUtils {

    private static final String PREF_NAME = "tos";

    private static final String AGREED_VERSION = "agreedVersion";

    private final Context context;

    public TosPrefUtilsImpl(Context context) {
        this.context = context;
    }

    public int getAgreedVersion() {
        return getPrefs().getInt(AGREED_VERSION, 0);
    }

    public void setAgreedVersion(int version) {
        getPrefs().edit().putInt(AGREED_VERSION, version).commit();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }
}
