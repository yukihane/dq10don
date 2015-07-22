package yukihane.dq10don.tobatsu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import yukihane.dq10don.R;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.background.Alarm;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.sqexid.view.SqexidActivity;
import yukihane.dq10don.tobatsu.presenter.MainPresenter;


public class MainActivity extends ActionBarActivity implements MainPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    private MainPresenter presenter;

    private TobatsuFragmentPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new DbHelperFactory(this));

        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tobatsuListPager);
        viewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getSupportFragmentManager();

        pageAdapter = new TobatsuFragmentPageAdapter(fm);

        viewPager.setAdapter(pageAdapter);


        presenter.onCreate();
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setAccounts(List<Account> accounts) {
        pageAdapter.setAccounts(accounts);
    }

    @Override
    public void setAlarm(long time) {
        Alarm.setAlarm(getApplication(), time);
    }

    @Override
    public void cancelAlarm() {
        Alarm.cancelAlarm(getApplication());
    }
}
