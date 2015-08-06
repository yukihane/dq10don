package yukihane.dq10don.tobatsu.view;

import android.support.v4.app.FragmentManager;

import yukihane.dq10don.background.TobatsuAlarm;
import yukihane.dq10don.base.view.BaseActivity;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.view.TobatsuPrefUtils;
import yukihane.dq10don.tobatsu.presenter.TobatsuPresenter;


public class TobatsuActivity extends BaseActivity<TobatsuPresenter, TobatsuListPagerAdapter> implements TobatsuPresenter.View {

    @Override
    protected TobatsuPresenter newPresenter() {
        return new TobatsuPresenter(this, new DbHelperFactory(this), new TobatsuPrefUtils(this));
    }

    @Override
    protected TobatsuListPagerAdapter newPagerAdapter(FragmentManager fm) {
        return new TobatsuListPagerAdapter(fm);
    }

    @Override
    public void setAlarm(long time) {
        TobatsuAlarm.setAlarm(getApplication(), time);
    }

    @Override
    public void cancelAlarm() {
        TobatsuAlarm.cancelAlarm(getApplication());
    }
}
