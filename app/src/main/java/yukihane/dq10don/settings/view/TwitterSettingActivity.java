package yukihane.dq10don.settings.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import yukihane.dq10don.R;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class TwitterSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // http://stackoverflow.com/questions/5169532/
        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(PrefUtils.PREF_NAME);
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

        addPreferencesFromResource(R.xml.pref_twitter);
    }
}
