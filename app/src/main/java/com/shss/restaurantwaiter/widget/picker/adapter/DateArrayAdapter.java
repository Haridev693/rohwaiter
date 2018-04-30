package com.shss.restaurantwaiter.widget.picker.adapter;

import java.util.Vector;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Adapter for string based wheel. Highlights the current value.
 */
public class DateArrayAdapter extends ArrayWheelAdapter<String> {
	// Index of current item
	int currentItem;
	// Index of item to be highlighted
	int currentValue;

	/**
	 * Constructor
	 */
	public DateArrayAdapter(Context context, Vector<String> items, int current) {
		super(context, items);
		this.currentValue = current;
		setTextSize(17);
	}

	@Override
	protected void configureTextView(TextView view) {
		super.configureTextView(view);
		if (currentItem == currentValue) {
			view.setTextColor(0xFF0000F0);
		}
		view.setTypeface(Typeface.SANS_SERIF);
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		currentItem = index;
		return super.getItem(index, cachedView, parent);
	}
}