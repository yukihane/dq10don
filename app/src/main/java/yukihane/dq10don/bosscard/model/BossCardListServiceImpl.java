package yukihane.dq10don.bosscard.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.profile.ItemBasicListValueList;
import yukihane.dq10don.communication.dto.profile.StorageDto;
import yukihane.dq10don.communication.dto.profile.StorageListValueList;
import yukihane.dq10don.communication.dto.profile.StoredItemDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.StorageDao;
import yukihane.dq10don.db.TobatsuListDao;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/31.
 */
public class BossCardListServiceImpl implements BossCardListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BossCardListServiceImpl.class);

    private final DbHelper dbHelper;

    public BossCardListServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * 全キャラクターの討伐情報をサーバから取得します.
     * ただしinvalidなキャラクターはサーバーにリクエストを行わず失敗したとみなし処理します.
     */
    @Override
    public Map<Character, List<Storage>> getTobatsuListsFromServer() throws SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        List<Account> accounts = accountDao.queryAll();
        Map<Character, List<Storage>> result = new HashMap<>();
        for (Account account : accounts) {
            for (Character character : account.getCharacters()) {
                List<Storage> storages = new ArrayList<>();

                try {
                    List<Storage> res = getTobatsuListFromServer(character);
                    storages.addAll(res);
                } catch (Exception e) {
                    LOGGER.error("討伐リクエスト失敗" + character, e);
                }

                result.put(character, storages);
            }
        }
        return result;
    }

    /**
     * DBから情報を取得します.
     *
     * @param webPcNo
     */
    @Override
    public List<Storage> getTobatsuListFromDB(long webPcNo) throws SQLException {
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
    public List<Storage> getTobatsuListFromServer(long webPcNo) throws AppException, SQLException {
        throw new UnsupportedOperationException();
    }


    /**
     * DBを見ずに直接サーバーに情報をリクエストします.
     * 結果はDBに永続化します.
     */
    private List<Storage> getTobatsuListFromServer(Character character)
            throws HappyServiceException, SQLException {

        String sessionId = character.getAccount().getSessionId();
        LOGGER.info("update target character: {}", character);
        HappyService service = HappyServiceFactory.getService(sessionId);
        service.characterSelect(character.getWebPcNo());

        StorageDto dto = service.getStorageList2();
        List<Storage> res = new ArrayList<>();
        for (StorageListValueList slvl : dto.getStorageListValueList()) {
            Storage s = Storage.from(slvl);
            s.setCharacter(character);

            StoredItemDto sidto = service.getStoredItemList(s.getStorageId(), s.getStorageIndex());
            for (ItemBasicListValueList iblvl : sidto.getItemBasicListValueList()) {
                StoredItem si = StoredItem.from(iblvl);
                s.addItem(si);
            }

            res.add(s);
            StorageDao.create(dbHelper).persist(s);
        }

        return res;
    }
}
