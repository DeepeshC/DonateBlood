/*@(#)GPSUtilityAgent.java}
 */
package com.sc.donateblood.gps;

import android.content.Context;
import android.location.LocationManager;

/**
 * Gps utility class.
 */
public class DBGPSUtilityAgent {
	/*** Location manager - for checking gps. */
	private LocationManager locManager = null;
	/*** The constant Request code gps. */
	public static final int REQUEST_CODE_GPS = 1;

	/**
	 * Constructor for GPSUtilityAgent.
	 * 
	 * @param context
	 *            for GPSUtilityAgent
	 */
	public DBGPSUtilityAgent(Context context) {
		locManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	/**
	 * This is the method for checking the gps status.
	 * 
	 * @return boolean - The status as true or false.
	 */
	public Boolean checkGpsStatus() {
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

}
