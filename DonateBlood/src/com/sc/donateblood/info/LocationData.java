/**
 * 
 */
package com.sc.donateblood.info;

import java.io.Serializable;

import android.content.Context;

/**
 * Location data class.
 * 
 */
public class LocationData implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;
	/** The latitude. */
	private double latitude;
	/** The longitude. */
	private double longitude;
	/** The accuracy. */
	private double accuracy;
	/** The context. */
	private Context context;
	/** The provider. */
	private String provider;
	/**
	 * Time.
	 */
	private Long time;

	/**
	 * This method can be used for getting the latitude.
	 * 
	 * @return The latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * This method can be used for setting the latitude.
	 * 
	 * @param latitude
	 *            The latitude to set.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * This method can be used for getting the longitude.
	 * 
	 * @return The longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * This method can be used for setting the longitude.
	 * 
	 * @param longitude
	 *            The longitude to set.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * This method can be used for getting the accuracy.
	 * 
	 * @return The accuracy.
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * This method can be used for setting the accuracy.
	 * 
	 * @param accuracy
	 *            The accuracy to set.
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * This method can be used for getting the context.
	 * 
	 * @return The context.
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * This method can be used for setting the context.
	 * 
	 * @param context
	 *            The context to set.
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * This method can be used for getting the provider.
	 * 
	 * @return The provider.
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * This method can be used for setting the provider.
	 * 
	 * @param provider
	 *            The provider to set.
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * This method can be used for getting the time.
	 * 
	 * @return The time.
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * This method can be used for setting the time.
	 * 
	 * @param time
	 *            The time to set.
	 */
	public void setTime(Long time) {
		this.time = time;
	}

}
