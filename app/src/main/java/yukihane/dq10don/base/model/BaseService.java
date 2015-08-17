package yukihane.dq10don.base.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.exception.AppException;

/**
 * サービスへ表示対象を要求し, キャラクターごとに結果をキャラクターごとに表示する機能のサービスのベース.
 *
 * @param <T> 表示対象とするデータの型.
 */
public interface BaseService<T> {

    /**
     * 全キャラクターの討伐情報をサーバから取得します.
     * ただしinvalidなキャラクターはサーバーにリクエストを行わず失敗したとみなし処理します.
     */
    Map<Character, T> getContentsFromServer() throws SQLException;

    /**
     * DBから情報を取得します.
     */
    T getContentFromDB(long webPcNo) throws SQLException;

    /**
     * サーバーから情報を取得します.
     * このメソッドの内部ではキャラクター/アカウントがinvalidかどうかは判別しないので,
     * 必要があれば呼び出す前に判定を行ってください.
     */
    T getContentFromServer(long webPcNo) throws AppException, SQLException;
}
