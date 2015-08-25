package yukihane.dq10don.sqexid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.login.view.LoginActivity;
import yukihane.dq10don.sqexid.presenter.SqexidPresenter;

public class SqexidActivity extends AppCompatActivity
        implements SqexidPresenter.View, OpeDialog.Listener, RemoveConfirmDialog.Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqexidActivity.class);

    private static final String KEY_SQEXID = "sqexid";
    private static final String KEY_DESCRIPTION = "description";

    private SqexidPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqexid);

        presenter = new SqexidPresenter(this, new DbHelperFactory(this));
        presenter.onCreate();
        getSqexidListView().setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    ListView lv = (ListView) parent;
                    Map<String, String> data = (Map<String, String>) lv.getItemAtPosition(position);
                    String userId = data.get(KEY_SQEXID);
                    OpeDialog df = new OpeDialog();
                    df.setUserId(userId);
                    df.show(getSupportFragmentManager(), "OpeDialog");
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sqexid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionAddAccount) {
            presenter.onLogin(null);
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
    public void bindToList(Observable observable) {
        ViewObservable.bindView(getSqexidListView(), observable);
    }

    @Override
    public void displayAccount(List<Account> accounts) {

        List<Map<String, String>> data = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            Map<String, String> acMap = new HashMap<>(2);
            acMap.put(KEY_SQEXID, account.getSqexid());
            if (account.isInvalid()) {
                acMap.put(KEY_DESCRIPTION, getString(R.string.http_401));
            }

            data.add(acMap);
        }
        String[] from = {KEY_SQEXID, KEY_DESCRIPTION};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adpt = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);
        getSqexidListView().setAdapter(adpt);
    }

    @Override
    public void showLogin(String userId) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userId", userId);
        startActivityForResult(intent, 0);
    }

    @Override
    public void showGuidance() {
        new GuidanceDialog().show(getSupportFragmentManager(), "GuidanceDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String sqexid;
        final String json;
        if (data == null) {
            sqexid = null;
            json = null;
        } else {
            sqexid = data.getStringExtra("userId");
            json = data.getStringExtra("result");
        }
        presenter.onActivityResult(sqexid, json);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private ListView getSqexidListView() {
        return (ListView) findViewById(R.id.sqexidList);
    }

    @Override
    public void onRelogin(String userId) {
        presenter.onLogin(userId);
    }

    @Override
    public void removeConfirm(String userId) {
        RemoveConfirmDialog df = new RemoveConfirmDialog();
        df.setUserId(userId);

        df.show(getSupportFragmentManager(), "RemoveConfirmDialog");
    }

    @Override
    public void onRemove(String userId) {
        presenter.onRemove(userId);
    }
}
