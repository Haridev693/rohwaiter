package com.shss.restaurantwaiter.config;

import android.content.Context;

import com.shss.restaurantwaiter.PacketUtility;

/**
 * Database informations
 * 
 * @author Lemon
 */
public final class DatabaseConfig {

	private static int DB_VERSION = 1;

	public static String DB_NAME = "Restaurants.sqlite";

	private static DatabaseConfig instance = null;

	/**
	 * Constructor
	 */
	public DatabaseConfig() {

	}

	/**
	 * Get class instance
	 * 
	 * @return
	 */
	public static DatabaseConfig getInstance() {
		if (instance == null) {
			instance = new DatabaseConfig();
		}
		return instance;
	}

	/**
	 * Get database version
	 * 
	 * @return
	 */
	public int getDatabaseVersion() {
		return DB_VERSION;
	}

	/**
	 * Get database name
	 * 
	 * @return
	 */
	public String getDatabaseName() {
		return DB_NAME;
	}

	/**
	 * Get database path
	 * 
	 * @return
	 */
	public String getDatabasePath() {
		PacketUtility packetUtility = new PacketUtility();
		return "/data/data/" + packetUtility.getPacketName() + "/databases/";
	}

	/**
	 * Get database path
	 * 
	 * @return
	 */
	public String getDatabaseFullPath() {
		return getDatabasePath() + DB_NAME;
	}
	public String getDatabaseFullPath(Context context) {
		return context.getFilesDir().getParentFile()+"/databases/"+DB_NAME;
	}
}
