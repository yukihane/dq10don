package yukihane.dq10don.bosscard.model;

import java.sql.SQLException;
import java.util.Map;

import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.exception.AppException;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListServiceImpl implements BossCardListService {
    public BossCardListServiceImpl(DbHelper dbHelper) {

    }

    /**
     * 全キャラクターの討伐情報をサーバから取得します.
     * ただしinvalidなキャラクターはサーバーにリクエストを行わず失敗したとみなし処理します.
     */
    @Override
    public Map<Character, Storage> getTobatsuListsFromServer() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * DBから情報を取得します.
     *
     * @param webPcNo
     */
    @Override
    public Storage getTobatsuListFromDB(long webPcNo) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * サーバーから情報を取得します.
     * このメソッドの内部ではキャラクター/アカウントがinvalid可動化は判別しないので,
     * 必要があれば呼び出す前に判定を行ってください.
     *
     * @param webPcNo
     */
    @Override
    public Storage getTobatsuListFromServer(long webPcNo) throws AppException, SQLException {
        throw new UnsupportedOperationException();
    }
}
