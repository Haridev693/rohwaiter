package com.shss.restaurantwaiter.database.mapper;

import android.database.Cursor;

import com.shss.restaurantwaiter.database.DBKeyConfig;
import com.shss.restaurantwaiter.object.UserInfo;

public class UserInfoMapper implements RowMapper<UserInfo> {

	@Override
	public UserInfo mapRow(Cursor row, int rowNum) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_USER_ID));
		userInfo.setUserName(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_USER_NAME));
		userInfo.setUserPassword(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_PASS));
		return userInfo;
	}

}
