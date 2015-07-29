package yukihane.dq10don.base.view;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

/**
 * Created by yuki on 15/07/29.
 */
public interface BaseViewAdapter extends ListAdapter, SpinnerAdapter {
    void clearItems();

    void addItem(Class<?> type, Object displayTarget);

    void notifyChanged();
}
