package yukihane.dq10don.farm.view;

import android.support.v4.app.FragmentManager;

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
}
