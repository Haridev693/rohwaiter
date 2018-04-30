package com.shss.restaurantwaiter.object;


public interface PickerListener {
	public void simplePicker(String simplePicker, String id);

	public void dateTimePicker(int year, int month, String monthName, int day, String dayOfWeek,
			int hour, int min);
}
