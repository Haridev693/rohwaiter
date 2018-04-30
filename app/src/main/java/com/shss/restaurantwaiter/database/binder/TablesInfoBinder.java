package com.shss.restaurantwaiter.database.binder;

import com.shss.restaurantwaiter.object.TablesInfo;

import android.database.sqlite.SQLiteStatement;

public class TablesInfoBinder implements ParameterBinder {

	@Override
	public void bind(SQLiteStatement st, Object object) {
		TablesInfo tablesInfo = (TablesInfo) object;
		st.bindString(1, tablesInfo.getTablesId());
		st.bindDouble(2, tablesInfo.getStatus());
		// st.bindString(3, tablesInfo.getNumberTable());

	}
}
