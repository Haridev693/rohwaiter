package com.shss.restaurantwaiter.database.binder;

import com.shss.restaurantwaiter.object.CategoryInfo;

import android.database.sqlite.SQLiteStatement;

public class CategoryInfoBinder implements ParameterBinder {

	@Override
	public void bind(SQLiteStatement st, Object object) {
		CategoryInfo categoryInfo = (CategoryInfo) object;
		st.bindString(1, categoryInfo.getImgUrl());
		st.bindString(2, categoryInfo.getName());

	}

}
