package yukihane.dq10don.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import yukihane.dq10don.TobatsuFragment;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuFragmentAdapter extends FragmentPagerAdapter {

    private final FragmentManager fragmentManager;

    public TobatsuFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        TobatsuFragment fragment = new TobatsuFragment();
        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 0;
    }
}
