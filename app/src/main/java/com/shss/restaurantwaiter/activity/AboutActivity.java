
package com.shss.restaurantwaiter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.widget.AutoBgButton;

public class AboutActivity extends BaseActivity implements OnClickListener {
	private TextView llwep;
	private AutoBgButton btnBack1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_about);
		initUI();
//		initControl();
	}
	private void initUI() {
		// TODO Auto-generated method stub
		llwep = (TextView) findViewById(R.id.lblWebsite);
		llwep.setOnClickListener(this);
		btnBack1 = (AutoBgButton) findViewById(R.id.btnBack1);
		btnBack1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==llwep){
			   String url = getString(R.string.about_link);
			   if (!url.contains("http"))
			    url = "http://" + url;
			   Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			    startActivity(i);
		}
	}
	
}