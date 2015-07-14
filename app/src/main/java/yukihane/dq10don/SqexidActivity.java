package yukihane.dq10don;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.account.Account;

public class SqexidActivity extends ActionBarActivity implements SqexidPresenter.View {

    private SqexidPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SqexidPresenter(this);
        setContentView(R.layout.activity_sqexid);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindToList(Observable observable) {
        ListView lv = (ListView) findViewById(R.id.sqexidList);
        ViewObservable.bindView(lv, observable);
    }

    @Override
    public void displayAccount(List<Account> accounts) {
        List<String> names = new ArrayList<>(accounts.size());
        for (Account a : accounts) {
            names.add(a.getSqexid());
        }
        displayAccountStr(names);
    }

    public void displayAccountStr(List<String> names) {
        ArrayAdapter<String> adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        ListView lv = (ListView) findViewById(R.id.sqexidList);
        lv.setAdapter(adpt);
    }
}
