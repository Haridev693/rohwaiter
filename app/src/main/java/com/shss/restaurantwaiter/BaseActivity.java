package com.shss.restaurantwaiter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shss.restaurantwaiter.activity.PaymentActivity;
import com.shss.restaurantwaiter.gui.LemonProgressDialog;
import com.shss.restaurantwaiter.activity.CartActivity;
import com.shss.restaurantwaiter.activity.tabhelp.TabGroupActivity;
import com.shss.restaurantwaiter.widget.AutoBgButton;

public class BaseActivity extends Activity {
	public static String TAG;
	private AutoBgButton btnBack, btnCart;
	private TextView lblHeaderTitle;

	protected ProgressDialog progressDialog;
	protected LemonProgressDialog mProgressDialog;
	protected BaseActivity self;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self = this;
		TAG = self.getClass().getSimpleName();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	protected void initHeaderUI() {
		btnBack = (AutoBgButton) findViewById(R.id.btnBack);
		btnCart = (AutoBgButton) findViewById(R.id.btnCart);
		lblHeaderTitle = (TextView) findViewById(R.id.lblHeaderTitle);

		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_right);
			}
		});

		btnCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoActivity(BaseActivity.this, CartActivity.class);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_right);
			}
		});
	}

	protected void setHeaderTitle(int stringId) {
		lblHeaderTitle.setText(stringId);
	}

	protected void setHeaderTitle(String string) {
		lblHeaderTitle.setText(string);
	}

	protected void hideHeaderButton(boolean isLeft, boolean isRight) {
		btnBack.setVisibility(isLeft ? View.GONE : View.VISIBLE);
		btnCart.setVisibility(isRight ? View.GONE : View.VISIBLE);
	}

	protected void setTitleHeader(int titleId) {
		if (lblHeaderTitle != null) {
			lblHeaderTitle.setText(titleId);
			lblHeaderTitle.setSelected(true);
		}
	}

	protected void setTitleHeader(String title) {
		if (lblHeaderTitle != null) {
			lblHeaderTitle.setText(title);
			lblHeaderTitle.setSelected(true);
		}
	}

	public void gotoActivityInGroup(Context context, Class<?> cla) {
		Intent previewMessage = new Intent(getParent(), cla);
		TabGroupActivity parentActivity = (TabGroupActivity) getParent();
		parentActivity.push(cla.getSimpleName(), previewMessage);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}

	public void gotoActivityInGroup(Context context, Class<?> cla, String key,
			CharSequence value) {
		Intent previewMessage = new Intent(getParent(), cla);
		previewMessage.putExtra(key, value);
		TabGroupActivity parentActivity = (TabGroupActivity) getParent();
		parentActivity.push(cla.getSimpleName(), previewMessage);
	}

	public void gotoActivityInGroup(Context context, Class<?> cla,
			String[] key, CharSequence[] value) {
		Intent previewMessage = new Intent(getParent(), cla);
		for (int i = 0; i < key.length; i++) {
			previewMessage.putExtra(key[i], value[i]);
		}
		TabGroupActivity parentActivity = (TabGroupActivity) getParent();
		parentActivity.push(cla.getSimpleName(), previewMessage);
	}

	public void gotoActivityInGroup(Context context, Class<?> cla, Bundle b) {
		Intent previewMessage = new Intent(getParent(), cla);
		previewMessage.putExtras(b);
		TabGroupActivity parentActivity = (TabGroupActivity) getParent();
		parentActivity.push(cla.getSimpleName(), previewMessage);
	}

	public void startActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void showToast(int idString) {
		Toast.makeText(this, idString, Toast.LENGTH_SHORT).show();
	}

	public void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

	public void gotoActivity(Context context, Class<?> cla) {
		Intent intent = new Intent(context, cla);
		startActivity(intent);
	}

	public void gotoActivity(Context context, Class<?> cla, Bundle bundle) {
		Intent intent = new Intent(context, cla);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	protected void showDialog(int messageId, int positiveLabelId,
			DialogInterface.OnClickListener positiveOnClick) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(messageId));
		builder.setPositiveButton(getString(positiveLabelId), positiveOnClick);
		AlertDialog alert = builder.create();
		alert.show();
	}

	// ======================= PROGRESS DIALOG ======================

	/**
	 * Open progress dialog
	 * 
	 * @param text
	 */
	public void showProgressDialog(String text) {
		if (progressDialog == null) {
			try {
				progressDialog = ProgressDialog.show(this.getParent(),
						getString(R.string.app_name), text, true);
				progressDialog.setCancelable(false);
			} catch (Exception e) {
				progressDialog = ProgressDialog.show(this,
						getString(R.string.app_name), text, true);
				progressDialog.setCancelable(false);
			}
		}
	}

	/**
	 * Open progress dialog
	 */
	public void showProgressDialog() {
		try {
			// showProgressDialog(getString(R.string.message_please_wait));
			if (mProgressDialog == null) {
				try {
					mProgressDialog = new LemonProgressDialog(self);
					mProgressDialog.show();
					mProgressDialog.setCancelable(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					mProgressDialog = new LemonProgressDialog(self.getParent());
					mProgressDialog.show();
					mProgressDialog.setCancelable(false);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Close progress dialog
	 */
	public void closeProgressDialog() {
		try {
			// if (progressDialog != null) {
			//
			// // progressDialog.cancel();
			// progressDialog.dismiss();
			// progressDialog = null;
			// }
			if (mProgressDialog != null) {
				mProgressDialog.cancel();
				mProgressDialog.dismiss();
				mProgressDialog = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
