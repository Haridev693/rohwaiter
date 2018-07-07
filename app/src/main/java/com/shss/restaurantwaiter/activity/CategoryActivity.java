package com.shss.restaurantwaiter.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.adapter.CategoryAdapter;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.json.util.ParserUtility;
import com.shss.restaurantwaiter.modelmanager.ErrorNetworkHandler;
import com.shss.restaurantwaiter.modelmanager.ModelManager;
import com.shss.restaurantwaiter.modelmanager.ModelManagerListener;
import com.shss.restaurantwaiter.object.CategoryInfo;
import com.shss.restaurantwaiter.utility.SmartLog;
import com.shss.restaurantwaiter.widget.AutoBgButton;

import java.util.ArrayList;

public class CategoryActivity extends BaseActivity implements OnClickListener {
    private AutoBgButton btnBack, btnCart, btnCustomerAccount;
    private ListView lsvCategory;
    private TextView lblHeaderTitle;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryInfo> listCategory;
    private String IDTable, Status;
    public static CategoryActivity self = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_category);
        self = CategoryActivity.this;
        if (getIntent().hasExtra("IDTable")) {
            IDTable = getIntent().getStringExtra("IDTable");
        }
        if (getIntent().hasExtra("Status")) {
            Status = getIntent().getStringExtra("Status");
        }
        initUI();
        initData();
        initControl();

    }

    private void initUI() {
        btnBack = (AutoBgButton) findViewById(R.id.btnBack);
        btnCart = (AutoBgButton) findViewById(R.id.btnCart);
        lblHeaderTitle = (TextView) findViewById(R.id.lblHeaderTitle);
        btnCustomerAccount = (AutoBgButton) findViewById(R.id.btnCustomerAccount);
        lsvCategory = (ListView) findViewById(R.id.lsvCategory);

    }

    private void initControl() {
        btnCustomerAccount.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCart.setOnClickListener(this);
    }

    private void initData() {

        lblHeaderTitle.setText(getString(R.string.table) + " " + IDTable);
        ModelManager.getAllCategory(self, true, new ModelManagerListener() {

            @Override
            public void onSuccess(Object json) {
                listCategory = ParserUtility.parseAllCategory(json.toString());
                categoryAdapter = new CategoryAdapter(CategoryActivity.this,
                        listCategory);
                lsvCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(VolleyError error) {
                ErrorNetworkHandler.processError(CategoryActivity.this, error);
            }
        });

        lsvCategory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle b = new Bundle();
                b.putString("NameCategory", listCategory.get(arg2).getName());
                b.putString("ImageCategory", listCategory.get(arg2).getImgUrl());
                b.putString("CATEGORYID", listCategory.get(arg2).getId());
                b.putString("IDTable", IDTable);
                b.putString("Status", Status);
                gotoActivity(CategoryActivity.this, ProductActivity.class, b);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            GlobalValue.databaseUtility.deleteWholeCarTable(CategoryActivity.this);
            onBackPressed();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCustomerAccount:
                onClickCustomerAccount();
                break;
            case R.id.btnBack:
                GlobalValue.databaseUtility.deleteWholeCarTable(CategoryActivity.this);
                onBackPressed();
                break;

            case R.id.btnCart:
                onClickCart();
                break;

        }
    }

    private void onClickCart() {
        Bundle b = new Bundle();
        b.putString("IDTable", IDTable);
        b.putString("Status", Status);
        gotoActivity(CategoryActivity.this, CartActivity.class, b);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void onClickCustomerAccount() {
        Bundle b = new Bundle();
        b.putString("IDTable", IDTable);
        SmartLog.log(b.toString(), "order");
        gotoActivity(CategoryActivity.this, PaymentActivity.class, b);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
