package yukihane.dq10don.bosscard.view;

import android.support.v4.app.FragmentManager;

import yukihane.dq10don.base.view.BaseFragmentPagerAdapter;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListPagerAdapter extends BaseFragmentPagerAdapter<BossCardListFragment> {

    public BossCardListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected BossCardListFragment newFragment() {
        return null;
    }
}
