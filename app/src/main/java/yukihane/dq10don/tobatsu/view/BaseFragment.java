package yukihane.dq10don.tobatsu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import yukihane.dq10don.R;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tobatsu.presenter.BasePresenter;

public abstract class BaseFragment<T, P extends BasePresenter, A extends BaseViewAdapter>
        extends Fragment implements BasePresenter.View<T> {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFragment.class);

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tobatsu, container, false);

        tobatsuViewAdapter = newViewAdapter(inflater);
        tobatsuListView = (ListView) view.findViewById(R.id.tobatsuListView);
        tobatsuListView.setAdapter(tobatsuViewAdapter);

        Button updateButton = (Button) view.findViewById(R.id.updateTobatsuButton);
        updateButton.setOnClickListener(v -> presenter.onUpdateClick());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void tobatsuListUpdate(T list) {
        addDisplayItems(tobatsuViewAdapter, list);
    }

    protected abstract void addDisplayItems(A viewAdapter, T list);

    protected abstract A newViewAdapter(LayoutInflater inflater);

    protected abstract P newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character);

}
