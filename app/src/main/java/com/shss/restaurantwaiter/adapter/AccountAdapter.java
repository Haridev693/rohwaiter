package com.shss.restaurantwaiter.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;

public class AccountAdapter extends BaseAdapter {
	static class ViewHolder {
		private TextView lblCode, lblName, lblPrice;
	}

	private ArrayList<CartInfo> arrAccount;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	public Context context;

	public String TAG = "Account";

	public AccountAdapter(Activity activity, ArrayList<CartInfo> d) {
		arrAccount = d;
		context = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrAccount.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrAccount.get(position);
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
			convertView = inflater.inflate(R.layout.row_item_account, null);
			holder = new ViewHolder();
			holder.lblCode = (TextView) convertView.findViewById(R.id.lblCode);
			holder.lblName = (TextView) convertView.findViewById(R.id.lblName);
			holder.lblPrice = (TextView) convertView
					.findViewById(R.id.lblPrice);
			convertView.setTag(holder);
		}
		CartInfo o = arrAccount.get(position);
		if (o != null) {
			holder = (ViewHolder) convertView.getTag();
			holder.lblCode.setText(o.getNumberCart() + "");
			holder.lblName.setText(o.getNameCart());
			final double total = o.getPrice();
			holder.lblPrice.setText(total + context.getString(R.string.dola));
		} else {
			Log.i(TAG, "Null Object !");
		}
		return convertView;

	}
}
