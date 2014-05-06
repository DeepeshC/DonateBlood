/**
 * 
 */
package com.sc.donateblood.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sc.donateblood.R;
import com.sc.donateblood.adapter.DBGrouopSpinnerAdapter;
import com.sc.donateblood.adapter.DBSearchListAdapter;
import com.sc.donateblood.async.DBParserAsync;
import com.sc.donateblood.gps.services.DBGPSLocationServiceImpl;
import com.sc.donateblood.info.DBUserDetails;
import com.sc.donateblood.info.LocationData;
import com.sc.donateblood.navigationcontroller.DBNavigationConstants;
import com.sc.donateblood.navigationcontroller.DBNavigationController;
import com.sc.donateblood.utility.DBCheckConnectivity;
import com.sc.donateblood.utility.DBDialogUtils;
import com.sc.donateblood.utility.DBUtilities;

/**
 * @author Deepesh
 * 
 */
public class DBSearchScreen extends RoboFragmentActivity {
	/** Blood Group Spinner. **/
	@InjectView(R.id.bloodgroup_spinner)
	private Spinner bloodGroupSpinner;
	/** Location. **/
	@InjectView(R.id.search_location)
	private EditText locationText;
	/** Search List. **/
	@InjectView(R.id.search_list)
	public ListView searchList;
	/** Search button. **/
	@InjectView(R.id.search_button)
	private Button searchBtn;
	/** ArrayList<DBUserDetails>. **/
	private ArrayList<DBUserDetails> dbUserDetails;
	/** DBSearchListAdapter. **/
	private DBSearchListAdapter listAdapter;
	/** Selected blood group. **/
	private String selectedGroup;
	/** ProgressDialog. **/
	private ProgressDialog progressDialog;
	/** distanceText **/
	@InjectView(R.id.text_search_distance_search)
	private TextView distanceText;
	/** Action bar layout. **/
	@InjectView(R.id.action_bar_layout)
	private LinearLayout actionBarView;
	/** Title Text. ***/
	@InjectView(R.id.text_actionbar_title)
	private TextView actionBarTitle;
	/** GPSLocationServiceImpl. */
	private DBGPSLocationServiceImpl gpsLocationServiceImpl;
	/** latitude. **/
	private Double latitude = 0.0;
	/** longitude. **/
	private Double longitude = 0.0;
	/** DBNavigationController. **/
	@Inject
	private DBNavigationController dbNavigationController;
	/** Dialog. **/
	@Inject
	private DBDialogUtils dbDialogUtils;
	/** Connection. **/
	@Inject
	private DBCheckConnectivity connectivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_group);
		initViews();
		createBllodGroupSpinner();
		setOnClickListener();
	}

	/**
	 * Method for initialize the views.
	 */
	private void initViews() {
		actionBarView
				.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		actionBarTitle
				.setText(getResources().getString(R.string.search_search));
		actionBarTitle.setTextColor(DBUtilities.getTextColor());
		searchList.setVisibility(View.GONE);
	}

	/**
	 * Method for create the blood group spinner.
	 */
	private void createBllodGroupSpinner() {
		DBGrouopSpinnerAdapter dbGrouopSpinnerAdapter = new DBGrouopSpinnerAdapter(
				DBSearchScreen.this, android.R.layout.simple_spinner_item,
				DBUtilities.getBloodGroups());
		dbGrouopSpinnerAdapter.setInflater(getLayoutInflater());
		bloodGroupSpinner.setAdapter(dbGrouopSpinnerAdapter);
	}

	/**
	 * Method for check the internet connection.
	 * 
	 * @return
	 */
	private boolean checkInternet() {
		Boolean connected = connectivity.checkNow(DBSearchScreen.this);
		if (connected) {
			return true;
		} else {
			dbDialogUtils.createOkAlert(
					getResources().getString(R.string.no_network), "");
			return false;

		}

	}

	/**
	 * Method for handle events.
	 */
	private void setOnClickListener() {
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (checkInternet()) {
					createProgress();
					getUserLocation();
				}
			}
		});
		bloodGroupSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						selectedGroup = DBUtilities.getBloodGroups()[pos];
					}

					public void onNothingSelected(AdapterView<?> parent) {
						selectedGroup = "AB+";
					}
				});
	}

	/**
	 * Create the user details list.
	 * 
	 * @param nameList
	 *            List<ParseObject>
	 */
	private void createUserDetails(List<ParseObject> nameList) {
		new DBParserAsync(latitude, longitude, nameList, new Observer() {
			@SuppressWarnings("unchecked")
			@Override
			public void update(Observable observable, Object data) {
				dbUserDetails = (ArrayList<DBUserDetails>) data;
				addToAdapter();
				dismissDialog();
			}
		});

	}

	/**
	 * Add to the adapter class.
	 */
	private void addToAdapter() {
		if (dbUserDetails.size() > 0) {
			searchList.setVisibility(View.VISIBLE);
			DBUtilities.sortList(dbUserDetails);
			listAdapter = new DBSearchListAdapter(DBSearchScreen.this,
					dbUserDetails, DBSearchScreen.this);
			listAdapter.setInflater(getLayoutInflater());
			searchList.setAdapter(listAdapter);
			distanceText.setVisibility(View.VISIBLE);
		} else {
			searchList.setVisibility(View.GONE);
			distanceText.setVisibility(View.GONE);
			dbDialogUtils.createOkAlert("No results are available.", "Sorry!");
		}
	}

	/**
	 * get user current location.
	 */
	private void getUserLocation() {
		LocationData locationData = new LocationData();
		locationData.setContext(DBSearchScreen.this);
		gpsLocationServiceImpl = new DBGPSLocationServiceImpl(locationData);
		gpsLocationServiceImpl.getUserLocation(locationData, new Observer() {
			@Override
			public void update(Observable observable, Object data) {
				LocationData locationData = (LocationData) data;
				gpsLocationServiceImpl.unRegisterListeners();
				if (null != locationData) {
					latitude = locationData.getLatitude();
					longitude = locationData.getLongitude();
				} else {
					latitude = 0.0;
					longitude = 0.0;
				}
				gpsLocationServiceImpl.removeLocationUpdates();
				searchUserWithGroup(selectedGroup, locationText.getText()
						.toString().trim());

			}
		});
	}

	/**
	 * Method for search the users.
	 * 
	 * @param group
	 *            Blood Group
	 * @param location
	 *            Location
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void searchUserWithGroup(String group, String location) {
		ParseQuery query = new ParseQuery("UserDetails");
		query.whereEqualTo("BloodGroup", group);
		if (!TextUtils.isEmpty(location)) {
			query.whereEqualTo("Location", location);
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> nameList, ParseException e) {
				if (e == null) {
					createUserDetails(nameList);
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});

	}

	/**
	 * Method for create progress.
	 */
	private void createProgress() {
		progressDialog = new ProgressDialog(DBSearchScreen.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialogInterface, int i,
					KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					return true;
				} else {
					return false;
				}
			}
		});
		progressDialog.setMessage(getResources()
				.getString(R.string.please_wait));
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		if (!isFinishing()) {
			progressDialog.show();
		}
	}

	/**
	 * This is the method for dismiss the dialog.
	 */
	private void dismissDialog() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.setCancelable(true);
			progressDialog.dismiss();
		}
	}

	/**
	 * Method for start user details.
	 * 
	 * @param dbUserDetails
	 */
	public void startUserDetailsScreen(DBUserDetails dbUserDetails) {
		Map<String, Serializable> values = new HashMap<String, Serializable>();
		values.put("userDetails", dbUserDetails);
		dbNavigationController.navigate(DBSearchScreen.this,
				DBNavigationConstants.USER_DETAILS_SCREEN, values);
	}
}
