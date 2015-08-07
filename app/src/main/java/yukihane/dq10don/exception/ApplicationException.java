package yukihane.dq10don.exception;


import lombok.Getter;

public class ApplicationException extends AppException {

    @Getter
    private final int errorCode;

    public ApplicationException(int errorCode) {
        super("application error: " + errorCode);
        this.errorCode = errorCode;
    }
}
