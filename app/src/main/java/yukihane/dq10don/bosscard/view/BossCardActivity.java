package yukihane.dq10don.bosscard.view;

import android.support.v4.app.FragmentManager;

import yukihane.dq10don.background.BossCardAlarm;
import yukihane.dq10don.base.view.BaseActivity;
import yukihane.dq10don.bosscard.presenter.BossCardPresenter;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.view.BossCardPrefUtils;

public class BossCardActivity extends BaseActivity<BossCardPresenter, BossCardListPagerAdapter> implements BossCardPresenter.View {

    @Override
    protected BossCardPresenter newPresenter() {
        return new BossCardPresenter(this, new DbHelperFactory(this), new BossCardPrefUtils(this));
    }

    @Override
    protected BossCardListPagerAdapter newPagerAdapter(FragmentManager fm) {
        return new BossCardListPagerAdapter(fm);
    }

    @Override
    public void setAlarmIfNothing() {
        BossCardAlarm.setIfNothing(getApplicationContext());
    }
}
