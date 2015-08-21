package yukihane.dq10don.communication.utils;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import lombok.Getter;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by yuki on 15/08/21.
 */
public class SessionProperties {

    @Getter
    private final String sessionId;
    @Getter
    private final long webPcNo;

    public SessionProperties() {
        try {
            InputStream propStream = SessionProperties.class.getClassLoader().getResourceAsStream("session.properties");
            Properties sessionInfo = new Properties();
            sessionInfo.load(new InputStreamReader(propStream));

            sessionId = sessionInfo.getProperty("sessionId", "");
            if (sessionId.isEmpty()) {
                throw new IOException("sessionId is not described in session.properties.");
            }
            webPcNo = Long.parseLong(sessionInfo.getProperty("webPcNo", "0"));
            if (webPcNo == 0L) {
                throw new IOException("webPcNo is not described in session.properties.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
