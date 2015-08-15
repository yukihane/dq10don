package yukihane.dq10don.tos.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

/**
 * Created by yuki on 2015/08/15.
 */
public class TosPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TosPresenter.class);
    private static final NullView NULL_VIEW = new NullView();
    private static final String LS = System.getProperty("line.separator");

    private View view;
    private DbHelper dbHelper;

    public TosPresenter(View view, DbHelperFactory dbHelperFactory) {
        this.view = view;
        this.dbHelper = dbHelperFactory.create();
    }

    public void onCreate() {

        BufferedReader reader = null;
        try {
            URL res = TosPresenter.class.getClassLoader().getResource("assets/tos.txt");
            reader = new BufferedReader(new InputStreamReader(res.openStream()));
            StringBuilder sb = new StringBuilder();

            String str = reader.readLine();
            while (str != null) {
                sb.append(str).append(LS);
                str = reader.readLine();
            }

            view.displayTos(sb.toString());
            view.enableButtons();

        } catch (IOException e) {
            LOGGER.error("terms of service display error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("stream close error", e);
                }
            }
        }
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        view = NULL_VIEW;
        dbHelper = null;
    }

    public void onAgree() {
        // TODO 同意したことを永続化
    }

    public interface View {
        void displayTos(String text);

        void enableButtons();
    }

    private static final class NullView implements View {
        @Override
        public void displayTos(String text) {
        }

        @Override
        public void enableButtons() {
        }
    }
}
