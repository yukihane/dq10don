package yukihane.dq10don.tos.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import yukihane.dq10don.tos.model.TosPrefUtils;

/**
 * Created by yuki on 2015/08/15.
 */
public class TosPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TosPresenter.class);
    private static final NullView NULL_VIEW = new NullView();
    private static final String LS = System.getProperty("line.separator");

    private View view;
    private TosPrefUtils prefUtils;

    public TosPresenter(View view, TosPrefUtils prefUtils) {
        this.view = view;
        this.prefUtils = prefUtils;
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
        view = NULL_VIEW;
        prefUtils = null;
    }

    public void onAgree() {
        int currentVersion = prefUtils.getCurrentVersion();
        prefUtils.setAgreedVersion(currentVersion);
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
