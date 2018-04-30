package com.shss.restaurantwaiter.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.shss.restaurantwaiter.config.DatabaseConfig;
import com.shss.restaurantwaiter.utility.SmartLog;

/**
 * @Project : Fruity Template Resraurant
 * @File : OpenDBHelper.java
 * @Author : Mr.Lemon
 * @Created : 22-02-2013 10:44:13
 * @Description : Helper class supports initial database or open database
 *              connection
 * @Copyright (C) Fruity Solutions Ltd 2013 . All rights reserved.
 */
public class OpenDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "OpenDBHelper";
	private Context context = null;

	private DatabaseConfig dbConfig = null;

	/**

	 */
	public OpenDBHelper(Context context, DatabaseConfig dbConfig) {
		super(context, dbConfig.getDatabaseName(), null, dbConfig
				.getDatabaseVersion());
		this.context = context;
		this.dbConfig = dbConfig;

		// Copy database to application data directory if not exists
		if (!isDatabaseExist()) {
			// Create blank file
			getReadableDatabase();
			close();
			try {
				copyDatabase();
				// assembleDataBase(context, dbConfig.getDatabaseFullPath());
			} catch (IOException e) {
				SmartLog.log(TAG, "Error to init database");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {

		/*
		 * //For inside database try { SmartLog.log(TAG, "Create new database");
		 * sqlitedatabase.execSQL(SQL_CREAT_TABLE_PRODUCT);
		 * sqlitedatabase.execSQL(SQL_CREAT_TABLE_PRODUCT_OPTION);
		 * sqlitedatabase.execSQL(SQL_CREAT_TABLE_CATEGORY);
		 * sqlitedatabase.execSQL(SQL_CREAT_TABLE_ETICKET);
		 * sqlitedatabase.execSQL(SQL_CREAT_TABLE_GIFT_CARD); } catch
		 * (SQLException ex) { ex.printStackTrace(); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// for inside database
		/*
		 * SmartLog.log(TAG, "Updating database...");
		 * db.execSQL("DROP TABLE IF EXISTS Product");
		 * db.execSQL("DROP TABLE IF EXISTS ProductOption");
		 * db.execSQL("DROP TABLE IF EXISTS Category");
		 * db.execSQL("DROP TABLE IF EXISTS ETicket");
		 * db.execSQL("DROP TABLE IF EXISTS GiftCard");
		 */
		onCreate(db);
	}

	/**
	 * Copy database to application directory on SD card
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */
	private void copyDatabase() throws IOException {
		SmartLog.log(TAG, "Copy database into application directory");
		InputStream is = context.getAssets().open(dbConfig.getDatabaseName());
		OutputStream os = new FileOutputStream(dbConfig.getDatabaseFullPath(context));
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) >0) {
			os.write(buffer, 0, length);
		}
		os.flush();
		os.close();
		is.close();
	}

	// private static void assembleDataBase(Context context, String fullPathDb)
	// throws IOException {
	// String[] dbFiles = context.getAssets().list(ASSETS_DB_FOLDER);
	// String outFileName = DB_FULL_PATH;
	// OutputStream myOutput = new FileOutputStream(outFileName);
	// for (int i = 0; i < dbFiles.length; i++) {
	// InputStream myInput = context.getAssets().open(
	// ASSETS_DB_FOLDER + "/" + dbFiles[i]);
	// byte[] buffer = new byte[1024];
	// int length;
	// while ((length = myInput.read(buffer)) > 0) {
	// myOutput.write(buffer, 0, length);
	// }
	// myInput.close();
	// }
	// myOutput.flush();
	// myOutput.close();
	// }

	/**
	 * Check database is exist
	 * 
	 * @return
	 */
	private boolean isDatabaseExist() {
		SmartLog.log(TAG,
				"Database is not exist! " + dbConfig.getDatabaseFullPath(context)
						+ " ======================");
		SQLiteDatabase checkDB = null;
		try {

			checkDB = SQLiteDatabase.openDatabase(
					dbConfig.getDatabaseFullPath(context), null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS
							| SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			SmartLog.log(TAG,
					"Database is not exist! " + dbConfig.getDatabaseFullPath(context)
							+ " ======================");
			// e.printStackTrace();
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return (checkDB != null) ? true : false;
	}
}
