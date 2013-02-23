/**
 * Sets the access to the application preferences.
 * @author: Fernando Velazquez B.
 */
package com.test.utils;

import android.content.Context;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {

	private final static String USER_ID = "user_id";
	private final static String USER_EMAIL = "user_email";
	private final static String SETTINGS_FILE_NAME = "preferences";

	public static String getUserEmail(Context ctx){
		return ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).getString(USER_EMAIL, null);
	}
	
	public static int getUserId(Context ctx){
		return ctx.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE).getInt(USER_ID, 0);
	}
	
	public static void setUserEmail(Context ctx, String id){
		ctx.getSharedPreferences(SETTINGS_FILE_NAME,Context.MODE_PRIVATE).edit().putString(USER_EMAIL, id).commit();
	}
	
	public static void setUserId(Context ctx, int id){
		ctx.getSharedPreferences(SETTINGS_FILE_NAME,Context.MODE_PRIVATE).edit().putInt(USER_ID, id).commit();
	}
	
}
