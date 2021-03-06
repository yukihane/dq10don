package yukihane.dq10don.sqexid.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.R;

public class RemoveConfirmDialog extends DialogFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveConfirmDialog.class);

    private Listener listener;

    public RemoveConfirmDialog() {
        LOGGER.debug("default constructor called");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RemoveConfirmDialog.Listener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String userId = getArguments().getString("userId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.text_confirm_delete_account, userId))
                .setPositiveButton(R.string.yes, (DialogInterface dialog, int id) -> {
                    listener.onRemove(userId);
                })
                .setNegativeButton(R.string.cancel, (DialogInterface dialog, int id) -> {
                });
        return builder.create();
    }

    public void setUserId(String userId) {
        Bundle b = getBundle();
        b.putString("userId", userId);
        setArguments(b);
    }

    private Bundle getBundle() {
        Bundle b = getArguments();
        if (b != null) {
            return b;
        }
        return new Bundle();
    }

    public interface Listener {
        void onRemove(String userId);
    }
}
