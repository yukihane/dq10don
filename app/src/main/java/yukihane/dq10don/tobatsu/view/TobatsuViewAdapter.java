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


    /**
     * Get the type of View that will be created by {@link #getView} for the specified item.
     *
     * @param position The position of the item within the adapter's data set whose view type we
     *                 want.
     * @return An integer representing the type of View. Two views should share the same type if one
     * can be converted to the other in {@link #getView}. Note: Integers must be in the
     * range 0 to {@link #getViewTypeCount} - 1. {@link #IGNORE_ITEM_VIEW_TYPE} can
     * also be returned.
     * @see #IGNORE_ITEM_VIEW_TYPE
     */
    @Override
    public int getItemViewType(int position) {
        Class<?> type = types.get(position);
        for (int i = 0; i < DISPLAYABLE_TYPES.length; i++) {
            if (type == DISPLAYABLE_TYPES[i]) {
                return i;
            }
        }

        throw new RuntimeException("unknown type: " + type + "(pos: " + position + ")");
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

    /**
     * @return true if this adapter doesn't contain any data.  This is used to determine
     * whether the empty view should be displayed.  A typical implementation will return
     * getCount() == 0 but since getCount() includes the headers and footers, specialized
     * adapters might want a different behavior.
     */
    @Override
    public boolean isEmpty() {
        return displayTargets.isEmpty();
    }

    /**
     * Indicates whether all the items in this adapter are enabled. If the
     * value returned by this method changes over time, there is no guarantee
     * it will take effect.  If true, it means all items are selectable and
     * clickable (there is no separator.)
     *
     * @return True if all items are enabled, false otherwise.
     * @see #isEnabled(int)
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     * Returns true if the item at the specified position is not a separator.
     * (A separator is a non-selectable, non-clickable item).
     * <p>
     * The result is unspecified if position is invalid. An {@link ArrayIndexOutOfBoundsException}
     * should be thrown in that case for fast failure.
     *
     * @param position Index of the item
     * @return True if the item is not a separator
     * @see #areAllItemsEnabled()
     */
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    /**
     * <p>Get a {@link View} that displays in the drop down popup
     * the data at the specified position in the data set.</p>
     *
     * @param position    index of the item whose view we want.
     * @param convertView the old view to reuse, if possible. Note: You should
     *                    check that this view is non-null and of an appropriate type before
     *                    using. If it is not possible to convert this view to display the
     *                    correct data, this method can create a new view.
     * @param parent      the parent that this view will eventually be attached to
     * @return a {@link View} corresponding to the data at the
     * specified position.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private static class TobatsuItemViewHolder {
        public TextView targetName;
        public TextView condition;
        public TextView point;
    }
}
