package yukihane.dq10don.twitter;

/**
 * Twitter投稿に関する設定を保存するPreferences操作クラス.
 * <a href="https://github.com/google/iosched/blob/master/android/src/main/java/com/google/samples/apps/iosched/util/PrefUtils.java">
 * こちらのクラス</a>を参考に作成.
 * (<a href="http://qiita.com/operandoOS/items/8af20ac09a9d6acb075e">こちら</a>からのリンク)
 */
public final class PrefUtils {
    public static final String PREF_NAME = "twitter";
    public static final String USER_ID = "accessToken.userId";
    public static final String SCREEN_NAME = "accessToken.screenName";
    public static final String TOKEN = "accessToken.token";
    public static final String TOKEN_SECRET = "accessToken.tokenSecret";

    private PrefUtils() {
    }
}
