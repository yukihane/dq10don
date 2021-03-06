package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import yukihane.dq10don.account.BgService;

/**
 * Created by yuki on 15/07/20.
 */
public class BgServiceDao {

    private final Dao<BgService, Long> bgServiceDao;

    private BgServiceDao(DbHelper dbHelper) throws SQLException {
        this.bgServiceDao = dbHelper.getDao(BgService.class);
    }

    public static BgServiceDao create(DbHelper dbHelper) throws SQLException {
        return new BgServiceDao(dbHelper);
    }

    private void persist(BgService bgService) throws SQLException {
        TransactionManager.callInTransaction(bgServiceDao.getConnectionSource(), () -> {
            // レコード数は常に1なので, 一旦全削除して今回分をinsertする
            TableUtils.clearTable(bgServiceDao.getConnectionSource(), BgService.class);
            bgServiceDao.create(bgService);

            return null;
        });
    }

    /**
     * レコードが存在するかどうかを返します(起動処理時, これは初回起動かどうかの判別のみに用います).
     *
     * @return レコードが存在していればtrue.
     * @throws SQLException
     */
    public boolean exists() throws SQLException {
        List<BgService> list = bgServiceDao.queryForAll();
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    public BgService get() throws SQLException {
        List<BgService> list = bgServiceDao.queryForAll();
        final BgService bg;
        if (list.isEmpty()) {
            bg = new BgService();
            bg.reset();
            persist(bg);
        } else {
            bg = list.get(0);
            if (bg.needsReset()) {
                bg.reset();
                persist(bg);
            }
        }
        return bg;
    }
}
