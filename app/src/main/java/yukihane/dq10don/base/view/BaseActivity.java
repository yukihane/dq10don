package yukihane.dq10don.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import yukihane.dq10don.R;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.base.presenter.BaseActivityPresenter;
import yukihane.dq10don.debug.view.DebugActivity;
import yukihane.dq10don.settings.view.SettingsActivity;
import yukihane.dq10don.sqexid.view.SqexidActivity;
import yukihane.dq10don.tobatsu.view.TobatsuActivity;

/**
 * Created by yuki on 15/08/06.
 */
public abstract class BaseActivity<P extends BaseActivityPresenter, A extends BaseFragmentPagerAdapter<?>>
        extends AppCompatActivity implements BaseActivityPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuActivity.class);

    private P presenter;

    private A pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = newPresenter();

        setContentView(R.layout.activity_base);
        ViewPager viewPager = (ViewPager) findViewById(R.id.baseListPager);
        viewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getSupportFragmentManager();

        pagerAdapter = newPagerAdapter(fm);

        viewPager.setAdapter(pagerAdapter);


        presenter.onCreate(savedInstanceState == null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    protected abstract P newPresenter();

    protected abstract A newPagerAdapter(FragmentManager fm);

    @Override
    public void setAccounts(List<Account> accounts) {
        pagerAdapter.setAccounts(accounts);
    }
}
