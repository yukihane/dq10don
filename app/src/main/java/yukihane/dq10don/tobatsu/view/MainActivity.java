package yukihane.dq10don.tobatsu.view;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.background.Alarm;
import yukihane.dq10don.background.RoundService;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.debug.view.DebugActivity;
import yukihane.dq10don.sqexid.view.SqexidActivity;
import yukihane.dq10don.tobatsu.presenter.MainPresenter;


public class MainActivity extends ActionBarActivity implements MainPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    private MainPresenter presenter;

    private TobatsuFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new DbHelperFactory(this));

        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tobatsuListPager);
        viewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getSupportFragmentManager();

        pagerAdapter = new TobatsuFragmentPagerAdapter(fm);

        viewPager.setAdapter(pagerAdapter);


        presenter.onCreate();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(RoundService.TOBATSU_NOTIFICATION_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
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
        } else if (id == R.id.action_debug) {
            Intent intent = new Intent(this, DebugActivity.class);
            startActivity(intent);
//            File fromDir = getFilesDir();
//            File toDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            presenter.exportLog(fromDir, toDir);
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
        pagerAdapter.setAccounts(accounts);
    }

    @Override
    public void setAlarm(long time) {
        Alarm.setAlarm(getApplication(), time);
    }

    @Override
    public void cancelAlarm() {
        Alarm.cancelAlarm(getApplication());
    }

    @Override
    public void showWelcomeDialog() {
        new WelcomeDialog().show(getSupportFragmentManager(), "WelcomeDialog");
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindActivity(this, observable);
    }

    @Override
    public void showMessage(MainPresenter.Message message) {
        final String text;
        switch (message) {
            case COMPLETED_EXPORTFILE:
                text = getString(R.string.completed_exportfile);
                break;
            default:
                LOGGER.error("message is not defined: {}", message);
                return;
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
