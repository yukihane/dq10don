package yukihane.dq10don.tobatsu.view;

import android.view.LayoutInflater;
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
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.view.BaseListFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tobatsu.model.TobatsuListServiceFactory;
import yukihane.dq10don.tobatsu.presenter.TobatsuListPresenter;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuListFragment
        extends BaseListFragment<TobatsuList, TobatsuListPresenter, TobatsuListViewAdapter>
        implements BasePresenter.View<TobatsuList> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuListFragment.class);

    @Override
    protected TobatsuListViewAdapter newViewAdapter(LayoutInflater inflater) {
        return new TobatsuListViewAdapter(inflater);
    }

    @Override
    protected TobatsuListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return new TobatsuListPresenter(new TobatsuListServiceFactory(), dbHelperFactory, character);
    }

    @Override
    protected void addDisplayItems(TobatsuListViewAdapter tobatsuViewAdapter, TobatsuList list) {
        tobatsuViewAdapter.clearItems();

        long issuedDateNum = Long.parseLong(list.getIssuedDate());
        Date issuedDate = new Date(issuedDateNum);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd '('E')' H:mm", Locale.JAPAN);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

        String issuedDateStr = sdf.format(issuedDate);

        // 本当はリスト内にString.classデータとして表示するものだが,
        // 現状大国の1種類しか表示しないのでヘッダ部に出力する(見えやすいように)
        TextView issuedDateView = (TextView) getView().findViewById(R.id.updateDateView);
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
}
