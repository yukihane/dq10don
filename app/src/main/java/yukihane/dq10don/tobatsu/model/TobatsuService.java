package yukihane.dq10don.tobatsu.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/18.
 */
public interface TobatsuService {
    Map<Character, TobatsuList> getTobatsuListsFromServer() throws SQLException;

    /**
     * DBから情報を取得します.
     */
    TobatsuList getTobatsuListFromDB(long webPcNo) throws SQLException;

    /**
     * サーバーから情報を取得します.
     */
    TobatsuList getTobatsuListFromServer(long webPcNo) throws HappyServiceException, SQLException;
}
