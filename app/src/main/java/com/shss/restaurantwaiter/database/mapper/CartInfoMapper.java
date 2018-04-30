package com.shss.restaurantwaiter.database.mapper;

import android.database.Cursor;

import com.shss.restaurantwaiter.database.DBKeyConfig;
import com.shss.restaurantwaiter.object.CartInfo;

public class CartInfoMapper implements RowMapper<CartInfo> {

	@Override
	public CartInfo mapRow(Cursor row, int rowNum) {
		CartInfo cartInfo = new CartInfo();
		cartInfo.setId(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_CART_PRODUCTID));
		cartInfo.setIdTable(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_CART_TABLE_ID));
		cartInfo.setImgUrl(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_CART_IMG_URL));
		cartInfo.setNameCart(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_CART_NAME_CART));
		cartInfo.setPrice(CursorParseUtility.getDouble(row,
				DBKeyConfig.KEY_CART_PRICE));
		cartInfo.setNumberCart(CursorParseUtility.getInt(row,
				DBKeyConfig.KEY_CART_NUMBER_CART));
		cartInfo.setNote(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_CART_NOTE));
		return cartInfo;
	}
}
