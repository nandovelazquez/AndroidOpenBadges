package com.test.threads;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.test.models.Badge;
import com.test.models.Group;
import com.test.net.Connect;
import com.test.threads.GroupsThread.GroupsResult;
import com.test.utils.Logger;
import com.test.utils.Preferences;
import com.test.utils.Utils;
import com.test.utils.UtilsConstants;

public class BadgesThread implements Runnable {

	protected int errorType = 1;
	protected final int noError = 1;
	protected final int notGotData = 2;
	protected final int noNetwork = 3;
	
	private Context ctx;
	private String url;
	private BadgesResult result;
	private ArrayList<Badge> badgesList;
	
	public BadgesThread(Context ctx, int groupID) {
		this.ctx = ctx;
		url = UtilsConstants.DISPLAYER_TAG + Preferences.getUserId(ctx) + UtilsConstants.BADGE_URL_1 + groupID + UtilsConstants.BADGE_URL_2; 
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message ms){
			
			if (errorType == noError)
				result.onGetBadgesSuccess(badgesList);
			else if (errorType == notGotData)
				result.onGetBadgesFail();
			else if (errorType == noNetwork)
				result.onErrorConnection();
				
		}
	}; 
	
	@Override
	public void run() {
		if (Utils.isNetworkAvailable(ctx)) {
			try {
				
				Connect connect = new Connect();
				badgesList = (ArrayList<Badge>) connect.getBadges(url);
				
			} catch (IOException e) {
				errorType = notGotData;
				Logger.Error("BadgesThread", e.toString());
			}
		} else {
			errorType = noNetwork;
			Logger.Error("BadgesThread", "No network connection");
		}
		
		handler.sendEmptyMessage(0);
	}

	public void setResult(BadgesResult result) {
		this.result = result;
	}
	
	public static abstract class BadgesResult {
		public abstract void onGetBadgesSuccess(ArrayList<Badge> badgesList);
		public abstract void onGetBadgesFail();
		public abstract void onErrorConnection();
	}
}
