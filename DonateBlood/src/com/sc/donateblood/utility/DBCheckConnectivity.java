/**
 * 
 */
package com.sc.donateblood.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author Deepesh
 * 
 */
public class DBCheckConnectivity {
	/*** ConnectivityManager class object. */
	private ConnectivityManager connectivityManager;
	/** * Network info wifi. */
	private NetworkInfo wifiInfo;
	/*** Network info mobile. */
	private NetworkInfo mobileInfo;

	/**
	 * Check for <code>TYPE_WIFI</code> and <code>TYPE_MOBILE</code> connection
	 * using <code>isConnected()</code> Checks for generic Exceptions and writes
	 * them to logcat as <code>CheckConnectivity Exception</code>. Make sure
	 * AndroidManifest.xml has appropriate permissions.
	 * 
	 * @param con
	 *            Application context
	 * @return Boolean
	 */
	public Boolean checkNow(Context con) {

		try {
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			wifiInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			mobileInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			Log.d("Exception CheckConnectivity checkNow", "" + e);
		}

		return false;
	}

	/**
	 * This is the method for Alert Message when the Network connection doesn't
	 * exist.
	 * 
	 * @param con
	 *            Context
	 */
	public void connectivityMessage(Context con) {
		if (con != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			builder.setMessage(
					"There was an error downloading information from the server. Please try syncing again later.")
					.setTitle("Whoops! Network error")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	/**
	 * <p>
	 * This is the method for Alert Message when the file Access failed.
	 * </p>
	 * 
	 * @param con
	 *            Context
	 */
	public void fileAccessMessage(Context con) {

		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setMessage(
				"There was an error while openeing the file.Please try again later.")
				.setTitle("Whoops!").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
