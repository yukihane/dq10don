package yukihane.dq10don.settings.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yukihane.dq10don.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View tobatsu = findViewById(R.id.settingTobatsuView);
        tobatsu.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, TobatsuSettingActivity.class);
            startActivity(intent);
        });

        View bossCard = findViewById(R.id.settingBossCardView);
        bossCard.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, BossCardSettingActivity.class);
            startActivity(intent);
        });

        View twitter = findViewById(R.id.settingTwitterView);
        twitter.setOnClickListener((v) -> {
            Intent intent = new Intent(SettingsActivity.this, TwitterSettingActivity.class);
            startActivity(intent);
        });
    }
}
