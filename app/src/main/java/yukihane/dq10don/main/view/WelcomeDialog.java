package yukihane.dq10don.main.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import yukihane.dq10don.R;

/**
 * Created by yuki on 15/07/23.
 */
public class WelcomeDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.text_welcome)
                .setPositiveButton(R.string.ok, (DialogInterface dialog, int id) -> {
                });
        return builder.create();
    }
}
