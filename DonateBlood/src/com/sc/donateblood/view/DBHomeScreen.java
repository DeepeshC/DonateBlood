/**
 * 
 */
package com.sc.donateblood.view;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sc.donateblood.R;
import com.sc.donateblood.navigationcontroller.DBNavigationConstants;
import com.sc.donateblood.navigationcontroller.DBNavigationController;

/**
 * @author Deepesh
 * 
 */
public class DBHomeScreen extends RoboFragmentActivity {
	/** Button for search blood group. **/
	@InjectView(R.id.home_search_bg)
	private Button searchBloodGroup;
	/** DBNavigationController. **/
	@Inject
	private DBNavigationController dbNavigationController;
	/** Button for register. **/
	@InjectView(R.id.home_new_user)
	private Button registerButton;
	/** Context. **/
	private Context context;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_home_screen);
		context = this;
		setOnClickListener();
	}

	/**
	 * Method for handle event.
	 */
	private void setOnClickListener() {
		searchBloodGroup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dbNavigationController.navigate(context,
						DBNavigationConstants.SEARCH_SCREEN, null);
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dbNavigationController.navigate(context,
						DBNavigationConstants.USER_REGISTER_SCREEN, null);
			}
		});
	}
}
