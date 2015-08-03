package yukihane.dq10don.settings.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import yukihane.dq10don.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        TextView bossCard = (TextView) findViewById(R.id.settingBossCardView);
        bossCard.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, BossCardSettingActivity.class);
            startActivity(intent);
        });

        TextView twitter = (TextView) findViewById(R.id.settingTwitterView);
        twitter.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, TwitterSettingActivity.class);
            startActivity(intent);
        });
    }
}
