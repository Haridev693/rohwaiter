package com.shss.restaurantwaiter.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.adapter.ProductAdapter;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.json.util.ParserUtility;
import com.shss.restaurantwaiter.modelmanager.ErrorNetworkHandler;
import com.shss.restaurantwaiter.modelmanager.ModelManager;
import com.shss.restaurantwaiter.modelmanager.ModelManagerListener;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.object.ProductInfo;
import com.shss.restaurantwaiter.utility.SmartLog;
import com.shss.restaurantwaiter.widget.AutoBgButton;

public class ProductActivity extends BaseActivity implements OnClickListener {
    private AutoBgButton btnBack, btnCart, btnCustomerAccount;
    private EditText txtSearchKeyword;
    private Button btnSearhNormal, btnDelSearch;
    private TextView lblHeaderTitle;
    private ListView lsvProduct;
    private String nameCategory, imgCategory, IDTable, Status;
    private ArrayList<ProductInfo> listProduct;
    private ProductAdapter productAdapter;
    private static String CategoryId;
    public static ProductActivity self = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_product);
        self = ProductActivity.this;
        if (getIntent().hasExtra("IDTable")) {
            IDTable = getIntent().getStringExtra("IDTable");
        }
        if (getIntent().hasExtra("NameCategory")) {
            nameCategory = getIntent().getStringExtra("NameCategory");
        }
        if (getIntent().hasExtra("ImageCategory")) {
            imgCategory = getIntent().getStringExtra("ImageCategory");
        }
        if (getIntent().hasExtra("Status")) {
            Status = getIntent().getStringExtra("Status");
        }
        if (getIntent().hasExtra("CATEGORYID")) {
            CategoryId = getIntent().getStringExtra("CATEGORYID");
        }
        initUI();
        initControl();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

    private void initUI() {
        btnBack = (AutoBgButton) findViewById(R.id.btnBack);
        btnCart = (AutoBgButton) findViewById(R.id.btnCart);
        lblHeaderTitle = (TextView) findViewById(R.id.lblHeaderTitle);
        btnCustomerAccount = (AutoBgButton) findViewById(R.id.btnCustomerAccount);
        txtSearchKeyword = (EditText) findViewById(R.id.txtSearchKeyword);
        txtSearchKeyword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                productAdapter.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSearhNormal = (Button) findViewById(R.id.btnSearhNormal);
        btnDelSearch = (Button) findViewById(R.id.btn_del_search);
        lsvProduct = (ListView) findViewById(R.id.lsvProduct);

    }

    private void initData() {
        lblHeaderTitle.setText(getString(R.string.table) + " " + IDTable);
        ModelManager.getProductbyCategoryId(self, CategoryId, true,
                new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object json) {
                        listProduct = ParserUtility
                                .parseProductbyCategoryId(json.toString());
                        for (ProductInfo item : listProduct) {
                            item.setImgCategory(imgCategory);
                            item.setIdTable(IDTable);

                        }

                        for (int i = 0; i < listProduct.size(); i++) {
                            CartInfo info = new CartInfo();
                            info.setTableId(IDTable);
                            info.setProductId(listProduct.get(i).getId());
                            info.setNameCart(listProduct.get(i).getName());
                            info.setPrice(listProduct.get(i).getPrice());
                            info.setNumberCart(listProduct.get(i)
                                    .getNumberName());
                        }

                        productAdapter = new ProductAdapter(
                                ProductActivity.this, listProduct);
                        Log.e("", "listproductsize " + listProduct.size());
                        lsvProduct.setAdapter(productAdapter);

                    }

                    @Override
                    public void onError(VolleyError error) {
                        ErrorNetworkHandler.processError(self, error);
                    }
                });

    }

    private void initControl() {
        btnCustomerAccount.setOnClickListener(this);
        btnSearhNormal.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        btnDelSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCustomerAccount:
                onClickCustomerAccount();
                break;

            case R.id.btnSearhNormal:
                onClickSearhNormal();
                break;
            case R.id.btnBack:
                onClickBack();
                break;

            case R.id.btnCart:
                onClickCart();
                break;

            case R.id.btn_del_search:
                onClickDel();
                break;
        }
    }

    private void onClickBack() {

        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void onClickCart() {
        Bundle b = new Bundle();
        b.putString("IDTable", IDTable);
        b.putString("Status", Status);
        gotoActivity(ProductActivity.this, CartActivity.class, b);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void onClickDel() {
        txtSearchKeyword.setText("");
        productAdapter = new ProductAdapter(ProductActivity.this, listProduct);
        productAdapter.notifyDataSetChanged();
        lsvProduct.setAdapter(productAdapter);
    }

    private void onClickCustomerAccount() {
        Bundle b = new Bundle();
        b.putString("IDTable", IDTable);
        SmartLog.log(b.toString(), "order");
        gotoActivity(ProductActivity.this, PaymentActivity.class, b);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void onClickSearhNormal() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        ArrayList<ProductInfo> arrSearch = new ArrayList<ProductInfo>();
        listProduct = GlobalValue.databaseUtility.getListProduct(ProductActivity.this,
                nameCategory);
        for (int i = 0; i < listProduct.size(); i++) {
            ProductInfo itemInfo = listProduct.get(i);
            String title = itemInfo.getName().toLowerCase().trim();
            String titleSearch = txtSearchKeyword.getText().toString()
                    .toLowerCase().trim();
            itemInfo.setIdTable(IDTable);
            itemInfo.setImgCategory(imgCategory);
            if (title.contains(titleSearch)) {
                arrSearch.add(itemInfo);
                productAdapter = new ProductAdapter(ProductActivity.this,
                        arrSearch);
                lsvProduct.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }
        }

    }

}
