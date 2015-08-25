package yukihane.dq10don.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * @param <V> 自身が実装するView型.
 * @param <P> 対応するプレゼンターの型.
 */
public abstract class BaseFragment<
        T,
        V extends BasePresenter.View<T>,
        P extends BasePresenter<T, V, ?>>
        extends Fragment implements BasePresenter.View<T> {

    public static final String CHARACTER = "character";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFragment.class);

    private P presenter;

    protected final P getPresenter() {
        return presenter;
    }

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

        View view = inflater.inflate(R.layout.fragment_base, container, false);

        int contentResId = getContentResId();
        View contentView = inflater.inflate(contentResId, null);
        ((ViewGroup) view.findViewById(R.id.contentPlace)).addView(contentView);

        Button updateButton = (Button) view.findViewById(R.id.updateContentsButton);
        updateButton.setOnClickListener(v -> presenter.onUpdateClick());

        presenter.onCreateView(getSelf());

        return view;
    }

    protected abstract V getSelf();

    protected abstract int getContentResId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
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
    public void setLoadingState(boolean loading) {
        Button updateButton = (Button) getView().findViewById(R.id.updateContentsButton);
        if (loading) {
            updateButton.setText(R.string.loading);
        } else {
            updateButton.setText(R.string.reload);
        }
        updateButton.setEnabled(!loading);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindSupportFragment(this, observable);
    }

    protected abstract P newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character);

}
