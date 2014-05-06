/**
 * 
 */
package com.sc.donateblood.view;

import java.util.HashMap;
import java.util.Map;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sc.donateblood.R;
import com.sc.donateblood.info.DBUserDetails;
import com.sc.donateblood.utility.DBConstants;
import com.sc.donateblood.utility.DBUtilities;

/**
 * @author Deepesh
 * 
 */
public class DBUserDetailsScreen extends RoboFragmentActivity {
	/** User name. ***/
	@InjectView(R.id.text_user_name)
	private TextView nameView;
	/** User phone. ***/
	@InjectView(R.id.text_user_phone)
	private TextView phoneView;
	/** User sex. ***/
	@InjectView(R.id.text_user_sex)
	private TextView sexView;
	/** User image. ***/
	@InjectView(R.id.image_user)
	private ImageView userImage;
	/*** DBUserDetails. */
	private DBUserDetails dbUserDetails;
	/** WebView to display V3 map... */
	@InjectView(R.id.webView_user_location)
	private WebView locationMapView;
	/** Call button. **/
	@InjectView(R.id.button_call)
	private Button callButton;
	/** Email button. **/
	@InjectView(R.id.button_email)
	private Button emailBtn;
	/** Context. **/
	private Context instance;
	/** Action bar layout. **/
	@InjectView(R.id.action_bar_layout)
	private LinearLayout actionBarView;
	/** Title Text. ***/
	@InjectView(R.id.text_actionbar_title)
	private TextView actionBarTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_deatils);
		instance = this;
		initExtras();
		initViews();
		loadMap();
		setOnClickListener();
	}

	/**
	 * Method for initialize views.
	 */
	private void initViews() {
		actionBarView
				.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		actionBarTitle.setText(getResources().getString(
				R.string.user_details_title));
		actionBarTitle.setTextColor(DBUtilities.getTextColor());
		nameView.setText(dbUserDetails.name);
		phoneView.setText(dbUserDetails.contactNumber);
		sexView.setText("Male");
		if (null != dbUserDetails.userImage) {
			userImage.setImageBitmap(DBUtilities
					.getBitmapFromParse(dbUserDetails.userImage));
		}

	}

	/**
	 * Method for handle the events.
	 */
	private void setOnClickListener() {
		callButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				makeCalltoNumber();
			}
		});
		emailBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				eMail();
			}
		});
	}

	/** To get data from bundle given by calling activity. */
	private void initExtras() {
		if (getIntent() != null) {
			Bundle bundle = getIntent().getExtras();
			if (bundle != null && bundle.containsKey("userDetails")) {
				dbUserDetails = (DBUserDetails) bundle.get("userDetails");
			}
		}
	}

	/**
	 * Method for loading map.
	 */
	private void loadMap() {
		locationMapView.clearView();
		locationMapView.clearHistory();
		locationMapView.loadUrl(DBConstants.MAPURL);
		locationMapView.getSettings().setJavaScriptEnabled(true);
		locationMapView.getSettings().setAppCacheMaxSize(1);
		locationMapView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View parentView, MotionEvent parentEvent) {
				parentView.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		locationMapView.addJavascriptInterface(new MapWebAppInterface(
				DBUserDetailsScreen.this, dbUserDetails), "GoogleMapview");
		locationMapView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,
					String urlNewString) {
				Map<String, String> noCacheHeaders = new HashMap<String, String>(
						2);
				noCacheHeaders.put("Pragma", "no-cache");
				noCacheHeaders.put("Cache-Control", "no-cache");
				view.loadUrl(urlNewString, noCacheHeaders);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				findViewById(R.id.contact_map_view_progress).setVisibility(
						View.VISIBLE);

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				view.clearCache(true);
				findViewById(R.id.contact_map_view_progress).setVisibility(
						View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
	}

	/**
	 * This is the method for send email.
	 * 
	 * @param gymName
	 *            gym Name
	 * @param feedback_address
	 *            feedback address
	 * @param message
	 *            message
	 */
	private void eMail() {
		if (DBUtilities.checkInternet(instance)) {
			try {
				DBUtilities.sendMail(instance, "", dbUserDetails.emailId, "",
						"", 10);
			} catch (Exception e) {
				Log.d("Exception", "" + e);
			}
		}

	}

	/**
	 * This is the method for make call to the phone number.
	 * 
	 * @param phoneNumber
	 *            phone Number
	 * @param phone
	 *            TextView
	 */
	private void makeCalltoNumber() {
		try {
			String url = "tel:" + dbUserDetails.contactNumber;
			DBUtilities.startIntent(instance, url, Intent.ACTION_CALL);
		} catch (Exception e) {
			Log.d("Exception makeCalltoNumber ContactUs", "" + e);
		}

	}

	/**
	 * Class for web view interface.
	 * 
	 * @author deepesh.c
	 * 
	 */
	public class MapWebAppInterface {
		/** DBUserDetails view map. **/
		private DBUserDetails dbUserDetails;

		/**
		 * Constructor for MapWebAppInterface.
		 */
		public MapWebAppInterface(Context context, DBUserDetails dbUserDetails) {
			this.dbUserDetails = dbUserDetails;
		}

		/**
		 * This is the method for get latitude.
		 * 
		 * @return latitude
		 */
		public String getLatitude() {
			return String.valueOf(dbUserDetails.latitude);
		}

		/**
		 * This is the method for get longitude.
		 * 
		 * @return longitude
		 */
		public String getLongitude() {
			return String.valueOf(dbUserDetails.longitude);
		}

		/**
		 * This is the method for get short name.
		 * 
		 * @return short name
		 */
		public String getShortName() {
			return dbUserDetails.name;
		}
	}
}
