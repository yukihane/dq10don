package yukihane.dq10don.base.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuki on 15/07/29.
 */
public abstract class BaseViewAdapter<T> implements ListAdapter, SpinnerAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private final LayoutInflater inflater;
    private final List<T> types;
    private final List<Object> displayTargets;


    public BaseViewAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        types = new ArrayList<>();
        displayTargets = new ArrayList<>();
    }

    public void addItem(T type, Object displayTarget) {
        validate(type, displayTarget);
        types.add(type);
        displayTargets.add(displayTarget);
    }


    public void clearItems() {
        types.clear();
        displayTargets.clear();
    }

    /**
     * Register an observer that is called when changes happen to the data used by this adapter.
     *
     * @param observer the object that gets notified when the data set changes.
     */
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    /**
     * Unregister an observer that has previously been registered with this
     * adapter via {@link #registerDataSetObserver}.
     *
     * @param observer the object to unregister.
     */
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return displayTargets.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return displayTargets.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Indicates whether the item ids are stable across changes to the
     * underlying data.
     *
     * @return True if the same id always refers to the same object.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    protected abstract void validate(T type, Object displayTarget);
}
