package yukihane.dq10don.tobatsu.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;

/**
 * Created by yuki on 15/07/18.
 */
public interface TobatsuService {
    Map<Character, TobatsuList> getTobatsuList() throws SQLException;
}
