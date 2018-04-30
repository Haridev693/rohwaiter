package com.shss.restaurantwaiter.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

/**
 * @File : AutoBgButton.java
 * @Author : Mr.Lemon
 * @Created : 30-05-2013
 * @Description : Button with background auto effect when select
 * 
 */

public class AutoBgButton extends android.support.v7.widget.AppCompatButton {

	public AutoBgButton(Context context) {
		super(context);
	}

	public AutoBgButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoBgButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("NewApi")
	@Override
	public void setBackground(Drawable background) {
		SAutoBgButtonBackgroundDrawable b = new SAutoBgButtonBackgroundDrawable(background);
		super.setBackground(b);
	}

	/**
	 * The stateful LayerDrawable used by this button.
	 */
	private class SAutoBgButtonBackgroundDrawable extends LayerDrawable {

		// The color filter to apply when the button is pressed
		protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
		// Alpha value when the button is disabled
		protected int _disabledAlpha = 100;

		public SAutoBgButtonBackgroundDrawable(Drawable d) {
			super(new Drawable[] { d });
		}

		@Override
		protected boolean onStateChange(int[] states) {
			boolean enabled = false;
			boolean pressed = false;

			for (int state : states) {
				if (state == android.R.attr.state_enabled)
					enabled = true;
				else if (state == android.R.attr.state_pressed)
					pressed = true;
			}

			mutate();
			if (enabled && pressed) {
				setColorFilter(_pressedFilter);
			} else if (!enabled) {
				setColorFilter(null);
				setAlpha(_disabledAlpha);
			} else {
				setColorFilter(null);
			}

			invalidateSelf();

			return super.onStateChange(states);
		}

		@Override
		public boolean isStateful() {
			return true;
		}
	}

}
