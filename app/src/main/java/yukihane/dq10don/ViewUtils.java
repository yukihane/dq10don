package yukihane.dq10don;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import retrofit.RetrofitError;
import retrofit.client.Response;
import yukihane.dq10don.exception.ErrorCode;
import yukihane.dq10don.exception.HappyServiceException;

import static yukihane.dq10don.communication.HappyServiceResultCode.HOUSEBAZAAR_UNSET;
import static yukihane.dq10don.communication.HappyServiceResultCode.INGAME;
import static yukihane.dq10don.communication.HappyServiceResultCode.NORMAL;
import static yukihane.dq10don.communication.HappyServiceResultCode.TOBATSUQUEST_NEVER_ACCEPTED;
import static yukihane.dq10don.communication.HappyServiceResultCode.TOBATSU_SLOW_SERVICE;

/**
 * Created by yuki on 15/07/22.
 */
public class ViewUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewUtils.class);

    private static final String ADMOB_PROPERTIES_FILE = "assets/admob.properties";
    private static final String KEY_ADUNITID = "adUnitId";

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
                    case NORMAL:
                        // 0 は正常コード
                        return "";
                    case INGAME:
                        return context.getString(R.string.happy_106);
                    case HOUSEBAZAAR_UNSET:
                        return context.getString(R.string.happy_12009);
                    case TOBATSU_SLOW_SERVICE:
                        return context.getString(R.string.happy_22001);
                    case TOBATSUQUEST_NEVER_ACCEPTED:
                        // 討伐が受けられるようになるクエストをこなしていない
                        return context.getString(R.string.happy_22002);
                    default:
                        return context.getString(R.string.happy_unknown, resultCode);
                }
            case HTTP:
                // http://qiita.com/yyaammaa/items/df67dc21ebdadfab3213
                RetrofitError re = ex.getCause();
                RetrofitError.Kind kind = re.getKind();
                switch (kind) {
                    case HTTP:
                        Response resp = re.getResponse();
                        int status = (resp != null) ? resp.getStatus() : -1;
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

    public static String getErrorMsg(Context context, int errorCode, Object... formatArgs) {

        switch (errorCode) {
            case ErrorCode.REPORTED:
                return context.getString(R.string.error_already_reported);
            case ErrorCode.ERROR:
            default:
                return context.getString(R.string.text_error);
        }
    }


    public static AdView createAdView(Context context) {
        AdView adView = new AdView(context);

        URL adprops = ViewUtils.class.getClassLoader().getResource(ADMOB_PROPERTIES_FILE);
        InputStream is = null;
        try {
            is = adprops.openStream();
            Properties props = new Properties();
            props.load(is);
            String adUnitId = props.getProperty(KEY_ADUNITID);
            LOGGER.debug("addUnitId: {}", adUnitId);
            adView.setAdUnitId(adUnitId);
        } catch (IOException e) {
            LOGGER.error("admob.properties error", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        adView.setAdSize(AdSize.SMART_BANNER);

        return adView;
    }

    public static AdRequest.Builder createAdRequestBuilder() {
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addKeyword("ドラゴンクエスト")
                .addKeyword("DQX")
                .addKeyword("WiiU")
                .addKeyword("3DS")
                .addKeyword("任天堂")
                .addKeyword("スクウェア・エニックス");

        return builder;
    }
}
