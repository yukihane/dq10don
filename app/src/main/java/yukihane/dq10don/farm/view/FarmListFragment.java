package yukihane.dq10don.farm.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import yukihane.dq10don.R;
import yukihane.dq10don.Utils;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.farm.model.FarmListServiceFactory;
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
    public void onDataUpdated(Farm data) {
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy/MM/dd '('E')' H:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd '('E')' H:mm");

        // 最終更新時刻
        String lastUpdateDateStr = ysdf.format(data.getLastUpdateDate());
        TextView updateDateView = (TextView) getView().findViewById(R.id.updateDateView);
        updateDateView.setText(lastUpdateDateStr);

        // 船旅
        String nextSailoutDt = data.getNextSailoutDt();
        String nextSailoutDtText;
        if (nextSailoutDt == null || nextSailoutDt.isEmpty()) {
            nextSailoutDtText = "";
        } else {
            nextSailoutDtText = sdf.format(Utils.parseDate(nextSailoutDt));
        }

        // デフォルトのテキスト色
        ColorStateList textColors = updateDateView.getTextColors();
        if (data.isUnanchorable()) {
            nextSailoutDtText += getText(R.string.unanchorable);
            textColors = ColorStateList.valueOf(Color.BLUE);
        }
        TextView nextSailoutDtView = (TextView) getView().findViewById(R.id.farmNextSailoutDt);
        nextSailoutDtView.setText(nextSailoutDtText);
        nextSailoutDtView.setTextColor(textColors);

        // 船旅報酬
        String nearLimitDt = data.getNearLimitDt();
        String nearLimitDtText;
        if (nearLimitDt == null || nearLimitDt.isEmpty()) {
            nearLimitDtText = "";
        } else {
            nearLimitDtText = sdf.format(Utils.parseDate(nearLimitDt));
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
            treasureboxListText = sdf.format(farmBoxNearestLimit)
                    + " (" + data.getFarmBoxSize() + ")";
        }
        TextView treasureboxListView = (TextView) getView().findViewById(R.id.farmTreasureboxList);
        treasureboxListView.setText(treasureboxListText);

        // 雑草
        String grassListText = String.valueOf(data.getFarmGrassSize());
        TextView grassListView = (TextView) getView().findViewById(R.id.farmGrassList);
        grassListView.setText(grassListText);

    }
}
