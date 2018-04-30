/*
 * Name: $RCSfile: DatabaseUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 2:55:54 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.database;

import java.util.ArrayList;

import android.content.Context;

import com.shss.restaurantwaiter.database.binder.AccountInfoBinder;
import com.shss.restaurantwaiter.database.binder.CartInfoBinder;
import com.shss.restaurantwaiter.database.binder.CategoryInfoBinder;
import com.shss.restaurantwaiter.database.binder.ProductInfoBinder;
import com.shss.restaurantwaiter.database.binder.TablesInfoBinder;
import com.shss.restaurantwaiter.database.binder.UserInfoBinder;
import com.shss.restaurantwaiter.database.mapper.CartInfoMapper;
import com.shss.restaurantwaiter.database.mapper.CategoryInfoMapper;
import com.shss.restaurantwaiter.database.mapper.ProductInfoMapper;
import com.shss.restaurantwaiter.database.mapper.TablesInfoMapper;
import com.shss.restaurantwaiter.database.mapper.UserInfoMapper;
import com.shss.restaurantwaiter.object.AccountInfo;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.object.CategoryInfo;
import com.shss.restaurantwaiter.object.ProductInfo;
import com.shss.restaurantwaiter.object.TablesInfo;
import com.shss.restaurantwaiter.object.UserInfo;

/**
 * DatabaseUtility
 * 
 * @author Star
 */
public final class DatabaseUtility {

	private PrepareStatement statement;

	public DatabaseUtility(Context context) {
		statement = new PrepareStatement(context);
	}

	private static String STRING_SQL_INSERT_INTO_LOGIN = "INSERT OR IGNORE INTO Login("
			+ DBKeyConfig.KEY_USER_ID
			+ ","
			+ DBKeyConfig.KEY_USER_NAME
			+ ","
			+ DBKeyConfig.KEY_PASS + ")VALUES (?,?,?)";

	private static String STRING_SQL_INSERT_INTO_TABLES = "INSERT OR IGNORE INTO Tables("
			+ DBKeyConfig.KEY_TABLES_ID
			+ ","
			+ DBKeyConfig.KEY_TABLES_STATUS
			+ ")VALUES(?,?)";

	private static String STRING_SQL_INSERT_INTO_PRODUCT = "INSERT OR IGNORE INTO Product("
			+ DBKeyConfig.KEY_PRO_ID
			+ ","
			+ DBKeyConfig.KEY_PRO_CODE
			+ ","
			+ DBKeyConfig.KEY_PRO_PRICE
			+ ","
			+ DBKeyConfig.KEY_PRO_NUMBER_NAME
			+ "," + DBKeyConfig.KEY_PRO_NAME + ")VALUES(?,?,?,?,?)";

	private static String STRING_SQL_INSERT_INTO_CATEGORY = "INSERT OR IGNORE INTO Category("
			+ DBKeyConfig.KEY_CATEGORY_IMAGE_URL
			+ ","
			+ DBKeyConfig.KEY_CATEGORY_NAME + ")VALUES(?,?)";

	private static String STRING_SQL_INSERT_INTO_CART = "INSERT OR IGNORE INTO Cart("
			+ DBKeyConfig.KEY_CART_PRODUCTID
			+ ","
			+ DBKeyConfig.KEY_CART_TABLE_ID
			+ ","
			+ DBKeyConfig.KEY_CART_IMG_URL
			+ ","

			+ DBKeyConfig.KEY_CART_NAME_CART
			+ ","
			+ DBKeyConfig.KEY_CART_PRICE
			+ ","
			+ DBKeyConfig.KEY_CART_NOTE
			+ ","
			+ DBKeyConfig.KEY_CART_NUMBER_CART + ")VALUES(?,?,?,?,?,?,?)";

	private static String STRING_SQL_INSERT_INTO_ACCOUNT = "INSERT OR IGNORE INTO Account("
			+ DBKeyConfig.KEY_ACCOUNT_ID
			+ ","
			+ DBKeyConfig.KEY_ACCOUNT_CODE
			+ ","
			+ DBKeyConfig.KEY_ACCOUNT_NAME
			+ ","
			+ DBKeyConfig.KEY_ACCOUNT_PRICE + ")VALUES(?,?,?,?)";

	// =============FUNCTIONS FOR LOGIN=======================//
	public boolean insertLogin(Context context, UserInfo userInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_LOGIN, userInfo,
				new UserInfoBinder());
	}

	public ArrayList<UserInfo> getUserInfo(Context context, String userId) {
		return statement.select(DBKeyConfig.TABLE_LOGIN, "*",
				DBKeyConfig.KEY_PASS + "=='" + userId + "'",
				new UserInfoMapper());
	}

	public ArrayList<UserInfo> getUser(Context context) {
		return statement.select(DBKeyConfig.TABLE_LOGIN, "*", "",
				new UserInfoMapper());
	}

	// ===============FUNCTIONS FOR TABLES======================//
	public boolean insertTables(Context context, TablesInfo tablesInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_TABLES, tablesInfo,
				new TablesInfoBinder());
	}

	public ArrayList<TablesInfo> getListTables(Context context) {
		return statement.select(DBKeyConfig.TABLE_TABLES, "*", "",
				new TablesInfoMapper());
	}

	public boolean deleteTables(Context context, String tablesId) {
		return statement.query("Delete from " + DBKeyConfig.TABLE_TABLES
				+ " where " + DBKeyConfig.KEY_TABLES_ID + "=?",
				new String[] { tablesId });
	}

	public boolean updateStatusTable(Context context, int status,
			String tableId) {
		return statement.update(DBKeyConfig.TABLE_TABLES,
				DBKeyConfig.KEY_TABLES_STATUS + " =='" + status + "'",
				DBKeyConfig.KEY_TABLES_ID + "='" + tableId + "'");
	}

	// ===============FUNCTIONS FOR PRODUCT======================//
	public boolean insertProduct(Context context, ProductInfo productInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_PRODUCT, productInfo,
				new ProductInfoBinder());
	}

	public ArrayList<ProductInfo> getListProduct(Context context,
			String categoryName) {
		return statement.select(DBKeyConfig.TABLE_PRODUCT, "*",
				DBKeyConfig.KEY_CATEGORY_NAME + "=='" + categoryName + "'",
				new ProductInfoMapper());
	}

	public boolean updateProduct(Context context, int numberPro,
			String newPro) {
		return statement.update(DBKeyConfig.TABLE_PRODUCT,
				DBKeyConfig.KEY_PRO_NUMBER_NAME + " =='" + numberPro + "'",
				DBKeyConfig.KEY_PRO_ID + "='" + newPro + "'");
	}

	// ===============FUNCTIONS FOR CATEGORY======================//
	public boolean insertCategory(Context context,
			CategoryInfo categoryInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_CATEGORY, categoryInfo,
				new CategoryInfoBinder());
	}

	public ArrayList<CategoryInfo> getListCategory(Context context) {
		return statement.select(DBKeyConfig.TABLE_CATEGORY, "*", "",
				new CategoryInfoMapper());
	}

	// ===============FUNCTIONS FOR CART======================//
	public boolean insertCart(Context context, CartInfo cartInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_CART, cartInfo,
				new CartInfoBinder());
	}

	public ArrayList<CartInfo> getListCart(Context context,
			String tableId) {
		return statement.select(DBKeyConfig.TABLE_CART, "*",
				DBKeyConfig.KEY_CART_TABLE_ID + "=='" + tableId + "'",
				new CartInfoMapper());
	}

	public ArrayList<CartInfo> getCart(Context context, String tableId,
			String nameCart, String ProductId) {
		return statement.select(DBKeyConfig.TABLE_CART, "*",
				DBKeyConfig.KEY_CART_TABLE_ID + "=='" + tableId + "'" + "and"
						+ " " + DBKeyConfig.KEY_CART_NAME_CART + "=='"
						+ nameCart + "'" + "and" + " "
						+ DBKeyConfig.KEY_CART_PRODUCTID + "=='" + ProductId
						+ "'", new CartInfoMapper());
	}

	public ArrayList<CartInfo> getCartNote(Context context,
			String tableId, String note, String ProductId) {
		return statement.select(DBKeyConfig.TABLE_CART, "*",
				DBKeyConfig.KEY_CART_TABLE_ID + "=='" + tableId + "'" + "and"
						+ " " + DBKeyConfig.KEY_CART_NOTE + "=='" + note + "'"
						+ "and" + " " + DBKeyConfig.KEY_CART_PRODUCTID + "=='"
						+ ProductId + "'", new CartInfoMapper());
	}

	public ArrayList<CartInfo> getListCart1(Context context,
			String tableId, String idcart) {
		return statement.select(DBKeyConfig.TABLE_CART, "*",
				DBKeyConfig.KEY_CART_TABLE_ID + "=='" + tableId + "'" + "and"
						+ " " + DBKeyConfig.KEY_CART_PRODUCTID + "=='" + idcart
						+ "'", new CartInfoMapper());
	}

	public boolean deleteCart(Context context, String cartname) {
		return statement.query("Delete from " + DBKeyConfig.TABLE_CART
				+ " where " + DBKeyConfig.KEY_CART_NAME_CART + "='" + cartname
				+ "'", null);
	}

	public boolean deleteTableCart(Context context, String Idtable) {
		return statement.query("Delete from " + DBKeyConfig.TABLE_CART
				+ " where " + DBKeyConfig.KEY_CART_TABLE_ID + "='" + Idtable
				+ "'", null);
	}

	public boolean deleteCartId(Context context, String cartId,
			String tableId) {
		return statement.query("Delete from " + DBKeyConfig.TABLE_CART
				+ " where " + DBKeyConfig.KEY_CART_PRODUCTID + "='" + cartId
				+ "'" + "and" + " " + DBKeyConfig.KEY_CART_TABLE_ID + "='"
				+ tableId + "'", null);
	}

	public boolean updateCart(Context context, int numberCart,
			String newCart, String IdTable) {
		return statement.update(DBKeyConfig.TABLE_CART,
				DBKeyConfig.KEY_CART_NUMBER_CART + " =='" + numberCart + "'",
				DBKeyConfig.KEY_CART_PRODUCTID + "='" + newCart + "'" + "and"
						+ " " + DBKeyConfig.KEY_CART_TABLE_ID + "='" + IdTable
						+ "'");
	}

	public boolean updateCartNote(Context context, String note,
			String IdTable, String nameCart, String ProductId) {
		return statement.update(DBKeyConfig.TABLE_CART,
				DBKeyConfig.KEY_CART_NOTE + " =='" + note + "'",
				DBKeyConfig.KEY_CART_TABLE_ID + "='" + IdTable + "'" + "and"
						+ " " + DBKeyConfig.KEY_CART_NAME_CART + "=='"
						+ nameCart + "'" + "and" + " "
						+ DBKeyConfig.KEY_CART_PRODUCTID + "=='" + ProductId
						+ "'");
	}

	// ===============FUNCTIONS FOR ACCOUNT======================//
	public boolean insertAccount(Context context, AccountInfo accountInfo) {
		return statement.insert(STRING_SQL_INSERT_INTO_ACCOUNT, accountInfo,
				new AccountInfoBinder());
	}
}
