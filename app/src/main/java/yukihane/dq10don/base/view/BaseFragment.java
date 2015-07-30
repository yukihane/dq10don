package yukihane.dq10don.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.ViewUtils;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.ErrorCode;
import yukihane.dq10don.exception.HappyServiceException;
import yukihane.dq10don.base.presenter.BasePresenter;

public abstract class BaseFragment<T, P extends BasePresenter, A extends BaseViewAdapter>
        extends Fragment implements BasePresenter.View<T> {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFragment.class);

    private P presenter;
    private A viewAdapter;
    private ListView listView;


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

        viewAdapter = newViewAdapter(inflater);
        listView = (ListView) view.findViewById(R.id.contentListView);
        listView.setAdapter(viewAdapter);

        Button updateButton = (Button) view.findViewById(R.id.updateContentsButton);
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
    public void setHeader(String sqexid, String smileUniqNo) {
        TextView sqexidView = (TextView) getView().findViewById(R.id.accountNameView);
        sqexidView.setText(sqexid);

        TextView smileUniqNoView = (TextView) getView().findViewById(R.id.smileUniqNoView);
        smileUniqNoView.setText(smileUniqNo);
    }

    @Override
    public void tobatsuListUpdate(T list) {
        addDisplayItems(viewAdapter, list);
    }

    @Override
    public void showMessage(HappyServiceException ex) {
        String text = ViewUtils.getHappyServiceErrorMsg(getActivity(), ex);
        showMessage(text);
    }

    @Override
    public void showMessage(int errorCode) {
        LOGGER.error("error: {}", errorCode);
        String message = ViewUtils.getErrorMsg(getActivity(), errorCode);
        showMessage(message);
    }

    private void showMessage(String message) {
        LOGGER.error(message);
        Toast.makeText(getView().getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindSupportFragment(this, observable);
    }

    protected abstract void addDisplayItems(A viewAdapter, T list);

    protected abstract A newViewAdapter(LayoutInflater inflater);

    protected abstract P newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character);

}
