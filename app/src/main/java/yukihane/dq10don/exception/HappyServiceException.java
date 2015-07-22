package yukihane.dq10don.exception;

/**
 * Created by yuki on 15/07/22.
 */
public class HappyServiceException extends Exception {

    private final int resultCode;

    public HappyServiceException(int resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getMessage() {
        return "Happy Service Error Return CODE: " + resultCode;
    }
}
