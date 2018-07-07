package com.shss.restaurantwaiter.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.object.ProductInfo;
import com.shss.restaurantwaiter.utility.SmartLog;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;

public class ProductAdapter extends BaseAdapter implements Filterable {
	static class ViewHolder {
		private Button imgAddProduct, btnRemoveProduct;
		private TextView lblNameProduct, lblCode, lblPrice, lblNumBerName;
	}
	private ValueFilter valueFilter;
	private ArrayList<ProductInfo> productobj_filler;
	private ArrayList<CartInfo> arrCart;
	private ArrayList<ProductInfo> listProduct;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	public Context context;
	private CartInfo cartinfo;

	public String TAG = "Product";

	public ProductAdapter(Activity activity, ArrayList<ProductInfo> d) {
		listProduct = d;
		context = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		productobj_filler = d;
		getFilter();
	}

	@Override
	public int getCount() {
		return listProduct.size();
	}

	@Override
	public Object getItem(int position) {

		return listProduct.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_item_product, null);
			holder = new ViewHolder();
			holder.lblNameProduct = (TextView) convertView
					.findViewById(R.id.lblNameProduct);
			holder.lblNameProduct.setSelected(true);
			holder.lblCode = (TextView) convertView.findViewById(R.id.lblCode);
			holder.lblPrice = (TextView) convertView
					.findViewById(R.id.lblPrice);
			holder.lblNumBerName = (TextView) convertView
					.findViewById(R.id.lblNumBerName);
			holder.imgAddProduct = (Button) convertView
					.findViewById(R.id.imgAddProduct);
			holder.btnRemoveProduct = (Button) convertView
					.findViewById(R.id.btnRemoveProduct);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ProductInfo o = listProduct.get(position);
		if (o != null) {
			holder.lblNameProduct.setText(o.getName());
			holder.lblNameProduct.setSelected(true);
			holder.lblCode.setText(o.getCode());
			holder.lblPrice.setText(o.getPrice() + " "
					+ context.getString(R.string.dola));
			holder.lblNumBerName.setText(" " + "(0" + ")");
			arrCart = new ArrayList<CartInfo>();
			arrCart = GlobalValue.databaseUtility.getListCart1(context, o.getIdTable(),
					o.getId());
			if (arrCart.size() == 0) {
				holder.lblNumBerName.setText(" " + "(" + o.getNumberName()
						+ ")");
			} else {
				for (int i = 0; i < arrCart.size(); i++) {
					holder.lblNumBerName.setText(" " + "("
							+ arrCart.get(i).getNumberCart() + ")");
				}
			}
			holder.imgAddProduct.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					arrCart = new ArrayList<CartInfo>();
					arrCart = GlobalValue.databaseUtility.getListCart1(context,
							o.getIdTable(), o.getId());
					Log.w("ProductQuantity",o.getTotalqty());
					if(Integer.parseInt(o.getTotalqty())!=-1)
					{
						if(arrCart.size()==0)
						{
							if(Integer.parseInt(o.getTotalqty())>=1)
							{
								if (arrCart.size() == 0) {
									o.setNumberName(o.getNumberName() + 1);
									cartinfo = new CartInfo();
									cartinfo.setId(o.getId());
									cartinfo.setIdTable(o.getIdTable());
									cartinfo.setImgUrl(o.getImgCategory());
									cartinfo.setNumberCart(o.getNumberName());
									cartinfo.setNameCart(o.getName());
									cartinfo.setPrice(o.getPrice());
									cartinfo.setNote("");
									SmartLog.log("Product",
											"NumberName===" + o.getNumberName());
									GlobalValue.databaseUtility.insertCart(context, cartinfo);
									GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
											o.getId(), o.getIdTable());
								} else {
									for (int i = 0; i < arrCart.size(); i++) {
										o.setNumberName(arrCart.get(i).getNumberCart() + 1);
									}
									GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
											o.getId(), o.getIdTable());
								}

								for (int i = 0; i < arrCart.size(); i++) {
									if (arrCart.get(i).getIdTable()
											.equalsIgnoreCase(o.getIdTable())) {
										GlobalValue.databaseUtility.updateCart(context,
												o.getNumberName(), o.getId(),
												o.getIdTable());
									}
								}
							}
							else
							{
								Toast.makeText(context, "No Stock", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							if(Integer.parseInt(o.getTotalqty())>=(arrCart.get(0).getNumberCart()+1))
							{
								if (arrCart.size() == 0) {
									o.setNumberName(o.getNumberName() + 1);
									cartinfo = new CartInfo();
									cartinfo.setId(o.getId());
									cartinfo.setIdTable(o.getIdTable());
									cartinfo.setImgUrl(o.getImgCategory());
									cartinfo.setNumberCart(o.getNumberName());
									cartinfo.setNameCart(o.getName());
									cartinfo.setPrice(o.getPrice());
									cartinfo.setNote("");
									SmartLog.log("Product",
											"NumberName===" + o.getNumberName());
									GlobalValue.databaseUtility.insertCart(context, cartinfo);
									GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
											o.getId(), o.getIdTable());
								} else {
									for (int i = 0; i < arrCart.size(); i++) {
										o.setNumberName(arrCart.get(i).getNumberCart() + 1);
									}
									GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
											o.getId(), o.getIdTable());
								}

								for (int i = 0; i < arrCart.size(); i++) {
									if (arrCart.get(i).getIdTable()
											.equalsIgnoreCase(o.getIdTable())) {
										GlobalValue.databaseUtility.updateCart(context,
												o.getNumberName(), o.getId(),
												o.getIdTable());
									}
								}
							}
							else
							{
								Toast.makeText(context, "No Stock", Toast.LENGTH_SHORT).show();
							}
						}
					}
					else
					{
						if (arrCart.size() == 0) {
							o.setNumberName(o.getNumberName() + 1);
							cartinfo = new CartInfo();
							cartinfo.setId(o.getId());
							cartinfo.setIdTable(o.getIdTable());
							cartinfo.setImgUrl(o.getImgCategory());
							cartinfo.setNumberCart(o.getNumberName());
							cartinfo.setNameCart(o.getName());
							cartinfo.setPrice(o.getPrice());
							cartinfo.setNote("");
							SmartLog.log("Product",
									"NumberName===" + o.getNumberName());
							GlobalValue.databaseUtility.insertCart(context, cartinfo);
							GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
									o.getId(), o.getIdTable());
						} else {
							for (int i = 0; i < arrCart.size(); i++) {
								o.setNumberName(arrCart.get(i).getNumberCart() + 1);
							}
							GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
									o.getId(), o.getIdTable());
						}

						for (int i = 0; i < arrCart.size(); i++) {
							if (arrCart.get(i).getIdTable()
									.equalsIgnoreCase(o.getIdTable())) {
								GlobalValue.databaseUtility.updateCart(context,
										o.getNumberName(), o.getId(),
										o.getIdTable());
							}
						}
					}

					// if (GlobalValue.arrcart.get(position).getNumberCart() <
					// 100) {
					// GlobalValue.arrcart.get(position).setNumberCart(
					// GlobalValue.arrcart.get(position)
					// .getNumberCart() + 1);
					//
					// }
					// // Log.e("","arrsizecart: "+)
					// holder.lblNumBerName.setText(" ("
					// + GlobalValue.arrcart.get(position).getNumberCart()
					// + ")");
					// // GlobalValue.numberCounts.get(position).setCount(
					// // GlobalValue.arrcart.get(position).getNumberCart());
					// Log.e("", "texttext "
					// + holder.lblNumBerName.getText().toString());
					notifyDataSetChanged();
				}
			});
			holder.btnRemoveProduct.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					arrCart = new ArrayList<CartInfo>();
					arrCart = GlobalValue.databaseUtility.getListCart1(context,
							o.getIdTable(), o.getId());
					for (int i = 0; i < arrCart.size(); i++) {
						if (arrCart.get(i).getNumberCart() > 0)
							o.setNumberName(arrCart.get(i).getNumberCart() - 1);
						GlobalValue.databaseUtility.updateCart(context, o.getNumberName(),
								o.getId(), o.getIdTable());
						if (arrCart.get(i).getNumberCart() == 1) {
							GlobalValue.databaseUtility.deleteCart(context, o.getName());
							o.setNumberName(0);
						}
					}
					// if (GlobalValue.arrcart.get(position).getNumberCart() >
					// 0) {
					// GlobalValue.arrcart.get(position).setNumberCart(
					// GlobalValue.arrcart.get(position)
					// .getNumberCart() - 1);
					// }
					// holder.lblNumBerName.setText(" ("
					// + GlobalValue.arrcart.get(position).getNumberCart()
					// + ")");
					// // CategoryActivity.numberCounts.get(position).setCount(
					// // GlobalValue.arrcart.get(position).getNumberCart());
					notifyDataSetChanged();
				}
			});
		} else {
			Log.i(TAG, "Null Object !");
		}
		return convertView;

	}
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (valueFilter == null) {

			valueFilter = new ValueFilter();
		}

		return valueFilter;
	}
	private class ValueFilter extends Filter {

		// Invoked in a worker thread to filter the data according to the
		// constraint.
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<ProductInfo> filterList = new ArrayList<ProductInfo>();
				for (int i = 0; i < productobj_filler.size(); i++) {
					if (productobj_filler.get(i).getName().toUpperCase()
							.contains(constraint.toString().toUpperCase())) {
						ProductInfo productInfo = new ProductInfo();
						// car.setCartype(carobj_filler.get(i).getCartype());
						// car.setCar_model(carobj_filler.get(i).getCar_model());
						// car.setCar_vendor(carobj_filler.get(i).getCar_vendor());
						// car.setC
						// car.setId(carobj_filler.get(i).getId());
						productInfo = productobj_filler.get(i);
						filterList.add(productInfo);
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = productobj_filler.size();
				results.values = productobj_filler;
			}
			return results;
		}

		// Invoked in the UI thread to publish the filtering results in the user
		// interface.
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listProduct = (ArrayList<ProductInfo>) results.values;
			notifyDataSetChanged();
		}

	}
}
