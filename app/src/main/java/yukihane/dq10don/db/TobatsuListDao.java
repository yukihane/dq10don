package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;

/**
 * 討伐リストDao.
 */
public class TobatsuListDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuListDao.class);

    private final Dao<TobatsuList, Long> tobatsuListDao;
    private final Dao<TobatsuItem, Long> tobatsuItemDao;

    private TobatsuListDao(DbHelper helper) throws SQLException {
        this.tobatsuListDao = helper.getDao(TobatsuList.class);
        this.tobatsuItemDao = helper.getDao(TobatsuItem.class);
    }

    public static TobatsuListDao create(DbHelper helper) throws SQLException {
        return new TobatsuListDao(helper);
    }

    /**
     * 指定したキャラクターの最新討伐リストを取得します.
     */
    public List<TobatsuList> queryLatest(Character character) throws SQLException {
        String queryStr = getExtractQuery().append("and B.character_id = ?;").toString();
        List<TobatsuList> lists = extract(queryStr, new String[]{Long.toString(character.getWebPcNo())});
        for (TobatsuList tl : lists) {
            queryAndSetItems(tl);
            tl.setCharacter(character);
        }
        return lists;
    }

    private void queryAndSetItems(TobatsuList tl) throws SQLException {
        List<TobatsuItem> items = tobatsuItemDao.queryForEq("list", tl.getId());
        for (TobatsuItem i : items) {
            tl.addListItem(i);
        }
    }


    private StringBuilder getExtractQuery() {
        return new StringBuilder()
                .append("select id from TobatsuList A ")
                .append("inner join (")
                .append("  select character_id, countySize, max(issuedDate) as maxDate ")
                .append("  from TobatsuList ")
                .append("  group by character_id, countySize ")
                .append(") B ")
                .append("on A.character_id = B.character_id ")
                .append("and A.countySize = B.countySize ")
                .append("and A.issuedDate = B.maxDate ");
    }

    /**
     * {@link #getExtractQuery()} に準じたクエリでTobatsuListを取得します.
     *
     * @param queryStr {@link #getExtractQuery()} を基本として構築したクエリ.
     */
    private List<TobatsuList> extract(String queryStr, String... arguments) throws SQLException {

        DataType[] dataType = new DataType[]{DataType.LONG};
        GenericRawResults<Object[]> rawResults = tobatsuListDao.queryRaw(queryStr, dataType, arguments);
        List<Object[]> results = rawResults.getResults();

        Object[] ids = results.toArray();
        LOGGER.debug("selected IDs: {}", ids);

        QueryBuilder<TobatsuList, Long> qb = tobatsuListDao.queryBuilder();
        qb.where().in("id", ids);
        return tobatsuListDao.query(qb.prepare());

    }

    public void persist(TobatsuList obj) throws SQLException {

        if (obj.getId() != null) {
            tobatsuItemDao.delete(obj.getListItems());
        }
        tobatsuListDao.createOrUpdate(obj);
        for (TobatsuItem ti : obj.getListItems()) {
            tobatsuItemDao.create(ti);
        }
    }
}
