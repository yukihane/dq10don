package yukihane.dq10don.exception;

import retrofit.RetrofitError;

/**
 * HappyServiceにリクエストし失敗に終わった場合に送出される例外です.
 * {@link #getType()} によって例外の種類を取得できます.
 * {@link yukihane.dq10don.exception.HappyServiceException.Type#SERVERSIDE} の場合,
 * {@link #getResultCode()} でエラー原因が判別できます.
 * {@link yukihane.dq10don.exception.HappyServiceException.Type#HTTP} の場合,
 * {@link #getCause()} によってRetrofitErrorが取得できますのでそれで判別します.
 */
public class HappyServiceException extends AppException {

    private final Type type;

    private final int resultCode;

    private final RetrofitError error;

    public HappyServiceException(int resultCode) {
        super();
        this.type = Type.SERVERSIDE;
        this.resultCode = resultCode;
        this.error = null;
    }

    public HappyServiceException(String detailMessage, RetrofitError throwable) {
        super(detailMessage, throwable);
        this.type = Type.HTTP;
        this.resultCode = -1;
        this.error = throwable;
    }

    public Type getType() {
        return type;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public RetrofitError getCause() {
        return error;
    }

    @Override
    public String getMessage() {
        switch (type) {
            case SERVERSIDE:
                return "Happy Service Error Return CODE: " + resultCode;
            case HTTP:
            default:
                return super.getMessage();
        }
    }

    /**
     * 例外の種類です.
     */
    public enum Type {
        /**
         * サーバーへは正常に到達しているが, サーバーがエラーを返した.
         */
        SERVERSIDE,
        /**
         * ネットワークの問題(サーバーに到達していない, あるいは到達したがstatus codeが200以外).
         */
        HTTP,
    }
}
