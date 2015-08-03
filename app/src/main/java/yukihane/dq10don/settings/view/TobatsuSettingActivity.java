package yukihane.dq10don.settings.view;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.R;
import yukihane.dq10don.background.TobatsuAlarm;
import yukihane.dq10don.db.BgServiceDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

public class TobatsuSettingActivity extends PreferenceActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuSettingActivity.class);

    private DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // http://stackoverflow.com/questions/5169532/
        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName(TobatsuPrefUtils.PREF_NAME);
        prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

        addPreferencesFromResource(R.xml.pref_tobatsu);

        dbHelper = new DbHelperFactory(this).create();

        Preference autoPilot = findPreference(TobatsuPrefUtils.AUTO_PILOT);
        autoPilot.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean autoPilot = (boolean) newValue;
                if (autoPilot) {
                    try {
                        BgServiceDao dao = BgServiceDao.create(dbHelper);
                        TobatsuAlarm.setAlarm(getApplicationContext(), dao.get().getNextAlarmTime());
                    } catch (SQLException e) {
                        LOGGER.error("db error", e);
                    }
                } else {
                    TobatsuAlarm.cancelAlarm(getApplicationContext());
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }
}
