package com.example.test.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.test.R;
import com.example.test.model.Account;
import com.example.test.model.Order;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "account.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 3;

	// the DAO object we use to access the SimpleData table
	private Dao<Account, String> accountDao = null;
  private Dao<Order, Integer> orderDao = null;
//	private RuntimeExceptionDao<Account, String> accountRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Account.class);
			
			TableUtils.createTable(connectionSource, Order.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
//		RuntimeExceptionDao<SimpleData, Integer> dao = getSimpleDataDao();
		Dao<Account, String> dao;
		Dao<Order, Integer> orderDao;
    try {
      dao = getAccountDao();
      Account simple = new Account("Kevin", "test");
      // create some entries in the onCreate
      dao.create(simple);
      simple = new Account("Hitomi", "test2");
      dao.create(simple);
      
      orderDao = getOrderDao();
      Order order = new Order(simple, new BigDecimal("12.50"), "ABCDEF");
      orderDao.create(order);
      
      Log.i(DatabaseHelper.class.getName(), "created new entries");
    } catch (SQLException e) {
      e.printStackTrace();
    }
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Account.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<Account, String> getAccountDao() throws SQLException {
		if (accountDao == null) {
			accountDao = getDao(Account.class);
		}
		return accountDao;
	}
	
	//TODO: try foreign collection
	// http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Foreign-Collection
	//TODO: try many to many:
	// https://github.com/j256/ormlite-jdbc/blob/master/src/test/java/com/j256/ormlite/examples/manytomany/ManyToManyMain.java
	
	public Dao<Order, Integer> getOrderDao() throws SQLException {
	  if (orderDao == null) {
	    orderDao = getDao(Order.class);
	  }
	  return orderDao;
	}
	
	//This should be extracted to a separate class
	public List<Order> getOrdersByAccount(Account account) throws SQLException {
	  return getOrderDao().queryBuilder().where().eq("account_id", account).query();
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		accountDao = null;
	}
}
