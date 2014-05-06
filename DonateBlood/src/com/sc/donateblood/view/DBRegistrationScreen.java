/**
 * 
 */
package com.sc.donateblood.view;

import java.io.ByteArrayOutputStream;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.sc.donateblood.R;
import com.sc.donateblood.adapter.DBGrouopSpinnerAdapter;
import com.sc.donateblood.utility.DBDialogUtils;
import com.sc.donateblood.utility.DBUtilities;

/**
 * @author Deepesh
 * 
 */
public class DBRegistrationScreen extends RoboFragmentActivity {
	/** Action bar layout. **/
	@InjectView(R.id.action_bar_layout)
	private LinearLayout actionBarView;
	/** Title Text. ***/
	@InjectView(R.id.text_actionbar_title)
	private TextView actionBarTitle;
	/** Register text. **/
	@InjectView(R.id.text_register)
	private TextView registerText;
	/** Context. **/
	private Context instance;
	/** Blood Group Spinner. **/
	private Spinner bloodGroupSpinner;
	/** Blood Group Spinner. **/
	private Spinner sexSpinner;
	/** Save button. **/
	private Button saveBtn;
	/** User Image. **/
	private ImageView userImage;
	/** Req code for image pick. **/
	private static int RESULT_LOAD_IMAGE = 100;
	/** Req code for image capture. **/
	private static int RESULT_CAPTURE_IMAGE = 101;
	/** String for name. **/
	private String name;
	/** String for location. **/
	private String location;
	/** String for sex. **/
	private String sex;
	/** String for blood group. **/
	private String bloodGroup;
	/** String for email. **/
	private String email;
	/** String for contact. **/
	private String contact;
	/** Dialog. **/
	@Inject
	private DBDialogUtils dbDialogUtils;
	/** ProgressDialog. **/
	private ProgressDialog progressDialog;
	/** Name text view. **/
	private EditText nameText;
	/** Contact text view. **/
	private EditText contactText;
	/** Email text view. **/
	private EditText emailText;
	/** Location text view. **/
	private EditText locationText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_screen);
		instance = this;
		initViews();
		setOnClickListener();
	}

	/**
	 * Method for initialize views.
	 */
	private void initViews() {
		actionBarView
				.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		actionBarTitle.setText(getResources()
				.getString(R.string.register_title));
		actionBarTitle.setTextColor(DBUtilities.getTextColor());

	}

	/**
	 * Method for handle events.
	 */
	private void setOnClickListener() {
		registerText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				createRegDialog();
			}
		});
	}

	/**
	 * Method for create the blood group spinner.
	 */
	private void createBllodGroupSpinner() {
		DBGrouopSpinnerAdapter dbGrouopSpinnerAdapter = new DBGrouopSpinnerAdapter(
				instance, android.R.layout.simple_spinner_item,
				DBUtilities.getBloodGroups());
		dbGrouopSpinnerAdapter.setInflater(getLayoutInflater());
		bloodGroupSpinner.setAdapter(dbGrouopSpinnerAdapter);
	}

	/**
	 * Method for create the blood group spinner.
	 */
	private void createSexSpinner() {
		DBGrouopSpinnerAdapter dbGrouopSpinnerAdapter = new DBGrouopSpinnerAdapter(
				instance, android.R.layout.simple_spinner_item,
				DBUtilities.getSex());
		dbGrouopSpinnerAdapter.setInflater(getLayoutInflater());
		sexSpinner.setAdapter(dbGrouopSpinnerAdapter);
	}

	/**
	 * This is the method for creating the SMS window.
	 */
	private void createRegDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_registration);
		bloodGroupSpinner = (Spinner) dialog.findViewById(R.id.spinner_bg_reg);
		sexSpinner = (Spinner) dialog.findViewById(R.id.spinner_sex_reg);
		saveBtn = (Button) dialog.findViewById(R.id.btn_save_reg);
		userImage = (ImageView) dialog.findViewById(R.id.image_user);
		nameText = (EditText) dialog.findViewById(R.id.edit_fname_reg);
		emailText = (EditText) dialog.findViewById(R.id.edt_email_reg);
		contactText = (EditText) dialog.findViewById(R.id.edt_contact_reg);
		locationText = (EditText) dialog.findViewById(R.id.edit_location);
		saveBtn.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		dialog.setCanceledOnTouchOutside(false);
		createBllodGroupSpinner();
		createSexSpinner();
		TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
		cancel.setBackgroundColor(DBUtilities.getButtonBackgroundColor());
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		setOnDialogButtonsEvent();
		dialog.show();
	}

	/**
	 * Method for handle dialog buttons event.
	 */
	private void setOnDialogButtonsEvent() {
		userImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				goToImageGallery();
				// goToCamera();
			}
		});
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getUserData();
			}
		});
		bloodGroupSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long arg3) {
						bloodGroup = DBUtilities.getBloodGroups()[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						bloodGroup = "AB+";
					}
				});
		sexSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				sex = DBUtilities.getSex()[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				sex = "Male";
			}
		});
	}

	/**
	 * Get the user entered data.
	 */
	private void getUserData() {
		name = nameText.getText().toString().trim();
		location = locationText.getText().toString().trim();
		contact = contactText.getText().toString().trim();
		email = emailText.getText().toString().trim();
		if (validateInput(name, location, email, contact)) {
			createProgress();
			saveDatatToParse();
		}
	}

	/**
	 * Method for go to image Gallery
	 */
	private void goToImageGallery() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	/**
	 * Method for go to Camera
	 */
	private void goToCamera() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, RESULT_CAPTURE_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != imageReturnedIntent) {
			Uri selectedImage = imageReturnedIntent.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			if (null != userImage)
				userImage.setImageBitmap(DBUtilities
						.getReducedBitmapFromFile(picturePath));

		} else if (requestCode == RESULT_CAPTURE_IMAGE
				&& resultCode == RESULT_OK && null != imageReturnedIntent) {
			Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
			if (null != userImage)
				userImage.setImageBitmap(photo);
		}
	}

	/**
	 * Method to save the registered data to parse cloud.
	 */
	private void saveDatatToParse() {
		Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		ParseObject dataObject = new ParseObject("UserDetails");
		dataObject.put("Name", name);
		dataObject.put("Location", location);
		dataObject.put("emailId", email);
		dataObject.put("Contact", contact);
		dataObject.put("Sex", sex);
		dataObject.put("BloodGroup", bloodGroup);
		if (byteArray != null) {
			ParseFile file = new ParseFile(name.toString() + ".jpg", byteArray);
			file.saveInBackground();
			dataObject.put("profileImage", file);
		}
		dataObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException ex) {
				dismissDialog();
			}
		});
	}

	/**
	 * Validates input to be non-null, within the desired range, and void of
	 * errors. If the input is not validated then an error dialog is displayed.
	 * 
	 * @param termsChecked
	 *            true if checked terms & conditions/else false
	 * @param dob
	 *            date of birth
	 * @param weight
	 *            weight
	 * @return true if success/else false
	 */
	private boolean validateInput(String name, String location, String email,
			String contact) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
				|| TextUtils.isEmpty(location) || TextUtils.isEmpty(contact)) {
			dbDialogUtils.createOkAlert(
					getResources().getString(R.string.required_fields_message),
					getResources().getString(R.string.required_fields_header));
		} else {
			return true;
		}

		return false;
	}

	/**
	 * Method for create progress.
	 */
	private void createProgress() {
		progressDialog = new ProgressDialog(instance);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialogInterface, int i,
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
		progressDialog.setMessage(getResources()
				.getString(R.string.please_wait));
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		if (!isFinishing()) {
			progressDialog.show();
		}
	}

	/**
	 * This is the method for dismiss the dialog.
	 */
	private void dismissDialog() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.setCancelable(true);
			progressDialog.dismiss();
		}
	}
}
