/*@(#)GPSLocationManager.java}
 */
package com.sc.donateblood.gps;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.sc.donateblood.info.LocationData;

/**
 * GPSLOcation manager class.
 */
public class DBGPSLocationManager extends Observable {
	/** The location Listener. */
	private LocationListener locationListener;
	/** Location manager. */
	private LocationManager locationManager;
	/** The location data. */
	private LocationData locationData;
	/** The context. */
	private Context context;
	/** The provider. */
	private String provider;
	/** The observer. */
	private Observer observer;

	/**
	 * Constructor for GPSLocationManager.
	 */
	public DBGPSLocationManager() {
		locationData = new LocationData();
	}

	/**
	 * This is the method for registerLocationManager.
	 * 
	 * @param locationData
	 *            The location data
	 */
	public void registerLocationManager(LocationData locationData,
			final Observer observer) {
		this.observer = observer;
		this.context = locationData.getContext();
		this.provider = locationData.getProvider();
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		locationManager
				.requestLocationUpdates(provider, 0, 0, locationListener);
	}

	/**
	 * This method can be used for getting the locationData.
	 * 
	 * @return The locationData.
	 */
	public LocationData getLocationData() {
		return locationData;
	}

	/**
	 * This method can be used for setting the locationData.
	 * 
	 * @param locationData
	 *            The locationData to set.
	 */
	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}

	/**
	 * GPSLocation listener class.
	 */
	private class GPSLocationListener implements LocationListener {

		/**
		 * This is the method for get onLocationChanged.
		 * 
		 * @param location
		 *            for Location
		 */
		@Override
		public void onLocationChanged(android.location.Location location) {
			if (null != location) {
				locationManager.removeUpdates(locationListener);
				locationData.setLatitude(location.getLatitude());
				locationData.setLongitude(location.getLongitude());
				locationData.setAccuracy(location.getAccuracy());
				Log.d("location.getLatitude()",
						"Latitude-->" + locationData.getLatitude());
				Log.d("locationData.getLongitude() ", "Longitude-->"
						+ locationData.getLongitude());

				observer.update(DBGPSLocationManager.this, locationData);

			}
		}

		/**
		 * This is the method for onProviderDisabled.
		 * 
		 * @param provider
		 *            for provider
		 */
		@Override
		public void onProviderDisabled(String provider) {
		}

		/**
		 * This is the method for onProviderEnabled.
		 * 
		 * @param provider
		 *            for provider
		 */
		@Override
		public void onProviderEnabled(String provider) {
		}

		/**
		 * This is the method for onStatusChanged.
		 * 
		 * @param provider
		 *            for provider
		 * @param status
		 *            for status
		 * @param extras
		 *            for extras
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
