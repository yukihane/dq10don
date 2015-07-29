package yukihane.dq10don.tobatsu.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.NumberFormat;

import yukihane.dq10don.R;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.base.view.BaseViewAdapter;

/**
 * Created by yuki on 15/07/08.
 */
public class TobatsuViewAdapter extends BaseViewAdapter<Class<?>> {

    private static final Class<?> DISPLAYABLE_TYPES[] = {String.class, TobatsuItem.class};

    public TobatsuViewAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected void validate(Class<?> type, Object displayTarget) {
        if (type != displayTarget.getClass()) {
            throw new IllegalArgumentException("difference error type: "
                    + type + ", displayTarget: " + displayTarget.getClass());
        }

        boolean isDisplayableType = false;
        for (Class<?> c : DISPLAYABLE_TYPES) {
            if (c == type) {
                isDisplayableType = true;
                break;
            }
        }
        if (!isDisplayableType) {
            throw new IllegalArgumentException("type is not supported: " + type);
        }
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent,
                           LayoutInflater inflater, Class<?> type, Object displayTarget) {

        final View v;

        if (String.class == type) {
            final TextView textView;
            if (convertView == null) {
                v = inflater.inflate(R.layout.list_tobatsu_separater, null);
                textView = (TextView) v.findViewById(R.id.separaterView);
                v.setTag(textView);
            } else {
                v = convertView;
                textView = (TextView) v.getTag();
            }
            String obj = (String) displayTarget;
            textView.setText(obj);

            v.setBackgroundColor(Color.GRAY);
        } else if (TobatsuItem.class == type) {
            final TextView targetName;
            final TextView condition;
            final TextView point;
            if (convertView == null) {
                v = inflater.inflate(R.layout.list_tobatsu, null);
                targetName = (TextView) v.findViewById(R.id.tobatsuTargetNameView);
                condition = (TextView) v.findViewById(R.id.tobatsuConditionView);
                point = (TextView) v.findViewById(R.id.tobatsuPointView);

                final TobatsuItemViewHolder holder = new TobatsuItemViewHolder();
                holder.targetName = targetName;
                holder.condition = condition;
                holder.point = point;
                v.setTag(holder);
            } else {
                v = convertView;
                TobatsuItemViewHolder holder = (TobatsuItemViewHolder) v.getTag();
                targetName = holder.targetName;
                condition = holder.condition;
                point = holder.point;
            }
            TobatsuItem obj = (TobatsuItem) displayTarget;
            targetName.setText(obj.getMonsterName());
            condition.setText(obj.getArea() + ", " + obj.getCount());

            NumberFormat nf = NumberFormat.getNumberInstance();
            point.setText(nf.format(obj.getPoint()));

            if (position % 2 == 0) {
                v.setBackgroundColor(Color.LTGRAY);
            } else {
                v.setBackgroundColor(Color.WHITE);
            }
        } else {
            throw new RuntimeException("unexcpected type: " + type);
        }

        return v;
    }

    @Override
    public int getItemViewType(Class<?> type) {
        for (int i = 0; i < DISPLAYABLE_TYPES.length; i++) {
            if (type == DISPLAYABLE_TYPES[i]) {
                return i;
            }
        }

        throw new RuntimeException("unknown type: " + type);
    }

    /**
     * <p>
     * Returns the number of types of Views that will be created by
     * {@link #getView}. Each type represents a set of views that can be
     * converted in {@link #getView}. If the adapter always returns the same
     * type of View for all items, this method should return 1.
     * </p>
     * <p>
     * This method will only be called when when the adapter is set on the
     * the {@link AdapterView}.
     * </p>
     *
     * @return The number of types of Views that will be created by this adapter
     */
    @Override
    public int getViewTypeCount() {
        return DISPLAYABLE_TYPES.length;
    }

    private static class TobatsuItemViewHolder {
        public TextView targetName;
        public TextView condition;
        public TextView point;
    }
}
