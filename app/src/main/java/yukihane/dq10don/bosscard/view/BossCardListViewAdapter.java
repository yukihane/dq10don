package yukihane.dq10don.bosscard.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yukihane.dq10don.base.view.BaseViewAdapter;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListViewAdapter extends BaseViewAdapter<Void> {

    public BossCardListViewAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected void validate(Void type, Object displayTarget) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater, Void type, Object displayTarget) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected int getItemViewType(Void type) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
