package yukihane.dq10don.settings.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import yukihane.dq10don.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_top);
    }
}
