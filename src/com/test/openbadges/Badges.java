package com.test.openbadges;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.test.adapters.BadgesAdapter;
import com.test.models.Badge;
import com.test.threads.BadgesThread;
import com.test.threads.BadgesThread.BadgesResult;
import com.text.notifications.SimpleDialog;

public class Badges extends ListActivity {

	private ProgressDialog pDialog;
	private ListView list;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.badges);
		
		list = getListView();
		int groupID = getIntent().getExtras().getInt("groupID");
		
		startProgressDialog();
		startThread(groupID);
		
	}
	

	/**
	 * Displays a dialog. This method is using to tell the user there was an error.
	 * @param msg - The message to display.
	 */
	private void displayMessage(String msg) {
		SimpleDialog sd = new SimpleDialog(this, false);
		sd.setMessage(msg);
		sd.setOkButton(getString(R.string.ok), new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
	        	
			}
		});
		sd.show();
	}
	
	/**
	 * Start the progress dialog.
	 */
	private void startProgressDialog() {
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
	private void stopProgressDialog() {
		if (pDialog != null && pDialog.isShowing())
			pDialog.dismiss();
	}
	
	private void startThread(int groupID) {
		final Context ctx = this;
		
		BadgesThread bThread = new BadgesThread(this, groupID);
		bThread.setResult(new BadgesResult() {
			
			@Override
			public void onGetBadgesSuccess(ArrayList<Badge> badgesList) {
				stopProgressDialog();
				
				if (badgesList != null) {
					BadgesAdapter listAdapter = new BadgesAdapter(ctx, R.layout.badge_item, badgesList);
					list.setAdapter(listAdapter);
				} else {
					TextView txtNoGroups = (TextView) findViewById(R.id.txtBNoBadges);
					txtNoGroups.setVisibility(View.VISIBLE);
					displayMessage(ctx.getString(R.string.no_badges_found));
				}
			}
			
			@Override
			public void onGetBadgesFail() {
				stopProgressDialog();
			}
			
			@Override
			public void onErrorConnection() {
				stopProgressDialog();
			}
		});
		
		Thread t = new Thread(bThread, "bThread");
		t.start();
	}
	
}
