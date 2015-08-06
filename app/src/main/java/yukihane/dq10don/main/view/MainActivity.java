package yukihane.dq10don.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import yukihane.dq10don.R;
import yukihane.dq10don.bosscard.view.BossCardActivity;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.debug.view.DebugActivity;
import yukihane.dq10don.main.presenter.MainPresenter;
import yukihane.dq10don.settings.view.SettingsActivity;
import yukihane.dq10don.sqexid.view.SqexidActivity;
import yukihane.dq10don.tobatsu.view.TobatsuActivity;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, new DbHelperFactory(this));

        View tobatsu = findViewById(R.id.launchTobatsuButton);
        tobatsu.setOnClickListener((v) -> {
            Intent intent = new Intent(this, TobatsuActivity.class);
            startActivity(intent);
        });

        View bossCard = findViewById(R.id.launchBossCardButton);
        bossCard.setOnClickListener((v) -> {
            Intent intent = new Intent(this, BossCardActivity.class);
            startActivity(intent);
        });

        presenter.onCreate(savedInstanceState == null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sqexid) {
            Intent intent = new Intent(this, SqexidActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_debug) {
            Intent intent = new Intent(this, DebugActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWelcomeDialog() {
        new WelcomeDialog().show(getSupportFragmentManager(), "WelcomeDialog");
    }
}
