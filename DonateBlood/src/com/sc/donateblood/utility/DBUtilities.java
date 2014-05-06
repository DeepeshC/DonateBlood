/**
 * 
 */
package com.sc.donateblood.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.sc.donateblood.R;
import com.sc.donateblood.info.DBUserDetails;
import com.sc.donateblood.info.LocationData;

/**
 * @author Deepesh
 * 
 */
public class DBUtilities {

	/**
	 * This is the method for getting the button background color .
	 * 
	 * @return color code
	 */
	public static int getButtonBackgroundColor() {
		return Color.rgb(102, 178, 255);
	}

	/**
	 * This is the method for method for set the text color white or not.
	 * 
	 * @return color code
	 */
	public static int getTextColor() {
		return Color.rgb(252, 252, 252);
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	public static boolean isBetterLocation(LocationData location,
			LocationData currentBestLocation) {
		if (null == currentBestLocation) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > DBConstants.TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -DBConstants.TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (null == provider1) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	/**
	 * method for calculate distance.
	 * 
	 * @param latitude1
	 *            current location latitude
	 * @param longitude1
	 *            current location longitude
	 * @param latitude2
	 *            Gym latitude
	 * @param longitude2
	 *            Gym longitude
	 * @return distance distance between current location and gym location
	 */
	public static String getDistance(double latitude1, double longitude1,
			double latitude2, double longitude2) {

		double distance;

		Location locationA = new Location("Current Location");

		locationA.setLatitude(latitude1);
		locationA.setLongitude(longitude1);

		Location locationB = new Location("Gym Location");

		locationB.setLatitude(latitude2);
		locationB.setLongitude(longitude2);

		distance = locationA.distanceTo(locationB);
		double radd = distance * DBConstants.kms;
		DecimalFormat df = new DecimalFormat("###.##");

		return df.format(radd);
	}

	/**
	 * This is the method for sort the list .
	 * 
	 * @param gymList
	 *            unsorted List
	 * @return sorted list
	 */
	public static List<DBUserDetails> sortList(List<DBUserDetails> gymList) {
		Collections.sort(gymList, new Comparator<DBUserDetails>() {
			public int compare(DBUserDetails userDetails1,
					DBUserDetails userDetails2) {
				if (userDetails1.distance != null
						&& userDetails2.distance != null) {
					return userDetails1.distance
							.compareTo(userDetails2.distance);
				} else {
					return 0;
				}
			}
		});
		return gymList;
	}

	/**
	 * Method for getting the image from parse.
	 * 
	 * @param bum
	 *            ParseFile
	 * @return Bitmap Image
	 */
	public static Bitmap getBitmapFromParse(byte[] file) {
		Bitmap preScaledBitmap = null;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
		try {
			if (null != inputStream) {
				BitmapFactory.Options imageOptions = new BitmapFactory.Options();
				imageOptions.inJustDecodeBounds = true;
				preScaledBitmap = BitmapFactory.decodeStream(inputStream, null,
						imageOptions);
				BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
				scaleOptions.inSampleSize = 1;
				inputStream = new ByteArrayInputStream(file);
				preScaledBitmap = BitmapFactory.decodeStream(inputStream, null,
						scaleOptions);
				inputStream.close();
			}
		} catch (IOException e) {
		} catch (OutOfMemoryError e) {
		}

		return preScaledBitmap;
	}

	/**
	 * Method for getting the blood groups.
	 * 
	 * @return Blood groups
	 */
	public static String[] getBloodGroups() {
		String[] groups = { "AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "O-" };
		return groups;
	}

	/**
	 * Method for getting sex.
	 * 
	 * @return sex
	 */
	public static String[] getSex() {
		String[] sexs = { "Male", "FeMale" };
		return sexs;
	}

	/**
	 * This is the method for create the email.
	 * 
	 * @param gymName
	 *            gym Name
	 * @param feedback_address
	 *            feedback address
	 * @param message
	 *            message
	 */
	public static void sendMail(Context context, String subject,
			String email_address, String message, String cc_address,
			int requestCode) {
		Activity activity = (Activity) context;
		try {
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

			String aEmailList[] = { email_address };
			String aEmailCCList[] = { cc_address };
			String aEmailBCCList[] = { "" };

			emailIntent
					.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
			emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
			emailIntent.putExtra(android.content.Intent.EXTRA_BCC,
					aEmailBCCList);

			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
			activity.startActivityForResult(emailIntent, requestCode);

		} catch (ActivityNotFoundException e) {
			Log.d("e_mail FeedBAck", "" + e);
		}

	}

	/**
	 * Method for check the internet connection.
	 * 
	 * @return
	 */
	public static boolean checkInternet(Context context) {
		DBDialogUtils dbDialogUtils = new DBDialogUtils(context);
		DBCheckConnectivity connectivity = new DBCheckConnectivity();
		Boolean connected = connectivity.checkNow(context);
		if (connected) {
			return true;
		} else {
			dbDialogUtils.createOkAlert(
					context.getResources().getString(R.string.no_network), "");
			return false;

		}

	}

	/**
	 * This is the method for start any intent(VIEW).
	 * 
	 * @param context
	 *            Context
	 * @param url
	 *            url to launch the intent
	 */
	public static void startIntent(Context context, String url, String action) {
		Intent intent = new Intent(action, Uri.parse(url));
		context.startActivity(intent);
	}

	/**
	 * Method for getting a rescaled bitmap image.
	 * 
	 * @param imageByteArray
	 *            byte array image
	 * @return Bitmap image
	 */
	public static Bitmap getReducedBitmapFromByte(byte[] imageByteArray) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				imageByteArray);
		Bitmap preScaledBitmap = null;
		try {
			if (null != inputStream) {
				BitmapFactory.Options imageOptions = new BitmapFactory.Options();
				imageOptions.inJustDecodeBounds = true;
				preScaledBitmap = BitmapFactory.decodeStream(inputStream, null,
						imageOptions);
				BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
				scaleOptions.inSampleSize = 1;
				inputStream = new ByteArrayInputStream(imageByteArray);
				preScaledBitmap = BitmapFactory.decodeStream(inputStream, null,
						scaleOptions);
				inputStream.close();
				return preScaledBitmap;
			}
		} catch (IOException e) {
		} catch (OutOfMemoryError e) {

		}
		return preScaledBitmap;
	}

	/**
	 * Method for getting a rescaled bitmap image.
	 * 
	 * @param imageByteArray
	 *            byte array image
	 * @return Bitmap image
	 */
	public static Bitmap getReducedBitmapFromFile(String path) {
		Bitmap preScaledBitmap = null;
		try {
			if (null != path) {
				BitmapFactory.Options imageOptions = new BitmapFactory.Options();
				imageOptions.inJustDecodeBounds = true;
				preScaledBitmap = BitmapFactory.decodeFile(path, imageOptions);
				BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
				scaleOptions.inSampleSize = 1;
				preScaledBitmap = BitmapFactory.decodeFile(path, scaleOptions);
				return preScaledBitmap;
			}
		} catch (OutOfMemoryError e) {

		}
		return preScaledBitmap;
	}
}
