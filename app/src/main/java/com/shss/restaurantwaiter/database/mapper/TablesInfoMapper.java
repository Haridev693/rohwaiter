package com.shss.restaurantwaiter.database.mapper;

import android.database.Cursor;

import com.shss.restaurantwaiter.database.DBKeyConfig;
import com.shss.restaurantwaiter.object.TablesInfo;

public class TablesInfoMapper implements RowMapper<TablesInfo> {

	@Override
	public TablesInfo mapRow(Cursor row, int rowNum) {
		TablesInfo tablesInfo = new TablesInfo();
		tablesInfo.setTablesId(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_TABLES_ID));
		tablesInfo.setStatus(CursorParseUtility.getInt(row,
				DBKeyConfig.KEY_TABLES_STATUS));

		return tablesInfo;
	}

}
