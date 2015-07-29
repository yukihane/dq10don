package yukihane.dq10don.base.view;

import android.database.DataSetObservable;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuki on 15/07/29.
 */
public abstract class BaseViewAdapter implements ListAdapter, SpinnerAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private final LayoutInflater inflater;
    private final List<Class<?>> types;
    private final List<Object> displayTargets;


    public BaseViewAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        types = new ArrayList<>();
        displayTargets = new ArrayList<>();
    }


    public abstract void clearItems();

    public abstract void addItem(Class<?> type, Object displayTarget);

    public abstract void notifyChanged();
}
