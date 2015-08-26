package yukihane.dq10don.farm.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import yukihane.dq10don.R;
import yukihane.dq10don.Utils;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.model.FarmListServiceFactory;
import yukihane.dq10don.farm.model.MowResult;
import yukihane.dq10don.farm.presenter.FarmListPresenter;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListFragment extends BaseFragment<
        Farm,
        FarmListPresenter.View,
        FarmListPresenter>
        implements FarmListPresenter.View {

    @Override
    protected FarmListPresenter.View getSelf() {
        return this;
    }

    @Override
    protected int getContentResId() {
        return R.layout.base_content_farm;
    }

    @Override
    protected FarmListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return new FarmListPresenter(new FarmListServiceFactory(), dbHelperFactory, character);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        View mowButton = view.findViewById(R.id.farmMowButton);
        mowButton.setOnClickListener(v -> getPresenter().mowGrasses());

        View openBoxButton = view.findViewById(R.id.farmOpenBoxButton);
        openBoxButton.setOnClickListener(v -> getPresenter().openBoxes());

        return view;
    }

    @Override
    public void onDataUpdated(Farm data) {
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy/MM/dd '('E')' H:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");

        // 最終更新時刻
        String lastUpdateDateStr = ysdf.format(data.getLastUpdateDate());
        TextView updateDateView = (TextView) getView().findViewById(R.id.updateDateView);
        updateDateView.setText(lastUpdateDateStr);

        // 船旅
        String nextSailoutDt = data.getNextSailoutDt();
        String nextSailoutDtText;
        // デフォルトのテキスト色
        ColorStateList textColors = updateDateView.getTextColors();
        if (nextSailoutDt == null || nextSailoutDt.isEmpty()) {
            nextSailoutDtText = "";
        } else {
            Date date = Utils.parseDate(nextSailoutDt);
            nextSailoutDtText = sdf.format(date);
            if (date.compareTo(data.getLastUpdateDate()) <= 0) {
                nextSailoutDtText += " " + getText(R.string.unanchorable);
                textColors = ColorStateList.valueOf(Color.BLUE);
            }
        }
        TextView nextSailoutDtView = (TextView) getView().findViewById(R.id.farmNextSailoutDt);
        nextSailoutDtView.setText(nextSailoutDtText);
        nextSailoutDtView.setTextColor(textColors);

        // 船旅報酬
        String nearLimitDt = data.getNearLimitDt();
        String nearLimitDtText;
        if (nearLimitDt == null || nearLimitDt.isEmpty()) {
            nearLimitDtText = getString(R.string.nonexist);
        } else {
            nearLimitDtText = sdf.format(Utils.parseDate(nearLimitDt))
                    + " [" + data.getUngetCount() + getString(R.string.text_unit) + "]";
        }
        TextView nearLimitDtView = (TextView) getView().findViewById(R.id.farmNearLimitDt);
        nearLimitDtView.setText(nearLimitDtText);

        // 青宝箱
        int isFriendBlueBoxId = data.isFriendBlueBox() ? R.string.exist : R.string.nonexist;
        TextView isFriendBlueBoxView = (TextView) getView().findViewById(R.id.farmIsFriendBlueBox);
        isFriendBlueBoxView.setText(getString(isFriendBlueBoxId));

        // 宝箱
        Date farmBoxNearestLimit = data.getFarmBoxNearestLimit();
        String treasureboxListText;
        if (farmBoxNearestLimit == null) {
            treasureboxListText = getString(R.string.nonexist);
        } else {
            String unit = getString(R.string.text_unit);
            String over = (data.isMoreRebirthTreasureBox()) ? getString(R.string.text_over) : "";
            treasureboxListText = sdf.format(farmBoxNearestLimit)
                    + " [" + data.getFarmBoxSize() + unit + over + "]";
        }
        TextView treasureboxListView = (TextView) getView().findViewById(R.id.farmTreasureboxList);
        treasureboxListView.setText(treasureboxListText);

        // 雑草
        String grassListText = String.valueOf(data.getFarmGrassSize());
        TextView grassListView = (TextView) getView().findViewById(R.id.farmGrassList);
        grassListView.setText(grassListText);

    }

    @Override
    public void setLoadingState(boolean loading) {
        super.setLoadingState(loading);

        Button mowButton = (Button) getView().findViewById(R.id.farmMowButton);
        mowButton.setEnabled(!loading);
    }

    @Override
    public void onGrassMowed(MowResult res) {
        String text = getString(R.string.mowed,
                res.getMedalCount(), res.getExpCount(), res.getOtherCount());
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
}
