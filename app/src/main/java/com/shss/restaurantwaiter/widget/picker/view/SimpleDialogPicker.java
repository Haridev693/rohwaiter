package com.shss.restaurantwaiter.widget.picker.view;

/**
 * @author Plum
 * 
 * Picker used for Type, Sex and Married (single wheel)
 */
import java.util.ArrayList;
import java.util.Vector;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.PickerListener;
import com.shss.restaurantwaiter.object.UserInfo;
import com.shss.restaurantwaiter.widget.picker.adapter.SimpleAdapter;

public class SimpleDialogPicker extends Dialog implements
		android.view.View.OnClickListener {

	private Button btnSetTime, btnCancel;
	private Vector<String> data = new Vector<String>();
	private Context context;
	private WheelView wheel;
	private String oldString = "";
	private String oldIndex = "0";
	private ArrayList<UserInfo> arrUser;
	private PickerListener pickerListener;

	public SimpleDialogPicker(Context context, ArrayList<UserInfo> arrUser,
			String oldString, String oldIndex, PickerListener pickerListener) {
		super(context);
		this.context = context;
		this.oldString = oldString;
		this.oldIndex = oldIndex;
		this.pickerListener = pickerListener;
		this.arrUser = arrUser;
		Log.e("aaaaa", arrUser.size()+"");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picker_simple_wheel);

		getWindow().setBackgroundDrawableResource(R.color.transparent);

		initUI();
		initData();
	}

	private void initUI() {
		wheel = (WheelView) findViewById(R.id.wheel);

		btnSetTime = (Button) findViewById(R.id.btn_set);
		btnSetTime.setOnClickListener(this);

		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(this);
	}

	public int getCurrentPosInit() {
		for (int i = 0; i < data.size(); i++) {
			if (data.elementAt(i).equals(oldString))
				return i;
		}
		return 0;
	}

	private void initData() {
		// for sex picker
		data.clear();
		// arrUser = GlobalValue.arrUser;
		for (int i = 0; i < arrUser.size(); i++) {
			String user = arrUser.get(i).getUserName();
			data.add(user);
		}

		wheel.setViewAdapter(new SimpleAdapter(context, data, oldString));
		wheel.setCurrentItem(getCurrentPosInit());
	}

	public void onClickSetTime() {
		pickerListener.simplePicker(data.elementAt(wheel.getCurrentItem()),
				(wheel.getCurrentItem() + 1) + "");
		dismiss();
	}

	@Override
	public void onClick(View v) {
		if (v == btnCancel) {
			dismiss();
		} else if (v == btnSetTime) {
			onClickSetTime();
		}
	}
}