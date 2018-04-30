package com.shss.restaurantwaiter.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.activity.ComplementsProductActivity;
import com.shss.restaurantwaiter.utility.SmartLog;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;
import com.shss.restaurantwaiter.widget.AutoBgButton;

public class CartAdapter extends BaseAdapter {

	public interface UpdateQuantityListener {
		void onUpdate(boolean isPlus, int index);

	}

	static class ViewHolder {
		private AutoBgButton btnReduction, btnInsumos, btnIncrease;
		private ImageView imgCart, imgCheckTrue;
		private TextView lblPrice, lblNameCart, lblNumberCart, lblTotalPrice;
	}

	private CartInfo cartinfo;
	private ArrayList<CartInfo> listCart;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	public Context context;
	public String TAG = "Cart";
	UpdateQuantityListener listener;
	private AQuery aq;
	/**
	 * @return the listener
	 */
	public UpdateQuantityListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(UpdateQuantityListener listener) {
		this.listener = listener;
	}

	public CartAdapter(Activity activity, ArrayList<CartInfo> d,
			UpdateQuantityListener listener) {
		listCart = d;
		context = activity;
		this.listener = listener;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		aq = new AQuery(activity);
	}
	public CartAdapter(Activity activity, ArrayList<CartInfo> d){
		listCart = d;
		context = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		aq = new AQuery(activity);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCart.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listCart.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_item_cart, null);
			holder = new ViewHolder();
			holder.imgCart = (ImageView) convertView.findViewById(R.id.imgCart);
			holder.imgCheckTrue = (ImageView) convertView
					.findViewById(R.id.imgCheckTrue);
			holder.btnIncrease = (AutoBgButton) convertView
					.findViewById(R.id.btnIncrease);
			holder.btnInsumos = (AutoBgButton) convertView
					.findViewById(R.id.btnInsumos);
			holder.btnReduction = (AutoBgButton) convertView
					.findViewById(R.id.btnReduction);
			holder.lblNameCart = (TextView) convertView
					.findViewById(R.id.lblNameCart);
			holder.lblNumberCart = (TextView) convertView
					.findViewById(R.id.lblNumberCart);
			holder.lblPrice = (TextView) convertView
					.findViewById(R.id.lblPrice);
			holder.lblTotalPrice = (TextView) convertView
					.findViewById(R.id.lblTotalPrice);
			convertView.setTag(holder);
		}
		final CartInfo o = listCart.get(position);
		if (o != null) {
			holder = (ViewHolder) convertView.getTag();
//			holder.imgCart.setImageDrawable(AssetUtil.getDrawable(context,
//					o.getImgUrl()));
			aq.id(holder.imgCart).image(o.getImgUrl());
			holder.lblNameCart.setText(o.getNameCart());
			holder.btnInsumos.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					gotoActive(o, ComplementsProductActivity.class);

				}

			});
			holder.lblNameCart.setSelected(true);
			if (o.getNumberCart() < 10) {
				holder.lblNumberCart.setText("0" + o.getNumberCart() + "");
			} else {
				holder.lblNumberCart.setText(o.getNumberCart() + "");
			}
			holder.lblPrice.setText(o.getPrice() + ""
					+ context.getString(R.string.dola));
			final double total = o.getNumberCart() * o.getPrice();
			holder.lblTotalPrice.setText(total + ""
					+ context.getString(R.string.dola));
			holder.btnIncrease.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onUpdate(true, position);

				}
			});
			holder.btnReduction.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onUpdate(false, position);

				}
			});
			if (!o.isTrue()) {
				holder.imgCheckTrue.setVisibility(View.GONE);
			} else {
				holder.imgCheckTrue.setVisibility(View.VISIBLE);
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					o.setTrue(!o.isTrue());
					SmartLog.log("", "isCheck===" + o.isTrue());
					notifyDataSetChanged();

				}
			});
		} else {
			Log.i(TAG, "Null Object !");
		}

		return convertView;

	}

	public void gotoActive(CartInfo cart, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		Bundle bundle = new Bundle();
		bundle.putString("NameCart", cart.getNameCart());
		bundle.putString("IdTable", cart.getIdTable());
		bundle.putString("IdProduct", cart.getId());
		intent.putExtra("mybundle", bundle);
		// SmartLog.log(bundle.toString(), "bundle");
		context.startActivity(intent);
	}
}
