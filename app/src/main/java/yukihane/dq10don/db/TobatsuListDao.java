package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

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

    private TobatsuListDao(DbHelper helper) throws SQLException {
        this.tobatsuListDao = helper.getDao(TobatsuList.class);
    }

    public static TobatsuListDao create(DbHelper helper) throws SQLException {
        return new TobatsuListDao(helper);
    }

    private StringBuilder getExtractQuery() {
        return new StringBuilder()
                .append("select id from TobatsuList A ")
                .append("inner join (")
                .append("  select character, countrySize, max(issuedDate) as maxDate ")
                .append("  from TobatsuList ")
                .append("  group by character, countrySize ")
                .append(") B ")
                .append("on A.character = B.character ")
                .append("and A.countrySize = B.countrySize ")
                .append("and A.issuedDate = B.maxDate ");
    }

    /**
     * 全キャラクター分の最新討伐リストを取得します.
     */
    public List<TobatsuList> queryALlLatest() throws SQLException {
        String queryStr = getExtractQuery().append(";").toString();

        return extract(queryStr);
    }

    /**
     * 指定したキャラクターの最新討伐リストを取得します.
     */
    public List<TobatsuList> queryLatest(Character character) throws SQLException {
        String queryStr = getExtractQuery().append("and B.character = ?;").toString();
        return extract(queryStr, new String[]{Long.toString(character.getWebPcNo())});
    }

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
}
