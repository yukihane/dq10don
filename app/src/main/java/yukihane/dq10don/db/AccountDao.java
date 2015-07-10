package yukihane.dq10don.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import yukihane.dq10don.account.*;
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
        accountDao.createOrUpdate(account);
        DeleteBuilder<Character, Long> builder = characterDao.deleteBuilder();
        String stmt = builder.where().eq("account_id", account.getSqexid()).getStatement();
        LOGGER.info("delete stmt: {}", stmt);

        for (Iterator<Character> ite = account.getCharacters();  ite.hasNext();) {
            Character c = ite.next();
            characterDao.createOrUpdate(c);
        }
    }

    public List<Account> queryAll() throws SQLException {
        List<Account> accounts = accountDao.queryForAll();
        LOGGER.info("accounts: {}", accounts.size());
        for (Account a : accounts) {
            List<Character> c = characterDao.queryForEq("account_id", a.getSqexid());
            a.setCharacters(c);
            LOGGER.info("characters: {}", c.size());
        }
        return accounts;
    }
}
