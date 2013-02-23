package com.test.openbadges;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.test.models.User;
import com.test.threads.LogInThread;
import com.test.threads.LogInThread.LogInResult;
import com.test.utils.Preferences;
import com.text.notifications.PopUp;
import com.text.notifications.SimpleDialog;

public class LogIn extends Activity implements OnClickListener{

	private ProgressDialog pDialog;
	private Context ctx;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.ctx = this;
		
		Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
		TextView lblLink = (TextView) findViewById(R.id.txtLinkMozilla);
		
		btnLogIn.setOnClickListener(this);
		lblLink.setOnClickListener(this);
		
		SpannableString underlineText = new SpannableString("openbadges");
		underlineText.setSpan(new UnderlineSpan(), 0, underlineText.length(), 0);
		
		lblLink.setText(underlineText);
		
	}

	/**
	 * Displays a dialog. This method is using to tell the user there was an error.
	 * @param msg - The message to display.
	 */
	protected void displayMessage(String msg) {
		SimpleDialog sd = new SimpleDialog(this, false);
		sd.setMessage(msg);
		sd.setOkButton(getString(R.string.ok), new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	
			}
		});
		sd.show();
	}
	
	private void logIn() {
		TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
		String email = txtEmail.getText().toString();
		
		if (validateEmail(email)) {
			startProgressDialog();

			LogInThread lThread = new LogInThread(this, email);
			lThread.setResult(new LogInResult() {
				@Override
				public void onLogInSuccess(User user) {
					Preferences.setUserId(ctx, user.getUserId());
					Preferences.setUserEmail(ctx, user.getEmail());
					
					stopProgressDialog();
					
					PopUp pop = new PopUp(ctx);
					pop.show(getString(R.string.logged_success));
					
					finish();
					startActivity(new Intent(ctx, Groups.class));
				}
				
				@Override
				public void onLogInFail() {
					stopProgressDialog();
					displayMessage(getString(R.string.email_not_found));
				}
				
				@Override
				public void onErrorConnection() {
					stopProgressDialog();
					displayMessage(getString(R.string.no_network_connection));
				}
			});
			
			Thread t = new Thread(lThread, "LogInThread");
			t.start();
		} else {
			displayMessage(getString(R.string.invalid_email));
		}
	}
	
	/**
	 * Start the progress dialog.
	 */
	protected void startProgressDialog() {
		pDialog = new ProgressDialog(this);
		
		pDialog = ProgressDialog.show(this, "", this.getString(R.string.logging), true);
		pDialog.setCancelable(true);
		pDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				
			}
		});
	}
	
	/**
	 * Stop the progress dialog.
	 */
	protected void stopProgressDialog() {
		if (pDialog != null && pDialog.isShowing())
			pDialog.dismiss();
	}
	
	/**
	 * Validate if the email has a valid statement.
	 * @param email - The email to validate
	 * @return true if it's valid otherwise false.
	 */
	protected boolean validateEmail(String email) {
		return email.contains(".") && email.contains("@");
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnLogIn:
			logIn();
			break;
		case R.id.txtLinkMozilla:
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.openbadges.org"));
			startActivity(intent);
			break;
		}
	}
}
