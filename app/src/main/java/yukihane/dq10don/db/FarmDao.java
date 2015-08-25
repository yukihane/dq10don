package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Farm;
import yukihane.dq10don.account.FarmBox;
import yukihane.dq10don.account.FarmGrass;

/**
 * Created by yuki on 15/08/21.
 */
public class FarmDao {

    private final Dao<Farm, Long> farmDao;
    private final Dao<FarmGrass, Long> farmGrassDao;
    private final Dao<FarmBox, Long> farmBoxDao;

    public FarmDao(DbHelper dbHelper) throws SQLException {
        farmDao = dbHelper.getDao(Farm.class);
        farmGrassDao = dbHelper.getDao(FarmGrass.class);
        farmBoxDao = dbHelper.getDao(FarmBox.class);
    }

    public static FarmDao create(DbHelper dbHelper) throws SQLException {
        return new FarmDao(dbHelper);
    }

    /**
     * 指定したキャラクターの牧場情報を取得します. 無ければnull.
     */
    public Farm query(long webPcNo) throws SQLException {
        PreparedQuery<Farm> qFarm = farmDao.queryBuilder()
                .where()
                .eq("character_id", webPcNo)
                .prepare();
        List<Farm> farms = farmDao.query(qFarm);

        if (farms.isEmpty()) {
            return null;
        }

        assert (farms.size() == 1);
        Farm farm = farms.get(0);

        PreparedQuery<FarmGrass> qGrass = farmGrassDao.queryBuilder()
                .where()
                .eq("farm_id", farm.getId())
                .prepare();
        List<FarmGrass> grasses = farmGrassDao.query(qGrass);
        for (FarmGrass g : grasses) {
            farm.addGrass(g);
        }

        PreparedQuery<FarmBox> qBox = farmBoxDao.queryBuilder()
                .where()
                .eq("farm_id", farm.getId())
                .prepare();
        List<FarmBox> boxes = farmBoxDao.query(qBox);
        for (FarmBox box : boxes) {
            farm.addBox(box);
        }

        return farm;
    }

    /**
     * Farmを保存します.
     */
    public void persist(Farm farm) throws SQLException {

        TransactionManager.callInTransaction(farmDao.getConnectionSource(), () -> {

            PreparedQuery<Farm> query = farmDao.queryBuilder()
                    .where()
                    .eq("character_id", farm.getCharacter())
                    .prepare();

            List<Farm> saveds = farmDao.query(query);
            if (!saveds.isEmpty()) {

                // ユニークキー指定なので存在するならば必ず1個
                assert (saveds.size() == 1);
                Farm saved = saveds.get(0);

                deleteGrasses(saved);
                deleteBoxes(saved);

                farm.setId(saved.getId());
            }

            farmDao.createOrUpdate(farm);
            for (FarmGrass g : farm.getFarmGrasses()) {
                farmGrassDao.create(g);
            }
            for (FarmBox b : farm.getFarmBoxes()) {
                farmBoxDao.create(b);
            }

            return null;
        });
    }

    /**
     * 牧場の雑草永続化データを削除します.
     */
    public void deleteGrasses(long webPcNo) throws SQLException {
        Farm farm = query(webPcNo);
        if (farm != null) {
            deleteGrasses(farm);
        }
    }

    private void deleteGrasses(Farm farm) throws SQLException {
        DeleteBuilder<FarmGrass, Long> builder = farmGrassDao.deleteBuilder();
        builder.where().eq("farm_id", farm);
        PreparedDelete<FarmGrass> query = builder.prepare();
        farmGrassDao.delete(query);
    }

    private void deleteBoxes(Farm farm) throws SQLException {
        DeleteBuilder<FarmBox, Long> builder = farmBoxDao.deleteBuilder();
        builder.where().eq("farm_id", farm);
        PreparedDelete<FarmBox> query = builder.prepare();
        farmBoxDao.delete(query);
    }
}
