package yukihane.dq10don.farm.view;

import android.support.v4.app.FragmentManager;

import yukihane.dq10don.base.view.BaseFragmentPagerAdapter;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListPagerAdapter extends BaseFragmentPagerAdapter<FarmListFragment> {
    public FarmListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected FarmListFragment newFragment() {
        return new FarmListFragment();
    }
}
