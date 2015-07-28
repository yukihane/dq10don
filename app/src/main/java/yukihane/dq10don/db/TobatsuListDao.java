package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import yukihane.dq10don.Utils;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.account.TobatsuList;

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
        List<TobatsuItem> items = tobatsuItemDao.queryForEq("list_id", tl.getId());
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

        List<Object> ids = new ArrayList<>();
        for (Object[] o : results) {
            ids.add(o[0]);
        }

        LOGGER.debug("selected IDs: {} by query:{}, dataType:{}, arguments:{}",
                ids, queryStr, dataType, arguments);

        QueryBuilder<TobatsuList, Long> qb = tobatsuListDao.queryBuilder();
        qb.where().in("id", ids);
        return tobatsuListDao.query(qb.prepare());

    }

    public void persist(TobatsuList obj) throws SQLException {

        LOGGER.debug("persist TobatsuList: {}", obj);

        TobatsuList savedList = deleteItems(obj);
        if (savedList != null) {
            obj.setId(savedList.getId());
        }

        tobatsuListDao.createOrUpdate(obj);
        for (TobatsuItem ti : obj.getListItems()) {
            tobatsuItemDao.create(ti);
        }
    }

    /**
     * TobatsuList オブジェクトに紐づくTobatsuItemをDBから削除します.
     * TobatsuList自体は削除しません.
     *
     * @param obj 処理対象とする TobatsuListオブジェクト.
     * @return 指定されたオブジェクトの, DB永続化された情報.
     * @throws SQLException
     */
    private TobatsuList deleteItems(TobatsuList obj) throws SQLException {
        PreparedQuery<TobatsuList> query = tobatsuListDao.queryBuilder().where()
                .eq("character_id", obj.getCharacter())
                .and().eq("countySize", obj.getCountySize())
                .and().eq("issuedDate", obj.getIssuedDate())
                .prepare();
        List<TobatsuList> saved = tobatsuListDao.query(query);

        if (!saved.isEmpty()) {
            // ユニークキーを指定しているので存在する場合は必ず1
            assert saved.size() == 1;
            TobatsuList res = saved.get(0);

            DeleteBuilder<TobatsuItem, Long> builder = tobatsuItemDao.deleteBuilder();
            builder.where().eq("list_id", res);
            PreparedDelete<TobatsuItem> itemQuery = builder.prepare();
            int delnum = tobatsuItemDao.delete(itemQuery);
            LOGGER.debug("deleted item(s): {}", delnum);

//            int dellistnum = tobatsuListDao.delete(res);
//            LOGGER.debug("deleted list: {}", dellistnum);

            return res;
        } else {
            return null;
        }
    }

    public TobatsuItem max(String issuedDate, Collection<Long> webPcNos) throws SQLException {
        if (webPcNos != null && webPcNos.isEmpty()) {
            return null;
        }

        StringBuilder listQBuilder = new StringBuilder()
                .append("select id ")
                .append("from tobatsuList ")
                .append("where issuedDate = ? ");
        if (webPcNos != null) {
            listQBuilder.append("and character_id in (");
            listQBuilder.append(Utils.join(", ", webPcNos));
            listQBuilder.append(")");
        }
        listQBuilder.append(";");
        String listQueryStr = listQBuilder.toString();

        DataType[] dataType = new DataType[]{DataType.LONG};
        GenericRawResults<Object[]> listQueryResults = tobatsuListDao.queryRaw(listQueryStr, dataType, new String[]{issuedDate});
        List<Object[]> results = listQueryResults.getResults();
        List<Long> listIds = new ArrayList<>();
        for (Object[] o : results) {
            listIds.add((Long) o[0]);
        }
        if (listIds.isEmpty()) {
            return null;
        }

        StringBuilder itemQBuilder = new StringBuilder()
                .append("select id ")
                .append("from tobatsuItem A ")
                .append("inner join( ")
                .append("  select max(point) maxPoint ")
                .append("  from tobatsuItem ")
                .append("  where list_id in (").append(Utils.join(", ", listIds)).append(") ")
                .append(") B ")
                .append("on A.point = B.maxPoint ")
                .append("where list_id in (").append(Utils.join(", ", listIds)).append(") ");
        String itemQueryStr = itemQBuilder.toString();
        DataType[] itemIdType = new DataType[]{DataType.LONG};
        GenericRawResults<Object[]> itemQueryResults = tobatsuItemDao.queryRaw(itemQueryStr, itemIdType, new String[0]);
        List<Object[]> itemResults = itemQueryResults.getResults();
        List<Long> itemIds = new ArrayList<>();
        for (Object[] o : itemResults) {
            itemIds.add((Long) o[0]);
        }
        if (itemIds.isEmpty()) {
            return null;
        }
        return tobatsuItemDao.queryForId(itemIds.get(0));
    }
}
