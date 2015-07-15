package yukihane.dq10don;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.view.TobatsuFragmentAdapter;
import yukihane.dq10don.view.TobatsuViewAdapter;


public class MainActivity extends ActionBarActivity implements MainPresenter.View {

    private final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private MainPresenter presenter;

    private TobatsuViewAdapter tobatsuViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new DbHelperFactory(this));

        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tobatsuListPager);
        viewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getSupportFragmentManager();

        TobatsuFragmentAdapter adapter = new TobatsuFragmentAdapter(fm);

        viewPager.setAdapter(adapter);


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

        if (id == R.id.action_reload_tobatsu) {
            presenter.onUpdateClick();
            return true;
        } else if (id == R.id.action_sqexid) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        logger.debug("requestCode: {}, resultCode: {}, intent: {}", requestCode, resultCode, data != null);

        if (data == null) {
            logger.error("LOGIN fail");
        } else {
            try {
                String sqexid = data.getStringExtra("userId");
                String json = data.getStringExtra("result");
                logger.info("LOGIN success({}): {}", sqexid, json);
                presenter.onActivityResult(sqexid, json);
            } catch (IOException e) {
                logger.error("parse error", e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void bindToList(Observable observable) {
        ListView view = (ListView) findViewById(R.id.tobatsuListView);
        ViewObservable.bindView(view, observable);
    }

    @Override
    public void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list) {
        tobatsuViewAdapter.addItem(String.class, "受注中");
        for (TobatsuItem item : list.getAcceptings()) {
            tobatsuViewAdapter.addItem(TobatsuItem.class, item);
        }

        tobatsuViewAdapter.addItem(String.class, "リスト");
        for (TobatsuItem item : list.getListings()) {
            tobatsuViewAdapter.addItem(TobatsuItem.class, item);
        }

        tobatsuViewAdapter.notifyChanged();
    }
}
