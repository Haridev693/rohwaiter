package com.shss.restaurantwaiter.json.util;

import android.util.Log;

import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.object.CategoryInfo;
import com.shss.restaurantwaiter.object.ProductInfo;
import com.shss.restaurantwaiter.object.RestAddress;
import com.shss.restaurantwaiter.object.TablesInfo;
import com.shss.restaurantwaiter.object.UserInfo;
import com.shss.restaurantwaiter.utility.GlobalVariable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * ParserUtility supports to parser http response
 *
 * @author Lemon
 */
public final class ParserUtility {
    // ----------------------
    private final static String KEY_DATA = "data";
    private final static String KEY_LOGIN = "login";

    // -----------------------

    public static final String TAG = "ParserUtility";

    public static ArrayList<UserInfo> parseListLogin(String json) {
        ArrayList<UserInfo> arr = new ArrayList<UserInfo>();
        RestAddress r = new RestAddress();

        UserInfo userInfo = null;
        try {
            JSONObject object = new JSONObject(json);


            JSONObject item;
            JSONArray items = object.getJSONArray(KEY_DATA);
            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);
                userInfo = new UserInfo();
                userInfo.setUserId(item.getString("id"));
                userInfo.setUserName(item.getString("nameOperator"));
                userInfo.setUserPassword(item.getString("passOperator"));
                arr.add(userInfo);
            }

            JSONObject jshop = object.getJSONObject("setting");

            r.Name = jshop.getString("rest_name");

            r.AddressLine1 = jshop.getString("address_1");
            r.AddressLine2 = jshop.getString("address_2");
            r.SalesInvoice = jshop.getString("sales_invoice");
            r.Footer1 = jshop.getString("footer_1");
            r.Footer2 = jshop.getString("footer_2");
            r.Footer3 = jshop.getString("footer_3");
            r.PrinterIP="";
            r.BillPrint=false;
            if(!jshop.isNull("printer_ip"))
            {
                            r.PrinterIP = jshop.getString("printer_ip");

            }
            if(!jshop.isNull("app_bill"))
            {
                            r.BillPrint = jshop.getInt("app_bill")==1;
            }

            GlobalVariable.restAddress = r;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    ;

    public static ArrayList<TablesInfo> parseAllTable(String json) {
        ArrayList<TablesInfo> arr = new ArrayList<TablesInfo>();
        TablesInfo tablesInfo = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item;
            JSONArray items = object.getJSONArray(KEY_DATA);
            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);
                tablesInfo = new TablesInfo();
                tablesInfo.setTablesId(item.getString("id"));
                tablesInfo.setStatus(item.getInt("status"));
                arr.add(tablesInfo);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    ;

    public static ArrayList<CategoryInfo> parseAllCategory(String json) {
        ArrayList<CategoryInfo> arr = new ArrayList<CategoryInfo>();
        CategoryInfo categoryInfo = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item;
            JSONArray items = object.getJSONArray(KEY_DATA);
            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);
                categoryInfo = new CategoryInfo();
                categoryInfo.setId(item.getString("id"));
                categoryInfo.setName(item.getString("name"));
                categoryInfo.setImgUrl(item.getString("imageUrl"));
                arr.add(categoryInfo);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    ;

    public static ArrayList<ProductInfo> parseProductbyCategoryId(String json) {
        ArrayList<ProductInfo> arr = new ArrayList<ProductInfo>();
        ProductInfo productInfo = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item;
            JSONArray items = object.getJSONArray(KEY_DATA);
            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);
                productInfo = new ProductInfo();
                productInfo.setId(item.getString("id"));
                productInfo.setCategoryId(item.getString("categoryId"));
                productInfo.setName(item.getString("name"));
                productInfo.setCode(item.getString("code"));
                productInfo.setPrice(item.getDouble("price"));
                productInfo.setNumberName(item.getInt("numbername"));
                productInfo.setTotalqty(item.getString("Totalqty"));
                arr.add(productInfo);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    ;

    public static String putJsonCart(ArrayList<CartInfo> arrCart) {
        JSONArray jsonArray = new JSONArray();
        String timeStamp = GlobalValue.timeStampForTable.get(GlobalValue.preferences.getTableClick());
        if (timeStamp == null || timeStamp.equals("") || timeStamp.equals("null")) {
            GlobalValue.timeStampForTable.put(GlobalValue.preferences.getTableClick(), Calendar.getInstance().getTimeInMillis() / 1000 + "");
        }

        for (int i = 0; i < arrCart.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("productId", arrCart.get(i).getId());
                object.put("tableId", arrCart.get(i).getTableId());
                object.put("cartName", arrCart.get(i).getNameCart());
                object.put("price", arrCart.get(i).getPrice() + "");
                object.put("numberCart", arrCart.get(i).getNumberCart());
                object.put("note", arrCart.get(i).getNote());
                object.put("cartID", GlobalValue.timeStampForTable.get(GlobalValue.preferences.getTableClick()));
                jsonArray.put(object);
            } catch (JSONException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        JSONObject jsoncart = new JSONObject();
        try {
            jsoncart.put("data", jsonArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String jsonStr = jsoncart.toString();
        Log.e(TAG, "putJsonCart: " + jsonStr);
        return jsonStr;

        // System.out.println("jsonString: " + jsonStr);
    }

    public static ArrayList<CartInfo> parseShowCart(String json) {
        ArrayList<CartInfo> arr = new ArrayList<CartInfo>();
        CartInfo cartInfo = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject item;
            JSONArray items = object.getJSONArray(KEY_DATA);
            GlobalVariable.taxAmount = Integer.parseInt(object.getString("tax"));
            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);
                cartInfo = new CartInfo();
                cartInfo.setId(item.getString("id"));
                cartInfo.setProductId(item.getString("productId"));
                cartInfo.setTableId(item.getString("tableId"));
                cartInfo.setImgUrl(item.getString("ImgUrl"));
                cartInfo.setNameCart(item.getString("cartName"));
                cartInfo.setPrice(Double.parseDouble(item.getString("price")));
                cartInfo.setNumberCart(Integer.parseInt(item
                        .getString("numberCart")));
                cartInfo.setCARTID(item.getString("cartId"));
                arr.add(cartInfo);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }

    ;
}
