package yukihane.dq10don.settings.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import yukihane.dq10don.R;

public class TobatsuSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // http://stackoverflow.com/questions/5169532/
        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(TobatsuPrefUtils.PREF_NAME);
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

        addPreferencesFromResource(R.xml.pref_tobatsu);
    }
}
