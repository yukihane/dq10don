package yukihane.dq10don.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import yukihane.dq10don.R;

/**
 * Created by yuki on 15/07/08.
 */
public class TobatsuListAdapter extends ArrayAdapter<TobatsuItem> {
    public TobatsuListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_tobatsu, null);
        }

        TobatsuItem p = getItem(position);

        if (p != null) {
            TextView nameView = (TextView) v.findViewById(R.id.tobatsuTargetNameView);
            TextView conditionView = (TextView) v.findViewById(R.id.tobatsuConditionView);
            TextView pointView = (TextView) v.findViewById(R.id.tobatsuPointView);

            if (nameView != null) {
                nameView.setText(p.getTargetName());
            }

            if (conditionView != null) {
                conditionView.setText(p.getCondition());
            }

            if (pointView != null) {
                pointView.setText(String.valueOf(p.getPoint()));
            }
        }

        return v;
    }
}
