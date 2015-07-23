package yukihane.dq10don;

import android.content.Context;

import retrofit.RetrofitError;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/22.
 */
public class ViewUtils {
    private ViewUtils() {
    }

    public static String getHappyServiceErrorMsg(Context context, HappyServiceException ex) {
        if (ex == null) {
            return context.getString(R.string.text_error);
        }
        switch (ex.getType()) {

            case SERVERSIDE:
                int resultCode = ex.getResultCode();
                switch (resultCode) {
                    case 0:
                        // 0 は正常コード
                        return "";
                    case 106:
                        return context.getString(R.string.happy_106);
                    case 12009:
                        return context.getString(R.string.happy_12009);
                    case 22001:
                        return context.getString(R.string.happy_22001);
                    default:
                        return context.getString(R.string.happy_unknown, resultCode);
                }
            case HTTP:
                // http://qiita.com/yyaammaa/items/df67dc21ebdadfab3213
                RetrofitError re = ex.getCause();
                RetrofitError.Kind kind = re.getKind();
                switch (kind) {
                    case HTTP:
                        int status = re.getResponse().getStatus();
                        switch (status) {
                            case 401:
                                return context.getString(R.string.http_401);
                            default:
                                return context.getString(R.string.http_other,
                                        String.valueOf(status));
                        }
                    default:
                        return context.getString(R.string.http_other, kind.toString());
                }
            default:
                return context.getString(R.string.text_error);
        }
    }
}
