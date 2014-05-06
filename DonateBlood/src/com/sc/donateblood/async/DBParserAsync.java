/**
 * 
 */
package com.sc.donateblood.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.text.TextUtils;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.sc.donateblood.info.DBUserDetails;
import com.sc.donateblood.utility.DBUtilities;

/**
 * @author Deepesh
 * 
 */
public class DBParserAsync extends Observable {
	/*** Observer. */
	private Observer observer;
	private ArrayList<DBUserDetails> dbUserDetails;
	private Double latitude;
	private Double longitude;

	/**
	 * Constructor for DatbaseReadService.
	 */
	public DBParserAsync(Double latitude, Double longitude,
			List<ParseObject> nameList, Observer observer) {
		this.observer = observer;
		this.latitude = latitude;
		this.longitude = longitude;
		createUserDetails(nameList);
	}

	/**
	 * Create the user details list.
	 * 
	 * @param nameList
	 *            List<ParseObject>
	 */
	private void createUserDetails(List<ParseObject> nameList) {
		dbUserDetails = new ArrayList<DBUserDetails>();
		for (final ParseObject parseObject : nameList) {
			try {
				final DBUserDetails userDetails = new DBUserDetails();
				ParseFile file = (ParseFile) parseObject.get("profileImage");
				userDetails.name = (String) parseObject.get("Name");
				String lat = (String) parseObject.get("Latitude");
				String lon = (String) parseObject.get("Longitude");
				if (!TextUtils.isEmpty(lon) && !TextUtils.isEmpty(lat)) {
					userDetails.latitude = Double.parseDouble(lat);
					userDetails.longitude = Double.parseDouble(lon);
					String distance = DBUtilities.getDistance(latitude,
							longitude, userDetails.latitude,
							userDetails.longitude);
					userDetails.distance = distance;
				} else {
					userDetails.distance = "0";
				}
				userDetails.emailId = (String) parseObject.get("emailId");
				userDetails.contactNumber = (String) parseObject.get("Contact");
				userDetails.location = (String) parseObject.get("Location");
				if (null != file) {
					byte[] fileByte = null;
					fileByte = file.getData();
					userDetails.userImage = fileByte;
				}
				dbUserDetails.add(userDetails);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		triggerObservers();
	}

	/**
	 * This is the method for trigger the observer.
	 */
	private void triggerObservers() {
		setChanged();
		notifyObservers();
		observer.update(DBParserAsync.this, dbUserDetails);
	}
}
