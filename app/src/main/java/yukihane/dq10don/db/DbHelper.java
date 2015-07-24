package yukihane.dq10don.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.BgService;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.account.TobatsuList;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    private static final String DATABASE_NAME = "dq10don.db";
    private static final int DATABASE_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        LOGGER.info("MyDatabaseHelper.onCreate()");
        try {
            TableUtils.createTable(connectionSource, BgService.class);
            TableUtils.createTable(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, Character.class);
            TableUtils.createTable(connectionSource, TobatsuList.class);
            TableUtils.createTable(connectionSource, TobatsuItem.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        LOGGER.info("BEGIN migration oldVersion: {}, newVersion: {}", oldVersion, newVersion);

        /*
         * 参考: http://www.sqlite.org/lang_altertable.html
         */

        db.beginTransaction();
        try {
            Dao<Account, String> accountDao = getDao(Account.class);
            Dao<Character, Long> characterDao = getDao(Character.class);

            if (oldVersion < 2) {
                db.execSQL("PRAGMA foreign_keys=OFF");

                String newAccount = "CREATE TABLE `new_account`"
                        + " (`sessionId` VARCHAR , `sqexid` VARCHAR NOT NULL,"
                        + " `invalid` SMALLINT NOT NULL , PRIMARY KEY (`sqexid`) );";
                db.execSQL(newAccount);

                String newAccountInsert = "INSERT INTO new_account (sessionId, sqexid, invalid)"
                        + " SELECT sessionId, sqexid, 0 FROM account;";
                db.execSQL(newAccountInsert);

                db.execSQL("DROP TABLE account;");
                db.execSQL("ALTER TABLE new_account RENAME TO account;");

                String newCharacter = "CREATE TABLE `new_character`"
                        + " (`account_id` VARCHAR NOT NULL , `characterName` VARCHAR NOT NULL ,"
                        + " `smileUniqNo` VARCHAR NOT NULL , `lastTobatsuResultCode` INTEGER NOT NULL ,"
                        + " `webPcNo` BIGINT NOT NULL ," +
                        " PRIMARY KEY (`webPcNo`), FOREIGN KEY (account_id) REFERENCES account(sqexid));";
                db.execSQL(newCharacter);

                String newCharacterInsert = "INSERT INTO new_character "
                        + " (`account_id`, `characterName`, `smileUniqNo`, `lastTobatsuResultCode`, `webPcNo`)"
                        + " SELECT `account_id`, `characterName`, `smileUniqNo`, 0, `webPcNo`"
                        + " FROM character;";
                db.execSQL(newCharacterInsert);

                db.execSQL("DROP TABLE character;");
                db.execSQL("ALTER TABLE new_character RENAME TO character;");

                db.rawQuery("PRAGMA foreign_key_check;", new String[]{});
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            LOGGER.error("ERROR migration", e);
            throw new RuntimeException(e);
        } finally {
            db.endTransaction();
            db.execSQL("PRAGMA foreign_keys=ON;");
            LOGGER.info("END migration");
        }
    }
}
