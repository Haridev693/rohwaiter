package com.shss.restaurantwaiter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;

public class MyApplication extends Activity {
	private static final String TAG = "MeyClubApplication";

	public NotificationManager notificationManager;

	private Context appContex;

	public static final boolean PROD_MODE = false;

	public static final boolean DEBUG_MODE = true;
	public static final boolean DEBUG_WS = true;
	public static final boolean DEBUG_DB = true;

	public void onCreate() {
		appContex = getApplicationContext();

	}

}
