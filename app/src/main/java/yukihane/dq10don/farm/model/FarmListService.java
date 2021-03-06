package yukihane.dq10don.farm.model;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Farm;
import yukihane.dq10don.base.model.BaseService;
import yukihane.dq10don.exception.AppException;

/**
 * モンスター牧場情報を取得するサービス
 */
public interface FarmListService extends BaseService<Farm> {
    MowResult mowAllGrasses(long webPcNo) throws SQLException, AppException;

    OpenBoxResult openAllTreasureBox(long webPcNo) throws SQLException, AppException;
}
