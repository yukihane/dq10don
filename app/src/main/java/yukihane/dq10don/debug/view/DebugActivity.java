package yukihane.dq10don.debug.view;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.io.File;

import yukihane.dq10don.R;

public class DebugActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        File toDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        TextView downloadDirView = (TextView) findViewById(R.id.downloadDirView);
        downloadDirView.setText(toDir.getAbsolutePath());

    }
}
