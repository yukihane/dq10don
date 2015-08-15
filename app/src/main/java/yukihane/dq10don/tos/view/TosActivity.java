package yukihane.dq10don.tos.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import yukihane.dq10don.R;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tos.presenter.TosPresenter;

/**
 * 利用規約(Terms of Service)を表示するアクティビティ.
 */
public class TosActivity extends AppCompatActivity implements TosPresenter.View {

    private TosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos);


        presenter = new TosPresenter(this, new DbHelperFactory(this));
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void displayTos(String text) {
        TextView textView = (TextView) findViewById(R.id.tosText);
        textView.setText(text);
    }

    @Override
    public void enableButtons() {
        View agreeButton = findViewById(R.id.tosAgreeButton);
        agreeButton.setEnabled(true);
        agreeButton.setOnClickListener((v) -> {
            presenter.onAgree();
            setResult(RESULT_OK);
            finish();
        });

        View disagreeButton = findViewById(R.id.tosDiscardButton);
        disagreeButton.setEnabled(true);
        disagreeButton.setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
