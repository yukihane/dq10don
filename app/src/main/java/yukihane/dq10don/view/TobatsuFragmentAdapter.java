package yukihane.dq10don.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import yukihane.dq10don.TobatsuFragment;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

import static yukihane.dq10don.TobatsuFragment.CHARACTER_NAME;
import static yukihane.dq10don.TobatsuFragment.SMILE_UNIQ_NO;
import static yukihane.dq10don.TobatsuFragment.SQEXID;

public class TobatsuFragmentAdapter extends FragmentPagerAdapter {

    private final FragmentManager fragmentManager;
    private final List<Character> characters = new ArrayList<>();

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
        Character c = characters.get(position);
        Bundle b = new Bundle();
        b.putString(SQEXID, c.getAccount().getSqexid());
        b.putString(CHARACTER_NAME, c.getCharacterName());
        b.putString(SMILE_UNIQ_NO, c.getSmileUniqNo());
        fragment.setArguments(b);
        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Character c = characters.get(position);
        String name = c.getCharacterName();
        return name;
    }

    public void setAccounts(List<Account> accounts) {
        this.characters.clear();
        for (Account a : accounts) {
            for (Character c : a.getCharacters()) {
                this.characters.add(c);
            }
        }
        notifyDataSetChanged();
    }
}
