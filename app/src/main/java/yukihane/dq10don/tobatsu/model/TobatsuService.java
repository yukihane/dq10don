package yukihane.dq10don.tobatsu.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.tobatsu.presenter.CharacterDto;

/**
 * Created by yuki on 15/07/18.
 */
public interface TobatsuService {
    Map<Character, TobatsuList> getTobatsuList() throws SQLException;

    TobatsuList getTobatsuList(long webPcNo) throws SQLException;

    /**
     * DB情報を見ずにサーバーから情報を取得します(強制更新).
     */
    TobatsuList getTobatsuListFromServer(long webPcNo) throws SQLException;
}
