package yukihane.dq10don.bosscard.view;

import android.view.LayoutInflater;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yukihane.dq10don.R;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.account.StoredItem;
import yukihane.dq10don.base.view.BaseListFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.bosscard.model.BossCard;
import yukihane.dq10don.bosscard.model.BossCardListServiceFactory;
import yukihane.dq10don.bosscard.presenter.BossCardListPresenter;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListFragment extends BaseListFragment<
        List<Storage>,
        BossCardListPresenter.View,
        BossCardListPresenter,
        BossCardListViewAdapter>
        implements BossCardListPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardListFragment.class);

    @Override
    protected BossCardListViewAdapter newViewAdapter(LayoutInflater inflater) {
        return new BossCardListViewAdapter(inflater);
    }

    @Override
    protected BossCardListPresenter.View getSelf() {
        return this;
    }

    @Override
    protected BossCardListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return new BossCardListPresenter(new BossCardListServiceFactory(), dbHelperFactory, character);
    }


    @Override
    protected void addDisplayItems(BossCardListViewAdapter viewAdapter, List<Storage> list) {
        viewAdapter.clearItems();

        // あと192時間有効
        // あと 52分 有効
        // 「分」の場合は半角スペースが前後に入っている
        Pattern p = Pattern.compile("あと ?(\\d+)([^ ]+) ?有効");

        final String lastUpdateDateStr;
        if (!list.isEmpty()) {
            // storageは全部一度に取得するのでlastUpdateDate値はどれでもほぼ同じ
            Date lastUpdateDate = list.get(0).getLastUpdateDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd '('E')' H:mm");
            lastUpdateDateStr = sdf.format(lastUpdateDate);
        } else {
            lastUpdateDateStr = "";
        }

        TextView dateView = (TextView) getView().findViewById(R.id.updateDateView);
        String heading = getString(R.string.lastUpdate) + ": ";
        dateView.setText(heading + lastUpdateDateStr);

        List<BossCard> items = new ArrayList<>();
        for (Storage s : list) {
            for (StoredItem i : s.getSotredItems()) {
                String variousStr = i.getVariousStr();
                if (variousStr == null) {
                    continue;
                }
                Matcher m = p.matcher(variousStr);
                if (m.matches()) {
                    String numStr = m.group(1);
                    String unit = m.group(2);
                    int num = Integer.parseInt(numStr);

                    // 「時間」意外の単位は「分」しか無いはず…
                    if ("時間".equals(unit)) {
                        //「残り1時間」の表示は、実際には1時間以上2時間未満のの頃時間があるので+1しておく
                        num = (num + 1) * 60;
                    }

                    BossCard bc = new BossCard(i.getItemName(),
                            s.getStorageName(), s.getLastUpdateDate(), num);

                    LOGGER.debug("{}: {} '{}'", i.getItemName(), num, unit);
                    items.add(bc);
                } else {
                    LOGGER.debug("NOT MATCH: {}, {}", i.getItemName(), i.getVariousStr());
                }
            }
        }

        Collections.sort(items, (BossCard lhs, BossCard rhs) -> {
            return lhs.getCalculatedLeftMinites() - rhs.getCalculatedLeftMinites();
        });

        for (BossCard item : items) {
            viewAdapter.addItem(null, item);
        }

        viewAdapter.notifyChanged();

    }
}
