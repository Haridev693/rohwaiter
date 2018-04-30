package com.shss.restaurantwaiter.database.binder;

import com.shss.restaurantwaiter.object.ProductInfo;

import android.database.sqlite.SQLiteStatement;

public class ProductInfoBinder implements ParameterBinder {

	@Override
	public void bind(SQLiteStatement st, Object object) {
		ProductInfo productInfo = (ProductInfo) object;
		st.bindString(1, productInfo.getId());
		st.bindString(2, productInfo.getCode());
		st.bindDouble(3, productInfo.getPrice());
		st.bindString(4, String.valueOf(productInfo.getNumberName()));
		st.bindString(5, productInfo.getName());

	}

}
