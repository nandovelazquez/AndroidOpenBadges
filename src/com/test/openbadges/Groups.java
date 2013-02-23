package com.test.openbadges;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.test.adapters.GroupsAdapter;
import com.test.models.Group;
import com.test.threads.GroupsThread;
import com.test.threads.GroupsThread.GroupsResult;
import com.text.notifications.SimpleDialog;

public class Groups extends ListActivity {

	private ProgressDialog pDialog;
	private ListView listView;
	private ArrayList<Group> groupsL = new ArrayList<Group>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups);
		
		listView = getListView();
		
		startProgressDialog();
		startThread();
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

	private void startThread() {
		final Context ctx = this;
		
		GroupsThread groupsT = new GroupsThread(this);
		groupsT.setResult(new GroupsResult() {
			
			@Override
			public void onGetGroupsSuccess(ArrayList<Group> groupsList) {
				stopProgressDialog();
				
				if (groupsList != null) {
					GroupsAdapter listAdapter = new GroupsAdapter(ctx, R.layout.group_item, groupsList);
					listView.setAdapter(listAdapter);
					groupsL = groupsList; 
				} else {
					TextView txtNoGroups = (TextView) findViewById(R.id.txtGNoGroups);
					txtNoGroups.setVisibility(View.VISIBLE);
					displayMessage(ctx.getString(R.string.no_groups_found));
				}
				
			}
			
			@Override
			public void onGetGroupsFail() {
				stopProgressDialog();
			}
			
			@Override
			public void onErrorConnection() {
				stopProgressDialog();
			}
		});
		
		Thread t = new Thread(groupsT, "groupsT");
		t.start();
	}

	public void onListItemClick(ListView listView, View view, int position, long id) {
		Group group = groupsL.get(position);
		
		Intent i = new Intent(this, Badges.class);
		Bundle b = new Bundle();
		
		b.putInt("groupID", group.getGroupId());
		i.putExtras(b);
		
		startActivity(i);
		
	}
}
