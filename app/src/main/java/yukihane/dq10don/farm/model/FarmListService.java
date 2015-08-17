package yukihane.dq10don.farm.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.base.model.BaseService;
import yukihane.dq10don.exception.AppException;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListService implements BaseService<Farm> {

    @Override
    public Map<Character, Farm> getContentsFromServer() throws SQLException {
        return null;
    }

    @Override
    public Farm getContentFromDB(long webPcNo) throws SQLException {
        return null;
    }

    @Override
    public Farm getContentFromServer(long webPcNo) throws AppException, SQLException {
        return null;
    }
}
