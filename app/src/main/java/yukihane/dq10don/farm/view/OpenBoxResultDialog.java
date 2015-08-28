package yukihane.dq10don.farm.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

import rx.Observable;
import yukihane.dq10don.R;

/**
 * Created by yuki on 15/08/26.
 */
public class OpenBoxResultDialog extends DialogFragment {

    public static final String SUCCESS_COUNT = "SUCCESS_COUNT";
    public static final String FAIL_COUNT = "FAIL_COUNT";
    public static final String SUCCESS_MESSAGES = "SUCCESS_MESSAGES";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int successCount = getArguments().getInt(SUCCESS_COUNT, 0);
        int failCount = getArguments().getInt(FAIL_COUNT, 0);
        ArrayList<String> successMessages = getArguments().getStringArrayList(SUCCESS_MESSAGES);

        String summary = getString(R.string.text_openbox_summary,
                successCount, failCount);
        StringBuilder body = new StringBuilder(summary);
        Observable.from(successMessages)
                .forEach(message -> body.append("\n").append(message));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(body)
                .setPositiveButton(R.string.ok, (DialogInterface dialog, int id) -> {
                });
        return builder.create();
    }
}
