package yukihane.dq10don.bosscard.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yukihane.dq10don.R;
import yukihane.dq10don.account.StoredItem;
import yukihane.dq10don.base.view.BaseViewAdapter;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListViewAdapter extends BaseViewAdapter<Void> {

    private static final Pattern PATTERN = Pattern.compile("あと(\\d+)(.+)有効");


    public BossCardListViewAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected void validate(Void type, Object displayTarget) {
        // 1種類しか表示しないので特に処理することはない
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater, Void type, Object displayTarget) {

        final View v;

        final TextView cardName;
        final TextView storage;
        final TextView leftTime;
        if (convertView == null) {
            v = inflater.inflate(R.layout.list_boss_card, null);
            cardName = (TextView) v.findViewById(R.id.bossCardNameView);
            storage = (TextView) v.findViewById(R.id.bossCardStorageView);
            leftTime = (TextView) v.findViewById(R.id.bossCardTimeView);

            final ViewHolder holder = new ViewHolder();
            holder.cardName = cardName;
            holder.storage = storage;
            holder.leftTime = leftTime;
            v.setTag(holder);
        } else {
            v = convertView;
            ViewHolder holder = (ViewHolder) v.getTag();
            cardName = holder.cardName;
            storage = holder.storage;
            leftTime = holder.leftTime;
        }
        StoredItem obj = (StoredItem) displayTarget;
        cardName.setText(obj.getItemName());
        storage.setText(obj.getStorage().getStorageName());

        Matcher m = PATTERN.matcher(obj.getVariousStr());
        m.matches();
        int timeNum = Integer.parseInt(m.group(1));

        NumberFormat nf = NumberFormat.getNumberInstance();
        leftTime.setText(nf.format(timeNum) + m.group(2));

        if (position % 2 == 0) {
            v.setBackgroundColor(Color.LTGRAY);
        } else {
            v.setBackgroundColor(Color.WHITE);
        }


        return v;
    }

    @Override
    protected int getItemViewType(Void type) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    private static class ViewHolder {
        public TextView cardName;
        public TextView storage;
        public TextView leftTime;
    }
}
