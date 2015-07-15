package yukihane.dq10don;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.android.view.ViewObservable;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.debug.DebugLogFragment;
import yukihane.dq10don.view.TobatsuViewAdapter;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuFragment extends DebugLogFragment implements TobatsuPresenter.View {

    public static final String SQEXID = "sqexid";
    public static final String CHARACTER_NAME = "characterName";
    public static final String SMILE_UNIQ_NO = "smileUniqNo";

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragment.class);

    private TobatsuViewAdapter tobatsuViewAdapter;
    private ListView tobatsuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tobatsu, container, false);

        tobatsuViewAdapter = new TobatsuViewAdapter(inflater);
        tobatsuListView = (ListView) view.findViewById(R.id.tobatsuListView);
        tobatsuListView.setAdapter(tobatsuViewAdapter);

        return view;
    }

    @Override
    public void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list) {
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
    public void bindToList(Observable observable) {
        ViewObservable.bindView(tobatsuListView, observable);
    }
}
