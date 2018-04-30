package com.shss.restaurantwaiter.activity.tabhelp;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ViewAnimator;

/**
 * The purpose of this Activity is to manage the activities in a tab. Note:
 * Child Activities can handle Key Presses before they are seen here.
 * 
 * @author Eric Harlow
 */
public class TabGroupActivity extends ActivityGroup {

	public Stack<String> stack;
	private ViewAnimator mViewAnimator;

	private String TAG = getClass().getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (stack == null)
			stack = new Stack<String>();

		mViewAnimator = new ViewAnimator(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		System.gc();
		// try {
		// if (!GlobalValue.isShowMap)
		// resetChildActivities();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public void finishFromChild(Activity child) {
		// try {
		// pop();
		// } catch (Exception e) {
		// e.printStackTrace();
		// // TODO Auto-generated catch block
		// finish();
		// }

		if (stack.size() > 1) {
			getLocalActivityManager().destroyActivity(stack.pop(), true);

			Intent lastIntent = getLocalActivityManager().getActivity(
					stack.peek()).getIntent();
			Window newWindow = getLocalActivityManager().startActivity(
					stack.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
		} else {
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// finish();
		}
	}

	public void push(String id, Intent intent) {
		// Window window = getLocalActivityManager().startActivity(id, intent);
		Window window = getLocalActivityManager().startActivity(id,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

		if (window != null) {
			stack.push(id);
			// animation
			// View view = window.getDecorView();
			// mViewAnimator.setInAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.slide_in_left));
			// mViewAnimator.setOutAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.slide_out_left));
			// mViewAnimator.addView(view);
			// mViewAnimator.showNext();
			// setContentView(mViewAnimator);
			// end animation
			setContentView(window.getDecorView());
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
		}
		return true;
	}

	public void resetChildActivities() {

		LocalActivityManager manager = getLocalActivityManager();
		if (stack.size() != 1) {
			int index = stack.size() - 1;

			for (int i = index; i > 0; i--) {
				manager.destroyActivity(stack.get(i), true);
				stack.remove(i);
			}

			Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
			Window newWindow = manager.startActivity(stack.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
			System.gc();
		}
	}

	DialogInterface.OnClickListener onOKClick = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();

		}
	};

}
