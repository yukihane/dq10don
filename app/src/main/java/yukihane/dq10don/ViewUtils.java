package yukihane.dq10don;

import android.content.Context;

/**
 * Created by yuki on 15/07/22.
 */
public class ViewUtils {
    private ViewUtils() {
    }

    public static String getHappyServiceErrorMsg(Context context, int resultCode) {
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
    }
}
