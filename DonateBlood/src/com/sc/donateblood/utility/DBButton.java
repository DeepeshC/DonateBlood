/**
 * 
 */
package com.sc.donateblood.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author Deepesh
 * 
 */
public class DBButton extends Button {
	/**
	 * Constructor for MigymButton.
	 * 
	 * @param context
	 *            Context
	 */
	public DBButton(Context context) {
		super(context);
		setBackgroundAndTextColors();
	}

	/**
	 * Constructor for MigymButton.
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            AttributeSet
	 * @param defStyle
	 *            Style
	 */
	public DBButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundAndTextColors();
	}

	/**
	 * Constructor for MigymButton.
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            AttributeSet
	 */
	public DBButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundAndTextColors();

	}

	/**
	 * This is the method for set the background color and text color of the
	 * button .
	 */
	private void setBackgroundAndTextColors() {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			setBackground(DBViewFactory.createBrandedButtonDrawable());
		} else {
			setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		}
		setTextColor(DBUtilities.getTextColor());
	}
}
