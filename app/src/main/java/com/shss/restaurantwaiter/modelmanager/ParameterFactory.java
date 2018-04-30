package com.shss.restaurantwaiter.modelmanager;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * ParameterFactory class builds parameters for web service apis
 * 
 */
public final class ParameterFactory {
	public static Map<String, String> createProduct(Context context,
			String CategoryID) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("CategoryId", CategoryID);
		return parameters;
	}

	public static Map<String, String> createUpdateTabe(Context context,
			String TableId, String Status) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("TableId", TableId);
		parameters.put("status", Status);
		return parameters;
	}

	public static Map<String, String> creatPutCart(Context context, String json) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("json", json);
		return parameters;
	}

	public static Map<String, String> creatshowCart(Context context,
			String tableId) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("tableId", tableId);
		return parameters;
	}

	public static Map<String, String> creatputHistory(Context context,
			String tableId, String CARTID) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("tableId", tableId);
		parameters.put("cartId", CARTID);
		return parameters;
	}
}
