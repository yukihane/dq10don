package yukihane.dq10don.tobatsu.model;

/**
 * Created by yuki on 15/07/18.
 */
public class TobatsuServiceFactory {

    public TobatsuService getService() {
        return new TobatsuServiceImpl();
    }
}
