package yukihane.dq10don.tobatsu.view;

import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import yukihane.dq10don.account.TobatsuItem;

/**
 * Created by yuki on 15/07/29.
 */
public interface BaseViewAdapter extends ListAdapter, SpinnerAdapter {
    void clearItems();

    void addItem(Class<?> type, Object displayTarget);

    void notifyChanged();
}
