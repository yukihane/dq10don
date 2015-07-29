package yukihane.dq10don.tobatsu.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tobatsu.presenter.BasePresenter;
import yukihane.dq10don.tobatsu.presenter.TobatsuPresenter;

public abstract class BaseFragment<P extends BasePresenter, A> extends Fragment {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragment.class);

    private P presenter;
    private A tobatsuViewAdapter;
    private ListView tobatsuListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharacterDtoImpl character = getArguments().getParcelable(CHARACTER);
        presenter = newPresenter(new DbHelperFactory(getActivity()), character);
        presenter.onCreate();
    }

    protected abstract P newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character);

}
