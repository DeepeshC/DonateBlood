/*@(#)LocationService.java}
 */
package com.sc.donateblood.gps.services;

import java.util.Observer;

import android.content.Context;

import com.sc.donateblood.info.LocationData;

/**
 * @author deepesh, deepesh.c@experionglobal.com <br>
 *         Aug 16, 2012, 8:25:06 PM <br>
 *         Package:- <b>com.sociable.ghs.services</b> <br>
 *         Project:- <b>Sociable</b>
 *         <p>
 */
public interface DBGPSLocationService {
	/**
	 * This is the method for get user location.
	 * 
	 * @param location
	 *            the location data
	 * @param observer
	 *            the observer
	 */
	void getUserLocation(LocationData location, Observer observer);

	/**
	 * This is the method for get unregister the location listeners.
	 */
	void unRegisterListeners();

	/**
	 * This is the method for get last known location first time.
	 * 
	 * @return locationModel the location model
	 */
	LocationData getLastKnownLocation(Context context);
}
