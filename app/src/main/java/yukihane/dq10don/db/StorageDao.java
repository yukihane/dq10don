package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.account.StoredItem;
import yukihane.dq10don.bosscard.model.BossCard;

/**
 * Created by yuki on 15/07/31.
 */
public class StorageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageDao.class);

    private final Dao<Storage, Long> storageDao;
    private final Dao<StoredItem, String> storedItemDao;

    private StorageDao(DbHelper dbHelper) throws SQLException {
        storageDao = dbHelper.getDao(Storage.class);
        storedItemDao = dbHelper.getDao(StoredItem.class);
    }

    public static StorageDao create(DbHelper dbHelper) throws SQLException {
        return new StorageDao(dbHelper);
    }


    public void persist(final Storage obj) throws SQLException {

        LOGGER.debug("persist Storage: {}", obj);

        TransactionManager.callInTransaction(storageDao.getConnectionSource(), () -> {
            Storage savedList = deleteItems(obj);
            if (savedList != null) {
                obj.setId(savedList.getId());
            }

            storageDao.createOrUpdate(obj);
            for (StoredItem i : obj.getSotredItems()) {
                storedItemDao.create(i);
            }

            return null;
        });
    }

    /**
     * Storage オブジェクトに紐づくTobatsuItemをDBから削除します.
     * Storage自体は削除しません.
     *
     * @param obj 処理対象とする Storageオブジェクト.
     * @return 指定されたオブジェクトの, DB永続化された情報.
     * @throws SQLException
     */
    private Storage deleteItems(Storage obj) throws SQLException {
        PreparedQuery<Storage> query = storageDao.queryBuilder().where()
                .eq("character_id", obj.getCharacter())
                .and().eq("storageId", obj.getStorageId())
                .and().eq("storageIndex", obj.getStorageIndex())
                .prepare();
        List<Storage> saved = storageDao.query(query);

        if (!saved.isEmpty()) {
            // ユニークキーを指定しているので存在する場合は必ず1
            assert saved.size() == 1;
            Storage res = saved.get(0);

            DeleteBuilder<StoredItem, String> builder = storedItemDao.deleteBuilder();
            builder.where().eq("storage_id", res);
            PreparedDelete<StoredItem> itemQuery = builder.prepare();
            int delnum = storedItemDao.delete(itemQuery);
            LOGGER.debug("deleted item(s): {}", delnum);

            return res;
        } else {
            return null;
        }
    }

    /**
     * 指定したキャラクターのstorage及びそのstorageに属すstoreditemを全削除します.
     *
     * @param character 削除対象のstorageを所有しているcharacter
     */
    public void delete(Character character) throws SQLException {

        TransactionManager.callInTransaction(storageDao.getConnectionSource(), () -> {
            PreparedQuery<Storage> query = storageDao.queryBuilder()
                    .where()
                    .eq("character_id", character.getWebPcNo())
                    .prepare();

            List<Storage> owneds = storageDao.query(query);
            if (owneds.isEmpty()) {
                return null;
            }
            List<Long> ownedIds = new ArrayList<>(owneds.size());
            for (Storage s : owneds) {
                ownedIds.add(s.getId());
            }
            DeleteBuilder<StoredItem, String> builder = storedItemDao.deleteBuilder();
            builder.where().in("storage_id", ownedIds);
            PreparedDelete<StoredItem> delQuery = builder.prepare();

            storedItemDao.delete(delQuery);
            storageDao.delete(owneds);

            return null;
        });
    }

    /**
     * characterが持っているストレージとその中のアイテムを取得します.
     *
     * @param webPcNo キャラクターのID.
     */
    public List<Storage> query(long webPcNo) throws SQLException {
        PreparedQuery<Storage> storageQuery = storageDao.queryBuilder()
                .where()
                .eq("character_id", webPcNo)
                .prepare();
        List<Storage> storages = storageDao.query(storageQuery);

        for (Storage s : storages) {
            PreparedQuery<StoredItem> itemQuery = storedItemDao.queryBuilder()
                    .where()
                    .eq("storage_id", s.getId())
                    .prepare();
            List<StoredItem> items = storedItemDao.query(itemQuery);
            for (StoredItem i : items) {
                s.addStoredItem(i);
            }
        }


        return storages;
    }

    /**
     * 指定の時間より短い有効期限のアイテムを取得します.
     * 期限切れは対象外です.
     *
     * @param leftMinitesLimit 分単位で指定します.
     * @return 条件にマッチするアイテム
     */
    public List<BossCard> queryLimitLessThan(int leftMinitesLimit) throws SQLException {
        List<BossCard> res = new ArrayList<>();

        List<StoredItem> items = storedItemDao.queryForAll();
        for (StoredItem i : items) {
            Integer lt = i.getLeftTime();
            if (lt == null) {
                continue;
            }
            Storage storage = storageDao.queryForId(i.getStorage().getId());
            BossCard card = new BossCard(i.getItemName(), storage.getStorageName(),
                    storage.getLastUpdateDate(), i.getLeftTime());
            int leftMinites = card.getCalculatedLeftMinites();
            if (0 < leftMinites && leftMinites <= leftMinitesLimit) {
                res.add(card);
            }
        }

        Collections.sort(res);
        return res;
    }
}
