/**
 * 
 */
package com.sc.donateblood.utility;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.Button;

/**
 * @author Deepesh
 * 
 */
public class DBViewFactory {
	private static final float PERCENT_DARKENED = 0.8f;

	/**
	 * Creates a view with the Brand color and selector layer-list.
	 * 
	 * @param context
	 *            Used to create new View.
	 * @return
	 */
	public static Drawable createBrandedButtonDrawable() {

		int brandColor = DBUtilities.getButtonBackgroundColor();
		int r = (int) ((float) Color.red(brandColor) * PERCENT_DARKENED);
		int g = (int) ((float) Color.green(brandColor) * PERCENT_DARKENED);
		int b = (int) ((float) Color.blue(brandColor) * PERCENT_DARKENED);
		int activeColor = Color.rgb(r, g, b);

		ColorDrawable defaultButton = new ColorDrawable(brandColor);
		ColorDrawable pressedButton = new ColorDrawable(activeColor);

		int focused = android.R.attr.state_focused;
		int pressed = android.R.attr.state_pressed;

		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { pressed }, pressedButton);
		states.addState(new int[] { focused }, pressedButton);
		states.addState(new int[] {}, defaultButton);

		return states;
	}

	/**
	 * Creates a view with the given color and selector layer-list.
	 * 
	 * @param context
	 *            Used to create new View.
	 * @return
	 */
	public static Drawable createColoredButtonDrawable(int givenColor) {

		int r = (int) ((float) Color.red(givenColor) * PERCENT_DARKENED);
		int g = (int) ((float) Color.green(givenColor) * PERCENT_DARKENED);
		int b = (int) ((float) Color.blue(givenColor) * PERCENT_DARKENED);
		int activeColor = Color.rgb(r, g, b);

		ColorDrawable defaultButton = new ColorDrawable(givenColor);
		ColorDrawable pressedButton = new ColorDrawable(activeColor);

		int focused = android.R.attr.state_focused;
		int pressed = android.R.attr.state_pressed;

		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { pressed }, pressedButton);
		states.addState(new int[] { focused }, pressedButton);
		states.addState(new int[] {}, defaultButton);

		return states;
	}

	@Deprecated
	/**
	 * <i> WARNING - Not Yet Implemented - Throws UnsupportedOperationException </i>
	 * Creates a Button with the correct Brand color and selector layer-list, and text color for the given buttonTitle.
	 * 
	 * @param context 
	 * @param buttonTitle
	 * @return
	 */
	public static Button createBrandedView(String buttonTitle) {
		throw new UnsupportedOperationException();
	}
}
