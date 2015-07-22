package yukihane.dq10don.tobatsu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

import static yukihane.dq10don.tobatsu.view.TobatsuFragment.CHARACTER;

public class TobatsuFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragmentPagerAdapter.class);

    private final List<Character> characters = new ArrayList<>();

    public TobatsuFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
        if (characters.size() > position) {
            Character c = characters.get(position);
            String name = c.getCharacterName();
            return name;
        } else {
            // ページを削除した後, サイズより大きい位置の要求が来る
            return "";
        }
    }

    @Override
    public int getItemPosition(Object object) {
        LOGGER.debug("getItemPosition called");
        return POSITION_NONE;
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
