package yukihane.dq10don.tobatsu.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.model.BaseService;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * 討伐リスト情報を取得するためのサービス.
 */
public interface TobatsuService extends BaseService<TobatsuList> {
}
