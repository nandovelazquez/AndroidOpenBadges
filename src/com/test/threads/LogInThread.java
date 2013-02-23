package com.test.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.test.models.User;
import com.test.net.Connect;
import com.test.utils.UtilsConstants;
import com.test.utils.Logger;
import com.test.utils.Utils;


public class LogInThread implements Runnable {

	protected int errorType = 1;
	protected final int noError = 1;
	protected final int notGotData = 2;
	protected final int noNetwork = 3;
	
	private String url = UtilsConstants.LOGIN_URL;
	private LogInResult result;
	private Context ctx;
	private String email;
	private User user;
	
	public LogInThread(Context ctx, String email) {
		this.ctx = ctx;
		this.email = email;
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message ms){
			
			if (errorType == noError)
				result.onLogInSuccess(user);
			else if (errorType == notGotData)
				result.onLogInFail();
			else if (errorType == noNetwork)
				result.onErrorConnection();
				
		}
	}; 
	
	@Override
	public void run() {
		
		if (Utils.isNetworkAvailable(ctx)) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add( new BasicNameValuePair("email", email));
				
				Connect connect = new Connect();
				user = connect.logIn(url, params);
			} catch (IOException e) {
				errorType = notGotData;
				Logger.Error("LogInThread", e.toString());
			}
		} else {
			errorType = noNetwork;
			Logger.Error("LogInThread", "No network connection");
		}
		
		handler.sendEmptyMessage(0);
	}
	
	public void setResult(LogInResult result) {
		this.result = result;
	}

	
	public static abstract class LogInResult {
		public abstract void onLogInSuccess(User user);
		public abstract void onLogInFail();
		public abstract void onErrorConnection();
	}
}
