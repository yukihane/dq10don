package yukihane.dq10don;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuki on 15/07/09.
 */
public class Application extends android.app.Application {

    private Logger logger;

    @Override
    public void onCreate() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("Application#onCreate called");
        super.onCreate();
    }
}
