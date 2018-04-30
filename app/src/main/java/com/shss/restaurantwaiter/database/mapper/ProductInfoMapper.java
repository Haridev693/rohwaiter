package com.shss.restaurantwaiter.database.mapper;

import android.database.Cursor;

import com.shss.restaurantwaiter.database.DBKeyConfig;
import com.shss.restaurantwaiter.object.ProductInfo;

public class ProductInfoMapper implements RowMapper<ProductInfo> {

	@Override
	public ProductInfo mapRow(Cursor row, int rowNum) {
		ProductInfo productInfo = new ProductInfo();

		productInfo.setId(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_PRO_ID));
		productInfo.setCode(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_PRO_CODE));
		productInfo.setPrice(CursorParseUtility.getDouble(row,
				DBKeyConfig.KEY_PRO_PRICE));
		productInfo.setNumberName(CursorParseUtility.getInt(row,
				DBKeyConfig.KEY_PRO_NUMBER_NAME));
		productInfo.setName(CursorParseUtility.getString(row,
				DBKeyConfig.KEY_PRO_NAME));
		return productInfo;
	}

}
