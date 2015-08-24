package yukihane.dq10don.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    private static final String DATABASE_NAME = "dq10don.db";
    private static final int DATABASE_VERSION = 4;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static void createBgService(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `bgservice` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `seed` BIGINT );");
    }

    private static void createAccount(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `account` (`sessionId` VARCHAR , `sqexid` VARCHAR NOT NULL , `invalid` SMALLINT NOT NULL , PRIMARY KEY (`sqexid`) );");
    }

    private static void createCharacter(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `character` (`account_id` VARCHAR NOT NULL , `characterName` VARCHAR NOT NULL , `smileUniqNo` VARCHAR NOT NULL , `lastTobatsuResultCode` INTEGER NOT NULL , `webPcNo` BIGINT NOT NULL , PRIMARY KEY (`webPcNo`), FOREIGN KEY (`account_id`) REFERENCES `account`(`sqexid`));");
    }

    private static void createTobatsuList(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `tobatsulist` (`character_id` BIGINT , `id` INTEGER PRIMARY KEY AUTOINCREMENT , `issuedDate` VARCHAR , `countySize` INTEGER , UNIQUE (`character_id`,`issuedDate`,`countySize`) );");
    }

    private static void createTobatsuItem(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `tobatsuitem` (`area` VARCHAR , `id` INTEGER PRIMARY KEY AUTOINCREMENT , `list_id` BIGINT , `monsterName` VARCHAR , `count` INTEGER , `point` INTEGER, FOREIGN KEY(`list_id`) REFERENCES `tobatsulist`(`id`) );");
    }

    private static void createStorage(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `storage` (`character_id` BIGINT NOT NULL , `id` INTEGER PRIMARY KEY AUTOINCREMENT , `lastUpdateDate` VARCHAR , `storageName` VARCHAR , `storageId` INTEGER NOT NULL , `storageIndex` INTEGER NOT NULL, UNIQUE (`character_id`,`storageId`,`storageIndex`) );");
    }

    private static void createStoredItem(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `storeditem` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `itemName` VARCHAR NOT NULL , `itemUniqueNo` VARCHAR NOT NULL , `storage_id` BIGINT , `variousStr` VARCHAR , `webItemId` VARCHAR , UNIQUE (`storage_id`, `itemUniqueNo`), FOREIGN KEY(`storage_id`) REFERENCES `storage`(`id`));");
    }

    private static void createFarmGrass(SQLiteDatabase db) {
        String stmt = "CREATE TABLE `farmgrass` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `farm_id` BIGINT NOT NULL , `itemId` VARCHAR NOT NULL , `count` INTEGER NOT NULL , `grassTicket` BIGINT NOT NULL , FOREIGN KEY(`farm_id`) REFERENCES `farm`(`id`) );";
        db.execSQL(stmt);
    }

    private static void createFarmBox(SQLiteDatabase db) {
        String stmt = "CREATE TABLE `farmbox` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `expiredDt` VARCHAR NOT NULL , `farm_id` BIGINT NOT NULL , `type` VARCHAR NOT NULL , `ticketNo` BIGINT NOT NULL , FOREIGN KEY(`farm_id`) REFERENCES `farm`(`id`) );";
        db.execSQL(stmt);
    }

    private static void createFarm(SQLiteDatabase db) {
        String stmt = "CREATE TABLE `farm` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `agencyStatus` VARCHAR , `ungetCount` INTEGER NOT NULL , `character_id` BIGINT NOT NULL , `lastUpdateDate` VARCHAR NOT NULL , `nearLimitDt` VARCHAR , `nextSailoutDt` VARCHAR , `isFriendBlueBox` SMALLINT NOT NULL , `moreRebirthTreasureBox` SMALLINT NOT NULL , UNIQUE(`character_id`) );";
        db.execSQL(stmt);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        LOGGER.info("MyDatabaseHelper.onCreate()");

        db.beginTransaction();
        try {
            createBgService(db);
            createAccount(db);
            createCharacter(db);
            createTobatsuList(db);
            createTobatsuItem(db);

            // v3 & v4
            createStorage(db);
            createStoredItem(db);

            // v5
            createFarmGrass(db);
            createFarmBox(db);
            createFarm(db);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
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
                // ver1はリリースしていないので実質ここは通らない

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

            if (oldVersion < 3) {
                db.execSQL("PRAGMA foreign_keys=OFF");

                // ver2の頃に作ったテーブルは外部制約がはられていないのでここで追加する
                // character
                String newCharacter = "CREATE TABLE `new_character` (`account_id` VARCHAR NOT NULL , `characterName` VARCHAR NOT NULL , `smileUniqNo` VARCHAR NOT NULL , `lastTobatsuResultCode` INTEGER NOT NULL , `webPcNo` BIGINT NOT NULL , PRIMARY KEY (`webPcNo`), FOREIGN KEY (`account_id`) REFERENCES `account`(`sqexid`));";
                db.execSQL(newCharacter);

                String newCharacterInsert = "INSERT INTO new_character (`account_id`, `characterName`, `smileUniqNo`, `lastTobatsuResultCode`, `webPcNo`) SELECT `account_id`, `characterName`, `smileUniqNo`, `lastTobatsuResultCode`, `webPcNo` FROM character;";
                db.execSQL(newCharacterInsert);

                db.execSQL("DROP TABLE character;");
                db.execSQL("ALTER TABLE new_character RENAME TO character;");

                // tobatsuitem
                db.execSQL("CREATE TABLE `new_tobatsuitem` (`area` VARCHAR , `id` INTEGER PRIMARY KEY AUTOINCREMENT , `list_id` BIGINT , `monsterName` VARCHAR , `count` INTEGER , `point` INTEGER, FOREIGN KEY(`list_id`) REFERENCES `tobatsulist`(`id`) );");
                db.execSQL("INSERT INTO new_tobatsuitem(`area`, `id`, `list_id`, `monsterName`, `count`, `point`) SELECT `area`, `id`, `list_id`, `monsterName`, `count`, `point` FROM tobatsuitem;");
                db.execSQL("DROP TABLE tobatsuitem;");
                db.execSQL("ALTER TABLE new_tobatsuitem RENAME TO tobatsuitem;");


                // 今回追加したテーブル
                createStorage(db);
                createStoredItem(db);

                db.rawQuery("PRAGMA foreign_key_check;", new String[]{});
            }

            if (oldVersion == 3) {
                // ver3 の頃作成したstoreditem は PK が間違っているので修正
                db.execSQL("CREATE TABLE `new_storeditem` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `itemName` VARCHAR NOT NULL , `itemUniqueNo` VARCHAR NOT NULL , `storage_id` BIGINT , `variousStr` VARCHAR , `webItemId` VARCHAR , UNIQUE (`storage_id`, `itemUniqueNo`), FOREIGN KEY(`storage_id`) REFERENCES `storage`(`id`));");
                db.execSQL("INSERT INTO `new_storeditem` (`itemName`, `itemUniqueNo`, `storage_id`, `variousStr`, `webItemId`) "
                        + "SELECT `itemName`, `itemUniqueNo`, `storage_id`, `variousStr`, `webItemId` FROM  `storeditem`;");
                db.execSQL("DROP TABLE storeditem;");
                db.execSQL("ALTER TABLE new_storeditem RENAME TO storeditem;");
            }

            if(oldVersion < 5) {
                createFarmGrass(db);
                createFarmBox(db);
                createFarm(db);
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
