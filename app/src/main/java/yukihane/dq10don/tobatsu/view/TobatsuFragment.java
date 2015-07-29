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
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.ViewUtils;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.HappyServiceException;
import yukihane.dq10don.tobatsu.presenter.TobatsuPresenter;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuFragment extends Fragment implements TobatsuPresenter.View {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuFragment.class);

    private TobatsuPresenter presenter;
    private TobatsuViewAdapter tobatsuViewAdapter;
    private ListView tobatsuListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharacterDtoImpl character = getArguments().getParcelable(CHARACTER);
        presenter = new TobatsuPresenter(this, new DbHelperFactory(getActivity()), character);
        presenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tobatsu, container, false);

        tobatsuViewAdapter = new TobatsuViewAdapter(inflater);
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
    public void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list) {
        tobatsuViewAdapter.clearItems();

        long issuedDateNum = Long.parseLong(list.getIssuedDate());
        Date issuedDate = new Date(issuedDateNum);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd '('E')' H:mm", Locale.JAPAN);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

        String issuedDateStr = sdf.format(issuedDate);

        // 本当はリスト内にString.classデータとして表示するものだが,
        // 現状大国の1種類しか表示しないのでヘッダ部に出力する(見えやすいように)
        TextView issuedDateView = (TextView) getView().findViewById(R.id.issuedDateView);
        issuedDateView.setText(issuedDateStr);

        // ポイントが大きい物順に並び替え
        List<TobatsuItem> items = new ArrayList<>(list.getListItems());
        Collections.sort(items, (TobatsuItem lhs, TobatsuItem rhs) -> {
            return rhs.getPoint() - lhs.getPoint();
        });

        for (TobatsuItem item : items) {
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
    public void showMessage(HappyServiceException ex) {
        String text = ViewUtils.getHappyServiceErrorMsg(getActivity(), ex);
        showMessage(text);
    }

    @Override
    public void showMessage(String message) {
        LOGGER.error(message);
        Toast.makeText(getView().getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindSupportFragment(this, observable);
    }
}
