package yukihane.dq10don.farm.view;

import yukihane.dq10don.R;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.model.FarmListServiceFactory;
import yukihane.dq10don.farm.presenter.FarmListPresenter;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListFragment extends BaseFragment<Farm, FarmListPresenter> {
    @Override
    protected int getContentResId() {
        return R.layout.base_content_farm;
    }

    @Override
    protected FarmListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return new FarmListPresenter(new FarmListServiceFactory(), dbHelperFactory, character);
    }

    @Override
    public void onListUpdated(Farm list) {
        // TODO コンテンツ更新
    }
}
