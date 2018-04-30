package com.shss.restaurantwaiter.database.binder;

import com.shss.restaurantwaiter.object.AccountInfo;

import android.database.sqlite.SQLiteStatement;

public class AccountInfoBinder implements ParameterBinder {

	@Override
	public void bind(SQLiteStatement st, Object object) {
		AccountInfo accountInfo = (AccountInfo) object;

		st.bindString(1, accountInfo.getId());
		st.bindString(2, String.valueOf(accountInfo.getCode()));
		st.bindString(3, accountInfo.getName());
		st.bindDouble(4, accountInfo.getPrice());

	}

}
