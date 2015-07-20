package yukihane.dq10don.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
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
    private static final int DATABASE_VERSION = 1;

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
        LOGGER.info("MyDatabaseHelper.onUpgrade()");
    }
}
