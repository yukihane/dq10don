package yukihane.dq10don.bosscard.view;

import android.view.LayoutInflater;

import yukihane.dq10don.account.Storage;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.bosscard.presenter.BossCardListPresenter;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListFragment extends BaseFragment<Storage, BossCardListPresenter, BossCardListViewAdapter>
        implements BasePresenter.View<Storage> {
    @Override
    protected void addDisplayItems(BossCardListViewAdapter viewAdapter, Storage list) {

    }

    @Override
    protected BossCardListViewAdapter newViewAdapter(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected BossCardListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return null;
    }
}
