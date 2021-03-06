package yukihane.dq10don.settings.view;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yukihane.dq10don.Utils;

/**
 * Twitter投稿に関する設定を保存するPreferences操作クラス.
 * <a href="https://github.com/google/iosched/blob/master/android/src/main/java/com/google/samples/apps/iosched/util/PrefUtils.java">
 * こちらのクラス</a>を参考に作成.
 * (<a href="http://qiita.com/operandoOS/items/8af20ac09a9d6acb075e">こちら</a>からのリンク)
 */
public final class TwitterPrefUtils {
    /**
     * Preferencesの名称
     */
    public static final String PREF_NAME = "twitter";


    public static final String USER_ID = "accessToken.userId";
    public static final String SCREEN_NAME = "accessToken.screenName";
    public static final String TOKEN = "accessToken.token";
    public static final String TOKEN_SECRET = "accessToken.tokenSecret";

    public static final String TOBATSU_TWEET_CHARACTERS = "tobatsu.characters";

    // pref_twitter.xml で定義されている, 自動保存される値のキー
    public static final String TWEET_TOBATSU = "tweet_tobatsu";
    public static final String TWEET_ALL_CHAR = "tweet_tobatsu_all_characters";

    private static final long DEF_LONG = -1L;
    private static final String DEF_STRING = "";

    private final Context context;

    public TwitterPrefUtils(Context context) {
        this.context = context;
    }

    public void storeAuthInfo(long userId, String screenName, String token, String tokenSecret) {
        SharedPreferences sp = getPrefs();
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(USER_ID, userId);
        edit.putString(SCREEN_NAME, screenName);
        edit.putString(TOKEN, token);
        edit.putString(TOKEN_SECRET, tokenSecret);
        edit.commit();
    }

    public long getUserId() {
        return getPrefs().getLong(USER_ID, DEF_LONG);
    }

    public String getScreenName() {
        return getPrefs().getString(SCREEN_NAME, DEF_STRING);
    }

    public String getToken() {
        return getPrefs().getString(TOKEN, DEF_STRING);
    }

    public String getTokenSecret() {
        return getPrefs().getString(TOKEN_SECRET, DEF_STRING);
    }

    public Collection<Long> getTobatsuTweetCharacters() {
        String str = getPrefs().getString(TOBATSU_TWEET_CHARACTERS, "");
        String[] strIds = str.split(",");
        Set<Long> res = new HashSet<>(strIds.length);
        for (String strId : strIds) {
            if (strId.isEmpty()) {
                // 未設定の場合はから文字列が来るのでスキップ
                // (breakしてもいいが)
                continue;
            }
            res.add(Long.parseLong(strId));
        }
        return res;
    }

    public void setTobatsuTweetCharacters(Collection<Long> webPcNos) {
        List<Long> list = new ArrayList<>(webPcNos);
        String str = Utils.join(",", list);
        getPrefs().edit().putString(TOBATSU_TWEET_CHARACTERS, str).commit();
    }

    public boolean getTweetTobatsu() {
        return getPrefs().getBoolean(TWEET_TOBATSU, true);
    }

    public boolean isTweetTobatsuAllChara() {
        return getPrefs().getBoolean(TWEET_ALL_CHAR, true);
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
