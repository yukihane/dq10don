package yukihane.dq10don;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveConfirmDialog extends DialogFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveConfirmDialog.class);

    public RemoveConfirmDialog() {
        LOGGER.debug("default constructor called");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String userId = getArguments().getString("userId");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(userId + " を削除します")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Listener listener = (Listener) getActivity();
                        listener.onRemove(userId);
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
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
