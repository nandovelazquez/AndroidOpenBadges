package com.test.threads;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.test.models.Group;
import com.test.net.Connect;
import com.test.utils.Logger;
import com.test.utils.Preferences;
import com.test.utils.Utils;
import com.test.utils.UtilsConstants;

public class GroupsThread implements Runnable {

	protected int errorType = 1;
	protected final int noError = 1;
	protected final int notGotData = 2;
	protected final int noNetwork = 3;
	
	private Context ctx;
	private GroupsResult result;
	private ArrayList<Group> groupsList;
	private String url;
	
	public GroupsThread(Context ctx) {
		this.ctx = ctx;
		url = UtilsConstants.DISPLAYER_TAG + Preferences.getUserId(ctx) + UtilsConstants.GROUPS_URL;
//		url = UtilsConstants.DISPLAYER_TAG + "26371" + UtilsConstants.GROUPS_URL;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message ms){
			
			if (errorType == noError)
				result.onGetGroupsSuccess(groupsList);
			else if (errorType == notGotData)
				result.onGetGroupsFail();
			else if (errorType == noNetwork)
				result.onErrorConnection();
				
		}
	}; 
	
	@Override
	public void run() {
		if (Utils.isNetworkAvailable(ctx)) {
			try {
				
				Connect connect = new Connect();
				groupsList = (ArrayList<Group>) connect.getGroups(url);
				
			} catch (IOException e) {
				errorType = notGotData;
				Logger.Error("GroupsThread", e.toString());
			}
		} else {
			errorType = noNetwork;
			Logger.Error("GroupsThread", "No network connection");
		}
		
		handler.sendEmptyMessage(0);
	}
	
	public void setResult(GroupsResult result) {
		this.result = result;
	}

	public static abstract class GroupsResult {
		public abstract void onGetGroupsSuccess(ArrayList<Group> groupsList);
		public abstract void onGetGroupsFail();
		public abstract void onErrorConnection();
	}

}
