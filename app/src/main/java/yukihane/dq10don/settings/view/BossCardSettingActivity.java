package yukihane.dq10don.settings.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import yukihane.dq10don.R;

public class BossCardSettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // http://stackoverflow.com/questions/5169532/
        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(BossCardPrefUtils.PREF_NAME);
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

        addPreferencesFromResource(R.xml.pref_boss_card);
    }
}
