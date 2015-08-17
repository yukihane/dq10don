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
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * サービスへ表示対象を要求し, キャラクターごとに結果をキャラクターごとに表示する機能の
 * Viewの実装ベース.
 *
 * @param <T> 表示対象とするデータの型.
 * @param <P> 対応するプレゼンターの型.
 * @param <A> 対応するViewAdapterの型.
 */
public abstract class BaseListFragment<T, P extends BasePresenter<T, ?>, A extends BaseViewAdapter<?>>
        extends BaseFragment<T, P> implements BasePresenter.View<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseListFragment.class);

    private A viewAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewAdapter = newViewAdapter(inflater);
        listView = (ListView) view.findViewById(R.id.contentListView);
        listView.setAdapter(viewAdapter);

        return view;
    }

    @Override
    protected int getContentResId() {
        return R.layout.base_content_list;
    }

    @Override
    public void onListUpdated(T list) {
        addDisplayItems(viewAdapter, list);
    }

    protected abstract void addDisplayItems(A viewAdapter, T list);

    protected abstract A newViewAdapter(LayoutInflater inflater);
}
