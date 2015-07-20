package yukihane.dq10don.exception;

/**
 * Created by yuki on 15/07/20.
 */
public class AppException extends RuntimeException {

    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public AppException() {
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public AppException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace,
     * the specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable     the cause of this exception.
     */
    public AppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public AppException(Throwable throwable) {
        super(throwable);
    }
}
