package yukihane.dq10don;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.login.view.LoginActivity;

public class SqexidActivity extends ActionBarActivity
        implements SqexidPresenter.View, OpeDialog.Listener, RemoveConfirmDialog.Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqexidActivity.class);

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
                    String userId = (String) lv.getItemAtPosition(position);
//                    presenter.onClickSqexid(userId);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        List<String> names = new ArrayList<>(accounts.size());
        for (Account a : accounts) {
            names.add(a.getSqexid());
        }
        displayAccountStr(names);
    }

    @Override
    public void showLogin(String userId) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userId", userId);
        startActivityForResult(intent, 0);
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

    public void displayAccountStr(List<String> names) {
        ArrayAdapter<String> adpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        getSqexidListView().setAdapter(adpt);
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
