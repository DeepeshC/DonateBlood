/**
 * 
 */
package com.sc.donateblood.view;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.inject.Inject;
import com.parse.Parse;
import com.sc.donateblood.R;
import com.sc.donateblood.navigationcontroller.DBNavigationConstants;
import com.sc.donateblood.navigationcontroller.DBNavigationController;

/**
 * @author Deepesh
 * 
 */
public class DBSplashScreen extends RoboFragmentActivity {
	/** Navigation controller object. **/
	@Inject
	private DBNavigationController dbNavigationController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		initializeParse();
		startTimer();
	}

	/**
	 * Method for start a timer
	 */
	private void startTimer() {
		new CountDownTimer(1000, 400) {

			public void onFinish() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						toHomeScreen();
					}
				});
			}

			@Override
			public void onTick(long millisUntilFinished) {

			}
		}.start();

	}

	/***
	 * Method to go HomeScreen.
	 */
	private void toHomeScreen() {
		dbNavigationController.navigate(DBSplashScreen.this,
				DBNavigationConstants.HOME_SCREEN, null);
		finish();
	}

	/**
	 * Method for initialize the parse.
	 */
	private void initializeParse() {
		Parse.initialize(this, "kyaajNRT2eQmjWjotrSB4QLDOGuHlysvhbfpmdd5",
				"uv4Q0NlntFz9UpB8f4MG3MXqwh1yV2ILnaTqSq2c");
	}

}
