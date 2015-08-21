package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.Farm;
import yukihane.dq10don.account.FarmBox;
import yukihane.dq10don.account.FarmGrass;
import yukihane.dq10don.account.Storage;
import yukihane.dq10don.account.StoredItem;

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
}
