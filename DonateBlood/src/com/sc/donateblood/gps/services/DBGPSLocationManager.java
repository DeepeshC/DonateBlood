/*@(#)GPSLocationManager.java}
 */
package com.sc.donateblood.gps.services;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.sc.donateblood.info.LocationData;

/**
 * @author deepesh,deepesh.c@experionglobal.com <br>
 * <br>
 *         Package:- <b>com.bottegasol.com.android.migym.service</b> <br>
 *         Project:- <b>Migym</b>
 *         <p>
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
		this.locationData = locationData;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		locationManager
				.requestLocationUpdates(provider, 0, 0, locationListener);
	}

	/**
	 * This is the method for remove the location updates..
	 */
	public void unRegisterListener() {
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
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
	 * This is the method for get last known location.
	 * 
	 * @param provider
	 *            the provider
	 * @return locationModel the location model
	 */
	public LocationData getLastKnownLocation(String provider) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		android.location.Location location = locationManager
				.getLastKnownLocation(provider);
		if (location != null) {
			LocationData locationModel = new LocationData();
			locationModel.setLatitude(location.getLatitude());
			locationModel.setLongitude(location.getLongitude());
			locationModel.setAccuracy(location.getAccuracy());
			locationModel.setTime(location.getTime());
			return locationModel;
		}
		return null;
	}

	/**
	 * This is the method for get last known location.
	 * 
	 * @param provider
	 *            the provider
	 * @return locationModel the location model
	 */
	public LocationData getLastKnownLocation(String provider, Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		android.location.Location location = locationManager
				.getLastKnownLocation(provider);
		if (location != null) {
			LocationData locationModel = new LocationData();
			locationModel.setLatitude(location.getLatitude());
			locationModel.setLongitude(location.getLongitude());
			locationModel.setAccuracy(location.getAccuracy());
			locationModel.setTime(location.getTime());
			return locationModel;
		}
		return null;
	}

	/**
	 * @author deepesh.c, deepesh.c@experionglobal.com <br>
	 *         Package:- <b>com.bottegasol.com.android.migym.services/b> <br>
	 *         Project:- <b>Migym</b>
	 *         <p>
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
			if (location != null) {
				locationData.setLatitude(location.getLatitude());
				locationData.setLongitude(location.getLongitude());
				locationData.setAccuracy(location.getAccuracy());
				locationData.setTime(location.getTime());
				Log.d("locationData", "DATA-->" + locationData.getLatitude()
						+ "," + locationData.getLongitude());
				Log.d("provider", " " + provider);
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
