/**
 * 
 */
package com.sc.donateblood.utility;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.sc.donateblood.R;
import com.sc.donateblood.adapter.DBGrouopSpinnerAdapter;

/**
 * @author Deepesh
 * 
 */
public class DBRegisterDialog extends RoboDialogFragment {
	/** Blood Group Spinner. **/
	@InjectView(R.id.spinner_bg_reg)
	private Spinner bloodGroupSpinner;
	/** Blood Group Spinner. **/
	@InjectView(R.id.spinner_sex_reg)
	private Spinner sexSpinner;
	/** Context. **/
	private Context context;
	/** Activity. **/
	private Activity activity;

	/**
	 * Constructor.
	 */
	public DBRegisterDialog(Context context) {
		this.context = context;
		this.activity = (Activity) context;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity(), R.style.RegisterDialog);
		dialog.setContentView(R.layout.dialog_registration);
		// TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
		// cancel.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		// cancel.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dialog.cancel();
		// }
		// });
		createSexSpinner();
		createBllodGroupSpinner();
		return dialog;
	}

	/**
	 * Method for create the blood group spinner.
	 */
	private void createBllodGroupSpinner() {
		DBGrouopSpinnerAdapter dbGrouopSpinnerAdapter = new DBGrouopSpinnerAdapter(
				context, android.R.layout.simple_spinner_item,
				DBUtilities.getBloodGroups());
		dbGrouopSpinnerAdapter.setInflater(activity.getLayoutInflater());
		bloodGroupSpinner.setAdapter(dbGrouopSpinnerAdapter);
	}

	/**
	 * Method for create the blood group spinner.
	 */
	private void createSexSpinner() {
		DBGrouopSpinnerAdapter dbGrouopSpinnerAdapter = new DBGrouopSpinnerAdapter(
				context, android.R.layout.simple_spinner_item,
				DBUtilities.getSex());
		dbGrouopSpinnerAdapter.setInflater(activity.getLayoutInflater());
		// sexSpinner.setAdapter(dbGrouopSpinnerAdapter);
	}
}
