/*
 * Name: $RCSfile: MeyClubSharedPreferences.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 2:07:45 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.shss.restaurantwaiter.object.UserInfo;
import com.shss.restaurantwaiter.utility.SmartLog;

/**
 * MeyClubSharedPreferences class which saves setting values
 *
 * @author FruitySolution
 */
public class MySharedPreferences {

    private String TAG = getClass().getSimpleName();

    public static final String APPLICATION_INSTALL_FIRST_TIME = "APPLICATION_INSTALL_FIRST_TIME";

    public static final String PREF_SETTING_USER_ID = "PREF_SETTING_USER_ID";

    public static final String PREF_SETTING_USER_NAME = "PREF_SETTING_USER_NAME";

    public static final String PREF_SETTING_USER_PASSWORD = "PREF_SETTING_USER_PASSWORD";

    public static final String PREF_USER_IS_KICK_OUT = "PREF_USER_IS_KICK_OUT";

    public static final String NAME = "NAME";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String POSTAL_CODE = "POSTAL_CODE";
    public static final String TOWN = "TOWN";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String CIVILITY = "CIVILITY";
    public static final String ADDRESS1 = "ADDRESS1";
    public static final String ADDRESS2 = "ADDRESS2";
    public static final String PHONE = "PHONE";

    public static final String IS_AUTO_SCROLL_CAROUSEL = "IS_AUTO_SCROLL_CAROUSEL";
    public static final String IS_ACTIVED = "IS_ACTIVED";

    // For store url for splash , banner , banner home

    public static final String SPLASH_URL = "SPLASH_URL";
    public static final String BANNER_URL = "BANNER_URL";
    public static final String USER_ID = "USER_ID";
    public static final String BANNER_HOME_URL = "BANNER_HOME_URL";
    public static final String TABLE_CLICK = "TABLE_CLICK";

    // ================================================================

    public static MySharedPreferences instance;

    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    /**
     * Constructor
     *
     * @return
     */
//	public static MySharedPreferences getInstance(Context context) {
//		if (instance == null) {
//			instance = new MySharedPreferences();
//			instance.context = context;
//		}
//		return instance;
//	}

    // ======================== UTILITY FUNCTIONS ========================

    // URL Image for getVisuals
    public void setTableClick(String table) {
        putStringValue(TABLE_CLICK, table);
    }

    public String getTableClick() {
      return getStringValue(TABLE_CLICK);
    }

    public void setIsFirstInstall() {
        putBooleanValue(APPLICATION_INSTALL_FIRST_TIME, true);
    }

    public boolean getIsFirstInstall() {
        return getBooleanValue(APPLICATION_INSTALL_FIRST_TIME);
    }

    public void setSplashUrl(String splashUrl) {
        putStringValue(SPLASH_URL, splashUrl);
    }

    public String getSplashUrl() {
        return getStringValue(SPLASH_URL);
    }

    public void setBannerUrl(String bannerUrl) {
        putStringValue(BANNER_URL, bannerUrl);
    }

    public String getBannerUrl() {
        return getStringValue(BANNER_URL);
    }

    public void setUserID(String userID) {
        putStringValue(USER_ID, userID);
    }

    public String getUserID() {
        return getStringValue(USER_ID);
    }

    public void setBannerHomeUrl(String bannerHomeUrl) {
        putStringValue(BANNER_HOME_URL, bannerHomeUrl);
    }

    public void setActived() {
        putBooleanValue(IS_ACTIVED, true);
    }

    public boolean getIsActived() {
        return getBooleanValue(IS_ACTIVED);
    }

    public String getBannnerHomeUrl() {
        return getStringValue(BANNER_HOME_URL);
    }

    // Carsousel auto scroll mode
    public void setAutoScrollMode(boolean isAuto) {
        SmartLog.log(TAG, "Start set autoscroll : " + isAuto);
        putBooleanValue(IS_AUTO_SCROLL_CAROUSEL, isAuto);
    }

    public boolean getAutoScrollMode() {
        SmartLog.log(TAG, "Start get autoscroll : "
                + getBooleanValue(IS_AUTO_SCROLL_CAROUSEL));
        return getBooleanValue(IS_AUTO_SCROLL_CAROUSEL);
    }

    /**
     * Save user information after sign in
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            putStringValue(PREF_SETTING_USER_ID, userInfo.getUserId());
            putStringValue(PREF_SETTING_USER_NAME, userInfo.getUserName());
            putStringValue(PREF_SETTING_USER_PASSWORD,
                    userInfo.getUserPassword());

        }
    }

    /**
     * Get user information
     *
     * @return
     */

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        if (!getStringValue(PREF_SETTING_USER_ID).equalsIgnoreCase("")) {
            userInfo.setUserId(getStringValue(PREF_SETTING_USER_ID));
            userInfo.setUserName(getStringValue(PREF_SETTING_USER_NAME));
            userInfo.setUserPassword(getStringValue(PREF_SETTING_USER_PASSWORD));

        }
        return userInfo;
    }

    // ======================== CORE FUNCTIONS ========================

    /**
     * Save a long integer to SharedPreferences
     *
     * @param key
     * @param n
     */
    public void putLongValue(String key, long n) {
        // SmartLog.log(TAG, "Set long integer value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, n);
        editor.commit();
    }

    /**
     * Read a long integer to SharedPreferences
     *
     * @param key
     * @return
     */
    public long getLongValue(String key) {
        // SmartLog.log(TAG, "Get long integer value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getLong(key, 0);
    }

    /**
     * Save an integer to SharedPreferences
     *
     * @param key
     * @param n
     */
    public void putIntValue(String key, int n) {
        // SmartLog.log(TAG, "Set integer value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, n);
        editor.commit();
    }

    /**
     * Read an integer to SharedPreferences
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        // SmartLog.log(TAG, "Get integer value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getInt(key, 0);
    }

    /**
     * Save an string to SharedPreferences
     *
     * @param key
     * @param s
     */
    public void putStringValue(String key, String s) {
        // SmartLog.log(TAG, "Set string value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.commit();
    }

    /**
     * Read an string to SharedPreferences
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        // SmartLog.log(TAG, "Get string value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getString(key, "");
    }

    /**
     * Read an string to SharedPreferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringValue(String key, String defaultValue) {
        // SmartLog.log(TAG, "Get string value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getString(key, defaultValue);
    }

    /**
     * Save an boolean to SharedPreferences
     *
     * @param key
     */
    public void putBooleanValue(String key, Boolean b) {
        // SmartLog.log(TAG, "Set boolean value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.commit();
    }

    /**
     * Read an boolean to SharedPreferences
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        // SmartLog.log(TAG, "Get boolean value");
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getBoolean(key, false);
    }

    /**
     * Save an float to SharedPreferences
     *
     * @param key
     */
    public void putFloatValue(String key, float f) {
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.commit();
    }

    /**
     * Read an float to SharedPreferences
     *
     * @param key
     * @return
     */
    public float getFloatValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(
                GlobalValue.EVANDRO_DROID_PREFERENCES, 0);
        return pref.getFloat(key, 0.0f);
    }
}
