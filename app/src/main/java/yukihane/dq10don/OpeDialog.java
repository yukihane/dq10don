package yukihane.dq10don;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpeDialog extends DialogFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpeDialog.class);

    public OpeDialog() {
        LOGGER.debug("default constuctor called");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence[] items = {getString(R.string.relogin), getString(R.string.remove)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Bundle b = getArguments();
                String userId = b.getString("userId");
                Listener listener = (Listener) getActivity();
                switch (which) {
                    case 0:
                        LOGGER.debug("RELOGIN");
                        listener.onRelogin(userId);
                        break;
                    case 1:
                        LOGGER.debug("REMOVE CONFIRM");
                        listener.removeConfirm(userId);
                        break;
                    default:
                        break;
                }
            }
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
        void onRelogin(String userId);

        void removeConfirm(String userId);
    }
}
