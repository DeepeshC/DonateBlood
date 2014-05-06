/*@(#)LocationServiceImpl.java}
 */
package com.sc.donateblood.gps.services;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.sc.donateblood.gps.DBGPSUtilityAgent;
import com.sc.donateblood.info.LocationData;
import com.sc.donateblood.utility.DBUtilities;

/**
 * @author deepesh.c , deepesh.c@experionglobal.com <br>
 * <br>
 *         Package:- <b>com.bottegasol.com.android.migym.service</b> <br>
 *         Project:- <b>Migym</b>
 *         <p>
 */
public class DBGPSLocationServiceImpl extends Observable implements
		DBGPSLocationService {
	/** The context. */
	private Context context;
	/** The location thread. */
	private Thread locationThread;
	/** The GPSLocationManager. */
	private DBGPSLocationManager gpsLocationManager;
	/** The NetworkLocationManager. */
	private DBGPSLocationManager networkLocationManager;
	/** The gps location data. */
	private LocationData gpsLocation;
	/** The network location data. */
	private LocationData ntwrkLocation;
	/** The gps utility agent. */
	private DBGPSUtilityAgent gpsUtilityAgent;
	/** Last known location. **/
	private LocationData lastKnownLocation;

	/**
	 * 
	 * Constructor for GPSLocationServiceImpl.
	 * 
	 * @param location
	 *            LocationData
	 */
	@Inject
	public DBGPSLocationServiceImpl(LocationData location) {
		this.context = location.getContext();
		gpsLocationManager = new DBGPSLocationManager();
		networkLocationManager = new DBGPSLocationManager();
		lastKnownLocation = getLastKnownLocation(context);
	}

	/**
	 * The override method for get user location.
	 * 
	 * @param location
	 *            the location data
	 * @param observer
	 *            the observer
	 */
	@Override
	public void getUserLocation(LocationData location, final Observer observer) {
		try {
			gpsUtilityAgent = new DBGPSUtilityAgent(context);
			if (gpsUtilityAgent.checkGpsStatus()) {
				gpsLocation = null;
				ntwrkLocation = null;
				LocationData gpsLocation = new LocationData();
				gpsLocation.setContext(location.getContext());
				gpsLocation.setProvider("gps");

				LocationData networkLocation = new LocationData();
				networkLocation.setContext(location.getContext());
				networkLocation.setProvider("network");
				registerManagerGPS(gpsLocationManager, gpsLocation);
				registerManagerNtwrk(networkLocationManager, networkLocation);
				createAndRunLocationThread(observer);
			} else {
				LocationData networkLocation = new LocationData();
				networkLocation.setContext(location.getContext());
				networkLocation.setProvider("network");
				registerManagerNtwrk(networkLocationManager, networkLocation);
				createAndRunLocationThread(observer);
			}
		} catch (Exception e) {
			Log.d(" GPSLocationServiceImpl getUserLocation", e.getMessage());
		}
	}

	/**
	 * This is the method for remove the location updates..
	 */
	public void removeLocationUpdates() {
		if (gpsLocationManager != null) {
			gpsLocationManager.unRegisterListener();
		}
	}

	/**
	 * The method to register gps listener.
	 * 
	 * @param manager
	 *            the manager
	 * @param locationData
	 *            the locationData
	 */
	private void registerManagerGPS(DBGPSLocationManager manager,
			LocationData locationData) {
		manager.registerLocationManager(locationData, new Observer() {

			@Override
			public void update(Observable observable, Object data) {
				gpsLocation = (LocationData) data;
			}
		});
	}

	/**
	 * The method to register network listener.
	 * 
	 * @param manager
	 *            the manager
	 * @param locationData
	 *            the locationData
	 */
	private void registerManagerNtwrk(DBGPSLocationManager manager,
			LocationData locationData) {
		manager.registerLocationManager(locationData, new Observer() {

			@Override
			public void update(Observable observable, Object data) {
				ntwrkLocation = (LocationData) data;
			}
		});
	}

	/**
	 * This is the method for create and run the location thread.
	 */
	private void createAndRunLocationThread(final Observer observer) {

		locationThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						Thread.sleep(1000);

					}
				} catch (InterruptedException e) {
					Log.e("EXCEPTION", "" + e.getMessage());
				} finally {
					if (gpsLocation != null
							&& lastKnownLocation != null
							&& gpsUtilityAgent.checkGpsStatus()
							&& DBUtilities.isBetterLocation(gpsLocation,
									lastKnownLocation)) {
						Log.d("gpsLocation", "returning gpsLocation");
						observer.update(DBGPSLocationServiceImpl.this,
								gpsLocation);
						gpsLocation = null;
					} else if (ntwrkLocation != null
							&& lastKnownLocation != null
							&& DBUtilities.isBetterLocation(ntwrkLocation,
									lastKnownLocation)) {
						Log.d("ntwrkLocation", "returning ntwrkLocation");
						observer.update(DBGPSLocationServiceImpl.this,
								ntwrkLocation);
						ntwrkLocation = null;
					} else if (lastKnownLocation != null) {
						Log.d("last known location", "returning last location");
						observer.update(DBGPSLocationServiceImpl.this,
								lastKnownLocation);
					} else {
						observer.update(DBGPSLocationServiceImpl.this, null);
					}
				}
			}
		};
		locationThread.start();
	}

	/**
	 * This is the method for get last known location.
	 * 
	 * @return locationModel the location model
	 */
	@Override
	public LocationData getLastKnownLocation(Context context) {
		LocationData gpsLocation = gpsLocationManager.getLastKnownLocation(
				"gps", context);
		LocationData networkLocation = networkLocationManager
				.getLastKnownLocation("network", context);
		if (gpsLocation != null) {
			return gpsLocation;
		} else if (networkLocation != null) {
			return networkLocation;
		} else {
			return null;
		}
	}

	/**
	 * This is the method for unregister the listeners.
	 */
	@Override
	public void unRegisterListeners() {
		try {
			gpsLocationManager.unRegisterListener();
			networkLocationManager.unRegisterListener();
		} catch (Exception e) {
			Log.d(" GPSLocationServiceImpl unRegisterListeners", e.getMessage());
		}
	}

}
