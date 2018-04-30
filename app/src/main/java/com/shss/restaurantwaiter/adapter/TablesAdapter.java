package com.shss.restaurantwaiter.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.TablesInfo;
import com.shss.restaurantwaiter.utility.SmartLog;
import com.shss.restaurantwaiter.utility.lazylist.ImageLoader;

public class TablesAdapter extends BaseAdapter {
    private Activity mContext;
    private ArrayList<TablesInfo> listTableInfo;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public TablesAdapter(Activity activity, ArrayList<TablesInfo> d) {
        mContext = activity;
        listTableInfo = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listTableInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item_gridview_table,
                    null);
            holder.imgRowTable = (ImageView) convertView
                    .findViewById(R.id.imgRowTable);
            holder.lnrTables = (RelativeLayout) convertView
                    .findViewById(R.id.lnrTables);
            holder.lblNumber = (TextView) convertView
                    .findViewById(R.id.lblNumber);
            convertView.setTag(holder);
        }
        TablesInfo o = listTableInfo.get(position);

        if (o != null) {
            holder = (ViewHolder) convertView.getTag();
            SmartLog.log("", "Id Tables" + o.getTablesId());
            holder.lblNumber.setText(o.getTablesId());
            if (o.getStatus() == TablesInfo.TABLE_FREE) {
                holder.imgRowTable
                        .setBackgroundResource(R.drawable.btn_eating1_1);
                holder.lnrTables.setBackgroundResource(R.drawable.bg_eating);
                holder.lblNumber.setTextColor(Color.BLACK);
            } else if (o.getStatus() == TablesInfo.TABLE_USING) {
                holder.imgRowTable
                        .setBackgroundResource(R.drawable.btn_operador_1);
                holder.lnrTables
                        .setBackgroundResource(R.drawable.bg_operador_1);
                holder.lblNumber.setTextColor(Color.RED);
            } else {
                holder.imgRowTable
                        .setBackgroundResource(R.drawable.btn_eating1_1);
                holder.lnrTables.setBackgroundResource(R.drawable.bg_eating);
                holder.lblNumber.setTextColor(Color.BLACK);
            }

        }
        return convertView;
    }

    static class ViewHolder {
        private ImageView imgRowTable;
        private RelativeLayout lnrTables;
        private TextView lblNumber;
    }

}
