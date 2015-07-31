package yukihane.dq10don.bosscard.view;

import android.view.LayoutInflater;
import android.widget.TextView;

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
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.view.BaseFragment;
import yukihane.dq10don.base.view.CharacterDtoImpl;
import yukihane.dq10don.bosscard.model.BossCardListServiceFactory;
import yukihane.dq10don.bosscard.presenter.BossCardListPresenter;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListFragment extends BaseFragment<List<Storage>, BossCardListPresenter, BossCardListViewAdapter>
        implements BasePresenter.View<List<Storage>> {

    @Override
    protected BossCardListViewAdapter newViewAdapter(LayoutInflater inflater) {
        return new BossCardListViewAdapter(inflater);
    }

    @Override
    protected BossCardListPresenter newPresenter(DbHelperFactory dbHelperFactory, CharacterDtoImpl character) {
        return new BossCardListPresenter(this, new BossCardListServiceFactory(), dbHelperFactory, character);
    }


    @Override
    protected void addDisplayItems(BossCardListViewAdapter viewAdapter, List<Storage> list) {
        viewAdapter.clearItems();

        Pattern p = Pattern.compile("あと(\\d+)(.+)有効");

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd '('E')' H:mm");
        String nowStr = sdf.format(now);

        TextView dateView = (TextView) getView().findViewById(R.id.updateDateView);
        String lastUpdateStr = getString(R.string.lastUpdate) + ": ";
        dateView.setText(lastUpdateStr + nowStr);

        List<StoredItem> items = new ArrayList<>();
        for (Storage s : list) {
            for (StoredItem i : s.getSotredItems()) {
                String variousStr = i.getVariousStr();
                if (variousStr == null) {
                    continue;
                }
                Matcher m = p.matcher(variousStr);
                if (m.matches()) {
                    items.add(i);
                }
            }
        }

        Collections.sort(items, (StoredItem lhs, StoredItem rhs) -> {
            Matcher lMatcher = p.matcher(lhs.getVariousStr());

            final int lNum;
            final String lUnit;
            if (lMatcher.matches()) {
                lNum = Integer.parseInt(lMatcher.group(1));
                lUnit = lMatcher.group(2);
            } else {
                return 1;
            }

            Matcher rMatcher = p.matcher(rhs.getVariousStr());
            final int rNum;
            final String rUnit;
            if (rMatcher.matches()) {
                rNum = Integer.parseInt(rMatcher.group(1));
                rUnit = lMatcher.group(2);
            } else {
                return -1;
            }

            // unit(単位)は「時間」と、あとおそらく「分」が存在する
            if (lUnit.equals(rUnit)) {
                return lNum - rNum;
            }
            if (lUnit.equals("時間")) {
                return 1;
            }
            return -1;
        });

        for (StoredItem item : items) {
            viewAdapter.addItem(null, item);
        }

        viewAdapter.notifyChanged();

    }
}
