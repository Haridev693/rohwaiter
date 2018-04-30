package com.shss.restaurantwaiter.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.database.DatabaseUtility;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.utility.StringUtility;
import com.shss.restaurantwaiter.widget.AutoBgButton;

public class ComplementsProductActivity extends BaseActivity implements
		OnClickListener {
	private AutoBgButton btnCustomerAccount;
	private EditText txtNote;
	private TextView lblNote;
	private String IdTable, namecart, IdProduct;
	private Intent inten;
	private Bundle bundle;
	private ArrayList<CartInfo> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_complements_product);
		inten = getIntent();
		bundle = inten.getBundleExtra("mybundle");
		namecart = bundle.getString("NameCart");
		IdTable = bundle.getString("IdTable");
		IdProduct = bundle.getString("IdProduct");
		initUI();
		initControl();
		initData();
	}

	private void initUI() {
		initHeaderUI();
		hideHeaderButton(false, true);
		setTitleHeader(namecart);
		btnCustomerAccount = (AutoBgButton) findViewById(R.id.btnCustomerAccount);
		txtNote = (EditText) findViewById(R.id.txtNote);
		lblNote = (TextView) findViewById(R.id.lblNote);
	}

	private void initControl() {
		btnCustomerAccount.setOnClickListener(this);
		lblNote.setOnClickListener(this);
	}

	private void initData() {
		list = new ArrayList<CartInfo>();
		list = GlobalValue.databaseUtility.getCart(ComplementsProductActivity.this,
				IdTable, namecart, IdProduct);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNote().equals("1")) {
				lblNote.setText("");
			} else {
				lblNote.setText(list.get(i).getNote());
			}

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCustomerAccount:
			onClickCustomerAccount();
			break;

		}
	}

	private void onClickCustomerAccount() {
		if (StringUtility.isEmpty(txtNote)) {
			Toast.makeText(ComplementsProductActivity.this,
					"You have not entered note", Toast.LENGTH_LONG).show();
		} else {
			list = new ArrayList<CartInfo>();
			list = GlobalValue.databaseUtility.getCart(ComplementsProductActivity.this,
					IdTable, namecart, IdProduct);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getNote().equals("1")) {
					GlobalValue.databaseUtility.updateCartNote(
							ComplementsProductActivity.this, txtNote.getText()
									.toString(), IdTable, namecart, IdProduct);
					txtNote.setText("");
				} else {
					String note = list.get(i).getNote();
					GlobalValue.databaseUtility.updateCartNote(
							ComplementsProductActivity.this, note + ", "
									+ txtNote.getText().toString(), IdTable,
							namecart, IdProduct);
					txtNote.setText("");
				}
			}

			Toast.makeText(ComplementsProductActivity.this, "Successfully",
					Toast.LENGTH_LONG).show();
			onBackPressed();
		}

	}
}
