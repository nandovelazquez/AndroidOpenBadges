/**
 * Use this class to logs any message in the Logcat.
 */
package com.test.utils;

import android.util.Log;



public class Logger {

	/**
	 * Display an error in the logcat.
	 * @param tag - Tag to show.
	 * @param msg - The message to display.
	 */
	public static void Error(String tag, String msg) {
		Log.e(tag, msg);
	}
	
	/**
	 * Display an error in the logcat.
	 * @param tag - Tag to show.
	 * @param msg - The message to display.
	 */
	public static void Warning(String tag, String msg){
//		Log.w(tag, msg);
	}
	
}
