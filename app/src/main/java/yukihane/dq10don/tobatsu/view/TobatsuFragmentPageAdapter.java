package yukihane.dq10don.tobatsu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

import static yukihane.dq10don.tobatsu.view.TobatsuFragment.CHARACTER;

public class TobatsuFragmentPageAdapter extends FragmentPagerAdapter {

    private final FragmentManager fragmentManager;
    private final List<Character> characters = new ArrayList<>();

    public TobatsuFragmentPageAdapter(FragmentManager fm) {
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
        CharacterDtoImpl cdto = CharacterDtoImpl.from(c);
        Bundle b = new Bundle();
        b.putParcelable(CHARACTER, cdto);
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
