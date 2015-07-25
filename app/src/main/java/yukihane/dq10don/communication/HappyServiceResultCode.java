package yukihane.dq10don.communication;

/**
 * Created by yuki on 15/07/24.
 */
public final class HappyServiceResultCode {

    public static final int NORMAL = 0;

    /**
     * ログイン中であり処理ができない.
     */
    public static final int INGAME = 106;
    /**
     * モーモンバザーを設置していない.
     */
    public static final int HOUSEBAZAAR_UNSET = 12009;
    /**
     * (推測)サーバー側の処理が終わっていない.
     */
    public static final int TOBATSU_SLOW_SERVICE = 22001;
    /**
     * 討伐が受注できるまで進めていない
     */
    public static final int TOBATSUQUEST_NEVER_ACCEPTED = 22002;

    private HappyServiceResultCode() {
    }
}
