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

    TobatsuList getTobatsuList(long webPcNo) throws HappyServiceException, SQLException;

    /**
     * DB情報を見ずにサーバーから情報を取得します(強制更新).
     */
    TobatsuList getTobatsuListFromServer(long webPcNo) throws HappyServiceException, SQLException;
}
