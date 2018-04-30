/*
 * Name: $RCSfile: SmartLog.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 1:58:08 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.utility;

import android.util.Log;

import com.shss.restaurantwaiter.MyApplication;

/**
 * SmartLog supports to show log on console base on application mode
 * 
 * @author Lemon
 */
public final class SmartLog {
	/**
	 * Call SmartLog.log
	 * 
	 * @param TAG
	 * @param msg
	 */
	public static void log(String TAG, String msg) {
		if (MyApplication.DEBUG_MODE) {
			Log.d(TAG, msg);
		}
	}

	public static void logWS(String TAG, String msg) {
		if (MyApplication.DEBUG_WS) {
			Log.w(TAG, msg);
		}
	}

	public static void logDB(String TAG, String msg) {
		if (MyApplication.DEBUG_DB) {
			Log.e(TAG, msg);
		}
	}

	/**
	 * Log sign in response information
	 * 
	 * @param TAG
	 * @param userInfo
	 */
//	public static void logSignInResponse(String TAG, UserInfo userInfo) {
//		if (userInfo != null) {
//			log(TAG, "SIGN IN RESPONSE: ============================");
//			log("SUCCESS", "" + userInfo.isSuccess());
//			log("USER ID", userInfo.getUserId());
//			log("USER NAME", userInfo.getUserName());
//		}
//	}

}
