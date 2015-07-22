package yukihane.dq10don.exception;

/**
 * Created by yuki on 15/07/22.
 */
public class HappyServiceException extends Exception {

    private final Integer resultCode;

    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public HappyServiceException() {
        super();
        this.resultCode = null;
    }

    public HappyServiceException(int resultCode) {
        super();
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        if (resultCode == null) {
            return super.getMessage();
        }
        return super.getMessage() + " (" + resultCode + ")";
    }

    public Integer getResultCode() {
        return resultCode;
    }
}
