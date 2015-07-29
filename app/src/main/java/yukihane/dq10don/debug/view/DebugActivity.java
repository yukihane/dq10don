package yukihane.dq10don.debug.view;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.debug.presenter.DebugPresenter;

public class DebugActivity extends AppCompatActivity implements DebugPresenter.View {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugActivity.class);

    private DebugPresenter presenter;

    private File filesDir;
    private File downloadDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        filesDir = getFilesDir();
        downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        presenter = new DebugPresenter(this);
        presenter.onCreate();
    }


    @Override
    public void setEventListener() {
        Button outputLogBtn = (Button) findViewById(R.id.outputLogButtn);
        outputLogBtn.setOnClickListener(v -> {
            presenter.exportLog(filesDir, downloadDir);
        });
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindActivity(this, observable);
    }

    @Override
    public void showMessage(DebugPresenter.Message message) {
        final String text;
        switch (message) {
            case COMPLETED_EXPORTFILE:
                text = getString(R.string.completed_exportfile);
                break;
            default:
                LOGGER.error("message is not defined: {}", message);
                return;
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDownloadDirectory() {
        TextView downloadDirView = (TextView) findViewById(R.id.downloadDirView);
        downloadDirView.setText(downloadDir.getAbsolutePath());
    }

}
