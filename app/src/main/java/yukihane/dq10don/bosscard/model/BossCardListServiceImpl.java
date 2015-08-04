package yukihane.dq10don.bosscard.model;

import com.j256.ormlite.misc.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.account.StoredItem;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.profile.ItemBasicListValueList;
import yukihane.dq10don.communication.dto.profile.StorageDto;
import yukihane.dq10don.communication.dto.profile.StorageListValueList;
import yukihane.dq10don.communication.dto.profile.StoredItemDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.StorageDao;
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
            if (account.isInvalid()) {
                continue;
            }

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
        return StorageDao.create(dbHelper).query(webPcNo);
    }

    /**
     * サーバーから情報を取得します.
     *
     * @param webPcNo
     */
    @Override
    public List<Storage> getTobatsuListFromServer(long webPcNo) throws AppException, SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);
        return getTobatsuListFromServer(character);
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
        StorageDao storageDao = StorageDao.create(dbHelper);

        List<Storage> res
                = TransactionManager.callInTransaction(dbHelper.getConnectionSource(), () -> {

            storageDao.delete(character);

            List<Storage> storages = new ArrayList<>();
            for (StorageListValueList slvl : dto.getStorageListValueList()) {
                Storage s = Storage.from(slvl);
                s.setCharacter(character);

                if (s.getStorageId() < Storage.STORAGE_ID_DOLL) {
                    // storageId が 100以上のもの(おそらく)はまたAPIが違うので現状はスキップ
                    StoredItemDto sidto = service.getStoredItemList(s.getStorageId(), s.getStorageIndex());
                    for (ItemBasicListValueList iblvl : sidto.getItemBasicListValueList()) {
                        StoredItem si = StoredItem.from(iblvl);
                        s.addStoredItem(si);
                    }
                }

                storages.add(s);
                storageDao.persist(s);
            }
            return storages;
        });

        return res;
    }
}
