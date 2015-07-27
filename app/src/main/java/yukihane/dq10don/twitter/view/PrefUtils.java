package yukihane.dq10don.twitter.view;

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
public final class PrefUtils {
    /**
     * Preferencesの名称
     */
    public static final String PREF_NAME = "twitter";


    public static final String USER_ID = "accessToken.userId";
    public static final String SCREEN_NAME = "accessToken.screenName";
    public static final String TOKEN = "accessToken.token";
    public static final String TOKEN_SECRET = "accessToken.tokenSecret";

    public static final String TOBATSU_TWEET_CHARACTERS = "tobatsu.characters";

    private static final long DEF_LONG = -1L;
    private static final String DEF_STRING = "";

    private final Context context;

    public PrefUtils(Context context) {
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

    public void setTobatsuTweetCharacters(Collection<Long> characters) {
        List<Long> list = new ArrayList<>(characters);
        String str = Utils.join(",", list);
        getPrefs().edit().putString(TOBATSU_TWEET_CHARACTERS, str).commit();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
