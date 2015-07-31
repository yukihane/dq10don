package yukihane.dq10don.bosscard.view;

import android.view.LayoutInflater;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yukihane.dq10don.R;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.account.StoredItem;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.bosscard.model.BossCardListServiceFactory;
import yukihane.dq10don.bosscard.presenter.BossCardListPresenter;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListFragment extends BaseFragment<List<Storage>, BossCardListPresenter, BossCardListViewAdapter>
        implements BasePresenter.View<List<Storage>> {

    @Override
    protected BossCardListViewAdapter newViewAdapter(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected BossCardListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return null;
    }
}
