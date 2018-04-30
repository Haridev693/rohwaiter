package com.shss.restaurantwaiter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.adapter.TablesAdapter;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.json.util.ParserUtility;
import com.shss.restaurantwaiter.modelmanager.ErrorNetworkHandler;
import com.shss.restaurantwaiter.modelmanager.ModelManager;
import com.shss.restaurantwaiter.modelmanager.ModelManagerListener;
import com.shss.restaurantwaiter.object.TablesInfo;
import com.shss.restaurantwaiter.widget.AutoBgButton;

import java.util.ArrayList;

public class TableActivity extends BaseActivity implements OnClickListener {
    private TextView lblTitleCart;
    private AutoBgButton btnBackCart, btnRefresh;
    private GridView grvTable;
    private TablesAdapter tablesAdapter;
    public static ArrayList<TablesInfo> listTables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_table);

        Log.e("TAble ---> ", "User ID " + GlobalValue.preferences.getUserID());
        initUI();
        initControl();
        initGridViewTable();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initUI() {
        grvTable = (GridView) findViewById(R.id.grvTable);
        lblTitleCart = (TextView) findViewById(R.id.lblTitleCart);
        btnBackCart = (AutoBgButton) findViewById(R.id.btnBackCart);
        btnRefresh = (AutoBgButton) findViewById(R.id.btnRefresh);
    }

    private void initControl() {
        lblTitleCart.setText(R.string.tables);
        btnBackCart.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
    }

    private void initGridViewTable() {
        listTables = new ArrayList<>();
        tablesAdapter = new TablesAdapter(self, listTables);
        grvTable.setAdapter(tablesAdapter);
        grvTable.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle b = new Bundle();
                b.putString("IDTable", listTables.get(arg2).getTablesId());
                b.putString("Status",
                        String.valueOf(listTables.get(arg2).getStatus()));
                gotoActivity(TableActivity.this, CategoryActivity.class, b);
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
                GlobalValue.preferences.setTableClick(listTables.get(arg2).getTablesId());

            }

        });
    }

    private void initData() {
        ModelManager.getAllTable(self, true, new ModelManagerListener() {

            @Override
            public void onSuccess(Object json) {
                listTables.clear();
                listTables.addAll(ParserUtility.parseAllTable(json.toString()));
                tablesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                ErrorNetworkHandler.processError(self, error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackCart:
                onBackPressed();
                break;
            case R.id.btnRefresh:
                initData();
                break;
            default:
                break;
        }
    }
}
