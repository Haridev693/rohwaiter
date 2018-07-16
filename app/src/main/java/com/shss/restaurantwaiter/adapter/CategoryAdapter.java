package com.shss.restaurantwaiter.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.CategoryInfo;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class CategoryAdapter extends BaseAdapter {
	static class ViewHolder {
		private ImageView imgProduct;
		private ProgressBar progress;
		private TextView lblNameCategory;
	}

	private ArrayList<CategoryInfo> listCategory;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	public Context context;
	private AQuery aq;
	public String TAG = "Demo";

	public CategoryAdapter(Activity activity, ArrayList<CategoryInfo> d) {
		listCategory = d;
		context = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		aq = new AQuery(activity);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCategory.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listCategory.get(position);
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
			convertView = inflater.inflate(R.layout.row_item_listview_category,
					null);
			holder = new ViewHolder();
			holder.imgProduct = (ImageView) convertView
					.findViewById(R.id.imgProduct);
			holder.lblNameCategory = (TextView) convertView
					.findViewById(R.id.lblNameCategory);

			convertView.setTag(holder);
		}
		CategoryInfo o = listCategory.get(position);
		if (o != null) {
			holder = (ViewHolder) convertView.getTag();
			// holder.imgProduct.setImageDrawable(AssetUtil.getDrawable(context,
			// o.getImgUrl()));
			holder.lblNameCategory.setText(o.getName());
			holder.lblNameCategory.setSelected(true);
			if(o.getImgUrl().isEmpty())
			{
				aq.id(holder.imgProduct).image(R.drawable.img_notfound);
			}
			else {
				aq.id(holder.imgProduct).image(o.getImgUrl());
			}
		} else {
			Log.i(TAG, "Null Object !");
		}
		return convertView;

	}
}
