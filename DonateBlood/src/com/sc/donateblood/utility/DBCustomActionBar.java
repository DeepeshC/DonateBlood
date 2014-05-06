/**
 * 
 */
package com.sc.donateblood.utility;

import roboguice.inject.InjectView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sc.donateblood.R;

/**
 * @author Deepesh
 * 
 */
public class DBCustomActionBar extends RelativeLayout {

	// View v;

	@InjectView(R.id.text_actionbar_title)
	TextView tv;

	@Inject
	public DBCustomActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/* v = */layoutInflater.inflate(R.layout.robo_action_bar, this);

	}

	@Inject
	public DBCustomActionBar(Context context) {
		super(context);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/* v = */layoutInflater.inflate(R.layout.robo_action_bar, this);

	}

	// Cannot invoke those methods from MainMenuActivity
	public void setText(String text) {
		tv.setText(text);
	}

	public void setBackButtonVisible(boolean set) {
		// invoking this method in MainMenuActivity constructor will produce
		// funny error
		// if (!set) {
		// backButton.setVisibility(View.GONE);
		// } else {
		// backButton.setVisibility(View.VISIBLE);
		// }
	}
}
