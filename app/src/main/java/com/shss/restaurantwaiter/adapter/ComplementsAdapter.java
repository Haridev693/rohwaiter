package com.shss.restaurantwaiter.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shss.restaurantwaiter.object.ComplementsProductInfo;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;

public class ComplementsAdapter extends BaseAdapter {
	static class ViewHolder {

	}

	private ArrayList<ComplementsProductInfo> listComplements;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	public Context context;

	public String TAG = "Complements";

	public ComplementsAdapter(Activity activity,
			ArrayList<ComplementsProductInfo> d) {
		listComplements = d;
		context = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listComplements.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listComplements.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(android.R.layout.simple_list_item_1,
					null);
			holder = new ViewHolder();
			/*
			 * 
			 * 
			 */
			convertView.setTag(holder);
		}
		ComplementsProductInfo o = listComplements.get(position);
		if (o != null) {
			holder = (ViewHolder) convertView.getTag();
			/*
			 * 
			 * 
			 */
		} else {
			Log.i(TAG, "Null Object !");
		}
		return convertView;

	}

}
