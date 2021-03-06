package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

/**
 * Created by yuki on 15/07/10.
 */
public class AccountDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDao.class);

    private final Dao<Account, String> accountDao;
    private final Dao<Character, Long> characterDao;

    private AccountDao(DbHelper helper) throws SQLException {
        this.accountDao = helper.getDao(Account.class);
        this.characterDao = helper.getDao(Character.class);
    }

    public static AccountDao create(DbHelper helper) throws SQLException {
        return new AccountDao(helper);
    }

    public void persist(Account account) throws SQLException {

        TransactionManager.callInTransaction(accountDao.getConnectionSource(), () ->
        {
            accountDao.createOrUpdate(account);

            for (Character c : account.getCharacters()) {
                characterDao.createOrUpdate(c);
            }
            return null;
        });
    }

    /**
     * 配下のCharacterを気にせずにaccountのフィールドだけを更新する場合に使用します.
     *
     * @param account 更新対象. 配下のcharacterは更新されません.
     */
    public void updateAccountOnly(Account account) throws SQLException {
        accountDao.update(account);
    }

    public void persist(Character character) throws SQLException {
        characterDao.createOrUpdate(character);
    }

    public List<Account> queryAll() throws SQLException {
        List<Account> accounts = accountDao.queryForAll();
        LOGGER.debug("queryAll: account size: {}", accounts.size());
        for (Account a : accounts) {
            List<Character> c = characterDao.queryForEq("account_id", a.getSqexid());
            a.setCharacters(c);
            LOGGER.debug("queryAll: character size: {}", c.size());
        }
        return accounts;
    }

    /**
     * アカウントと紐づくキャラクターを削除します.
     *
     * @param sqexid アカウントのID
     * @throws SQLException
     */
    public void deleteById(String sqexid) throws SQLException {

        TransactionManager.callInTransaction(accountDao.getConnectionSource(), () -> {
            DeleteBuilder<Character, Long> builder = characterDao.deleteBuilder();
            builder.where().eq("account_id", sqexid);
            characterDao.delete(builder.prepare());

            accountDao.deleteById(sqexid);

            return null;
        });
    }

    /**
     * キャラクターとその親であるアカウントを返します.
     * {@link Account#getCharacters()}は、このキャラクターのみを返します.
     */
    public Character findCharacterByWebPcNo(long webPcNo) throws SQLException {
        List<Character> c = characterDao.queryForEq("webPcNo", webPcNo);
        if (c.isEmpty()) {
            return null;
        }
        assert c.size() == 1;
        Character character = c.get(0);

        String queryStr = ""
                + "select account_id from Character "
                + "where webPcNo = ?";
        String[] arguments = new String[]{Long.toString(webPcNo)};
        DataType[] dataType = new DataType[]{DataType.STRING};
        GenericRawResults<Object[]> rawResults = characterDao.queryRaw(queryStr, dataType, arguments);
        List<Object[]> results = rawResults.getResults();

        String sqexid = (String) results.get(0)[0];
        Account account = accountDao.queryForId(sqexid);

        List<Character> characters = new ArrayList<>(1);
        characters.add(character);
        account.setCharacters(characters);

        return character;
    }

    /**
     * 1レコードも存在しなければfalse.
     */
    public boolean exists() throws SQLException {
        return 0 < accountDao.countOf();
    }
}
