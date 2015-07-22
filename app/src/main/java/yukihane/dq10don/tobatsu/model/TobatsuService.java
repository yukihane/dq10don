package yukihane.dq10don.tobatsu.model;

import java.util.Map;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.exception.AppException;

/**
 * Created by yuki on 15/07/18.
 */
public interface TobatsuService {
    Map<Character, TobatsuList> getTobatsuListsFromServer() throws AppException;

    TobatsuList getTobatsuList(long webPcNo) throws AppException;

    /**
     * DB情報を見ずにサーバーから情報を取得します(強制更新).
     */
    TobatsuList getTobatsuListFromServer(long webPcNo) throws AppException;
}
