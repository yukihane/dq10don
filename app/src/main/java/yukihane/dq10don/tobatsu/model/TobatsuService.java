package yukihane.dq10don.tobatsu.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/18.
 */
public interface TobatsuService {

    /**
     * 全キャラクターの討伐情報をサーバから取得します.
     * ただしinvalidなキャラクターはサーバーにリクエストを行わず失敗したとみなし処理します.
     */
    Map<Character, TobatsuList> getTobatsuListsFromServer() throws SQLException;

    /**
     * DBから情報を取得します.
     */
    TobatsuList getTobatsuListFromDB(long webPcNo) throws SQLException;

    /**
     * サーバーから情報を取得します.
     * このメソッドの内部ではキャラクター/アカウントがinvalid可動化は判別しないので,
     * 必要があれば呼び出す前に判定を行ってください.
     */
    TobatsuList getTobatsuListFromServer(long webPcNo) throws AppException, SQLException;
}
