/*
 * Name: $RCSfile: StringUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 1:54:00 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.widget.EditText;

/**
 * StringUtility class
 * 
 * @author Lemon
 * 
 */
public final class StringUtility {
	/**
	 * Check Edit Text input string
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEmpty(EditText editText) {
		if (editText == null
				|| editText.getEditableText() == null
				|| editText.getEditableText().toString().trim()
						.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	/**
	 * Check input string
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEmpty(String editText) {
		if (editText == null || editText.trim().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public static String getSubString(String input, int maxLength) {
		String temp = input;
		if (input.length() < maxLength)
			return temp;
		else
			return input.substring(0, maxLength - 1) + "...";
	}

	/**
	 * Merge all elements of a string array into a string
	 * 
	 * @param strings
	 * @param separator
	 * @return
	 */
	public static String join(String[] strings, String separator) {
		StringBuffer sb = new StringBuffer();
		int max = strings.length;
		for (int i = 0; i < max; i++) {
			if (i != 0)
				sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	/**
	 * Convert current date time to string
	 * 
	 * @param updateTime
	 * @return
	 */
	public static String convertNowToFullDateString() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Calendar calendar =
		// Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// return dateformat.format(calendar.getTime());
		return dateformat.format(new Date());
	}

	public static String convertNowToDateString(String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		// dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Calendar calendar =
		// Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// return dateformat.format(calendar.getTime());
		return dateformat.format(new Date());
	}

	/**
	 * Initial sync date string
	 * 
	 * @return
	 */
	public static String initDateString() {
		return "1900-01-01 09:00:00";
	}

	/**
	 * Convert a string divided by ";" to multiple xmpp users
	 * 
	 * @param userString
	 * @return
	 */
	public static String[] convertStringToXmppUsers(String userString) {
		return userString.split(";");
	}

	/**
	 * get Unique Random String
	 * 
	 * @return
	 */

	public static String getUniqueRandomString() {

		// return String.valueOf(System.currentTimeMillis());
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}
	// public static String getUniqueRandomString() {
	//
	// return UUID.randomUUID().toString();
	//
	// }
}
