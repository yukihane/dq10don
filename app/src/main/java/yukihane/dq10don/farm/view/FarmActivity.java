package yukihane.dq10don.farm.view;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import yukihane.dq10don.R;
import yukihane.dq10don.ViewUtils;
import yukihane.dq10don.base.view.BaseActivity;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.presenter.FarmPresenter;

public class FarmActivity extends BaseActivity<FarmPresenter, FarmListPagerAdapter> implements FarmPresenter.View {
    @Override
    protected FarmPresenter newPresenter() {
        return new FarmPresenter(this, new DbHelperFactory(this));
    }

    @Override
    protected FarmListPagerAdapter newPagerAdapter(FragmentManager fm) {
        return new FarmListPagerAdapter(fm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (ViewUtils.createDqxToolsIntent(this) != null) {
            getMenuInflater().inflate(R.menu.menu_farm, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_dqxtools) {
            Intent intent = ViewUtils.createDqxToolsIntent(this);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
