package yukihane.dq10don.exception;

/**
 * Created by yuki on 15/07/25.
 */
public class AppException extends Exception {

    public AppException() {
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }

    public AppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AppException(Throwable throwable) {
        super(throwable);
    }

}
