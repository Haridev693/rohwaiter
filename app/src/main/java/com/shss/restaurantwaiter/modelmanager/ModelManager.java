package com.shss.restaurantwaiter.modelmanager;

import android.content.Context;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.config.WebServiceConfig;
import com.shss.restaurantwaiter.volleynetwork.HttpError;
import com.shss.restaurantwaiter.volleynetwork.HttpGet;
import com.shss.restaurantwaiter.volleynetwork.HttpListener;

import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private static String TAG = "ModelManager";

    public static void getLogin(final Context context, boolean isShowDialog,
                                final ModelManagerListener listener) {
        Map<String, String> params = new HashMap<>();
        new HttpGet(context, WebServiceConfig.getURLLogIn(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void getAllTable(final Context context, boolean isShowDialog,
                                   final ModelManagerListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("id", GlobalValue.preferences.getUserID());
        new HttpGet(context, WebServiceConfig.getURLTable(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void UpdateTable(final Context context, String TableId,
                                   String Status, boolean isShowDialog,
                                   final ModelManagerListener listener) {
        Map<String, String> params = ParameterFactory
                .createUpdateTabe(context, TableId, Status);
        new HttpGet(context, WebServiceConfig.getURLUpdateTable(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void getAllCategory(final Context context, boolean isShowDialog,
                                      final ModelManagerListener listener) {
        Map<String, String> params = new HashMap<>();
        new HttpGet(context, WebServiceConfig.getURLAllCategory(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void getProductbyCategoryId(Context context,
                                              String CategoryId, boolean isShowDialog,
                                              final ModelManagerListener listener) {

        Map<String, String> params = ParameterFactory
                .createProduct(context, CategoryId);
        new HttpGet(context, WebServiceConfig.getURLProduct(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void putJsonCart(Context context, String json,
                                   boolean isShowDialog, final ModelManagerListener listener) {

        Map<String, String> params = ParameterFactory
                .creatPutCart(context, json);
        new HttpGet(context, WebServiceConfig.getURLJsonCart(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void getShowCart(Context context, String tableId,
                                   boolean isShowDialog, final ModelManagerListener listener) {

        Map<String, String> params = ParameterFactory
                .creatshowCart(context, tableId);
        new HttpGet(context, WebServiceConfig.getURLShowCart(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }

    public static void putBooking(Context context, String tableId,
                                  String CARTID, boolean isShowDialog,
                                  final ModelManagerListener listener) {

        Map<String, String> params = ParameterFactory
                .creatputHistory(context, tableId, CARTID);
        new HttpGet(context, WebServiceConfig.getURLBooking(context), params, isShowDialog, new HttpListener() {
            @Override
            public void onHttpRespones(Object respones) {
                if (respones != null) {
                    listener.onSuccess(respones.toString());
                } else {
                    listener.onError(null);
                }
            }
        }, new HttpError() {
            @Override
            public void onHttpError(VolleyError volleyError) {

            }
        });
    }
}