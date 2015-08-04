package yukihane.dq10don.tobatsu.view;

import android.support.v4.app.FragmentManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.base.view.BaseFragmentPagerAdapter;

public class TobatsuListPagerAdapter extends BaseFragmentPagerAdapter<TobatsuListFragment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuListPagerAdapter.class);

    public TobatsuListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected TobatsuListFragment newFragment() {
        return new TobatsuListFragment();
    }
}
