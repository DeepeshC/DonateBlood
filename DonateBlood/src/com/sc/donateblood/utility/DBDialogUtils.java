/*
 * @(#)DialogUtils.java}
 *
 * Copyright (c) 2011 Experion Technologies Pvt. Ltd.
 * 407,Thejaswini, Technopark Campus, Trivandrum 695 581
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Experion Technologies Pvt. Ltd. You shall not disclose such
 * Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into
 * with Experion Technologies.
 */
package com.sc.donateblood.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.Window;

import com.google.inject.Inject;
import com.sc.donateblood.R;

/**
 * Creation of dialogs. <dt><b>Date</b>
 * <dd>Nov 16, 2012, 11:33:52 AM
 * <dt><b>Module</b>
 * <dd>Android3.3
 */
public class DBDialogUtils {
	/*** Alaert dialog. */
	private AlertDialog alertDialog;
	/** Current context. **/
	private Context currentContext;
	/** String for OK. **/
	private String ok;
	/** String for NO. **/
	private String no;
	/** String for YES. **/
	private String yes;
	/** Activity. **/
	private Activity activity;

	/**
	 * Constructor for DialogUtils.
	 */
	@Inject
	public DBDialogUtils(Context context) {
		super();
		this.currentContext = context;
		this.activity = (Activity) context;
		ok = currentContext.getResources().getString(R.string.ok);
		no = currentContext.getResources().getString(R.string.no);
		yes = currentContext.getResources().getString(R.string.yes);
	}

	/**
	 * This is the method for create an alert box with Yes and No buttons.
	 * 
	 * @param context
	 *            Context
	 * @param message
	 *            message to display
	 * @param title
	 *            title
	 * @return alert dialog
	 */
	public AlertDialog createAlert(String message, String title,
			String buttonTextOne, String ButtonTextTwo) {
		Builder builder = new AlertDialog.Builder(currentContext);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(true);
		builder.setPositiveButton(buttonTextOne, this.new OkOnClickListener());
		builder.setNegativeButton(ButtonTextTwo,
				this.new CancelOnClickListener());
		alertDialog = builder.create();
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCanceledOnTouchOutside(false);
		if (!activity.isFinishing()) {
			alertDialog.show();
		}
		alertDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					return true;
				} else {
					return false;
				}
			}
		});
		return alertDialog;
	}

	/**
	 * This is the method for create an alert box with Yes and No buttons.
	 * 
	 * @param context
	 *            Context
	 * @param message
	 *            message to display
	 * @param title
	 *            title
	 * @return alert dialog
	 */
	public AlertDialog createAlert(String message, String title) {
		Builder builder = new AlertDialog.Builder(currentContext);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(true);
		builder.setPositiveButton(yes, this.new OkOnClickListener());
		builder.setNegativeButton(no, this.new CancelOnClickListener());
		alertDialog = builder.create();
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCanceledOnTouchOutside(false);
		if (!activity.isFinishing()) {
			alertDialog.show();
		}
		alertDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					return true;
				} else {
					return false;
				}
			}
		});
		return alertDialog;
	}

	/**
	 * This is the method for creat an alert box with Ok button.
	 * 
	 * @param context
	 *            Context
	 * @param message
	 *            message to display
	 * @param title
	 *            title
	 * @return alert dialog
	 */
	public AlertDialog createOkAlert(String message, String title) {
		alertDialog = new AlertDialog.Builder(currentContext).create();
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setButton(Dialog.BUTTON_NEUTRAL, ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.dismiss();
					}
				});
		alertDialog.setCancelable(false);
		if (!activity.isFinishing()) {
			alertDialog.show();
		}
		alertDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					return true;
				} else {
					return false;
				}
			}
		});
		return alertDialog;
	}

	/**
	 * Class for Dialog OK button Click.
	 */
	private final class CancelOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dismissDialog(dialog);
		}
	}

	/**
	 * Class for Dialog No button Click.
	 */
	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dismissDialog(dialog);
		}
	}

	/**
	 * This is the method for dismiss the dialog.
	 * 
	 * @param dialog
	 *            DialogInterface
	 */
	private void dismissDialog(DialogInterface dialog) {
		if (null != dialog) {
			dialog.dismiss();
		}
	}

}
