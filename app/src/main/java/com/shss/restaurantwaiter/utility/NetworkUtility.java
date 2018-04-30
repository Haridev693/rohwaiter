/*
 * Name: $RCSfile: NetworkUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 3:57:18 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkUtility checks available network
 * 
 * @author Lemon
 */
public final class NetworkUtility {
	private Context context = null;

	private static NetworkUtility instance = null;

	private static String TAG = "NetworkUtility";

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	private NetworkUtility(Context context) {
		this.context = context;
	}

	/**
	 * Get class instance
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkUtility getInstance(Context context) {
		if (instance == null) {
			instance = new NetworkUtility(context);
		}
		return instance;
	}

	/**
	 * Check network connection
	 * 
	 * @return
	 */
	public boolean isNetworkAvailable() {
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) {
			return false;
		}
		if (!i.isConnected()) {
			return false;
		}
		if (!i.isAvailable()) {
			return false;
		}
		return true;
	}

}
