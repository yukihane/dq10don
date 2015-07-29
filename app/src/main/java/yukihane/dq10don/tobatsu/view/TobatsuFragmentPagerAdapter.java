package yukihane.dq10don.tobatsu.view;

import android.support.v4.app.FragmentManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.base.view.BaseFragmentPagerAdapter;

public class TobatsuFragmentPagerAdapter extends BaseFragmentPagerAdapter<TobatsuFragment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragmentPagerAdapter.class);

    public TobatsuFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    protected TobatsuFragment newFragment() {
        return new TobatsuFragment();
    }
}
