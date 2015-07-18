package yukihane.dq10don;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.debug.DebugLogFragment;
import yukihane.dq10don.view.CharacterDto;
import yukihane.dq10don.view.TobatsuViewAdapter;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuFragment extends DebugLogFragment implements TobatsuPresenter.View {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragment.class);

    private TobatsuPresenter presenter;
    private TobatsuViewAdapter tobatsuViewAdapter;
    private ListView tobatsuListView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tobatsu, container, false);

        tobatsuViewAdapter = new TobatsuViewAdapter(inflater);
        tobatsuListView = (ListView) view.findViewById(R.id.tobatsuListView);
        tobatsuListView.setAdapter(tobatsuViewAdapter);

        CharacterDto character = getArguments().getParcelable(CHARACTER);
        presenter = new TobatsuPresenter(this, new DbHelperFactory(getActivity()), character);

        Button updateButton = (Button) view.findViewById(R.id.updateTobatsuButton);
        updateButton.setOnClickListener(v -> presenter.onUpdateClick());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list) {
        tobatsuViewAdapter.clearItems();
        tobatsuViewAdapter.addItem(String.class, "受注中");
        for (TobatsuItem item : list.getAcceptings()) {
            tobatsuViewAdapter.addItem(TobatsuItem.class, item);
        }

        tobatsuViewAdapter.addItem(String.class, "リスト");
        for (TobatsuItem item : list.getListings()) {
            tobatsuViewAdapter.addItem(TobatsuItem.class, item);
        }

        tobatsuViewAdapter.notifyChanged();
    }

    @Override
    public void setHeader(String sqexid, String smileUniqNo) {
        TextView sqexidView = (TextView) getView().findViewById(R.id.accountNameView);
        sqexidView.setText(sqexid);

        TextView smileUniqNoView = (TextView) getView().findViewById(R.id.smileUniqNoView);
        smileUniqNoView.setText(smileUniqNo);
    }


    @Override
    public void bindToList(Observable observable) {
        ViewObservable.bindView(tobatsuListView, observable);
    }
}
