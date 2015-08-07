package yukihane.dq10don.exception;

/**
 * アプリケーションで発生させる例外のベースクラス.
 */
public abstract class AppException extends Exception {

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
