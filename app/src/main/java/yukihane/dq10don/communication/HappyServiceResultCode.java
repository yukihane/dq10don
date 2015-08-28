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
     * [finish]現在のプレイ環境では　この機能は\n使用制限されています。\n正式なプレイ環境となれば\nこの機能も　お使いいただけます。
     */
    public static final int TRIAL_RESTRICTED = 113;

    /**
     * 引っ越ししたキャラクターに対して発生したので, おそらく該当キャラが存在しない場合のエラーコード.
     * 本アプリとしては再ログイン処理を行ってキャラ一覧を更新する必要がある.
     */
    public static final int NO_SUCH_CHARACTER = 1005;
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

    /**
     * 現在　メンテナンスをおこなっております。\n\n2015年8月24日(月) 2:00より10:00頃まで\n\n申し訳ありませんが　しばらくお待ちください。\n
     */
    public static final int OUT_OF_SERVICE = 99999;

    private HappyServiceResultCode() {
    }
}
