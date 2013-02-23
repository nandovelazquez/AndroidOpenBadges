/**
 * Create a dialog.
 */
package com.text.notifications;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class SimpleDialog extends Dialog {

	protected AlertDialog.Builder builder;
	protected AlertDialog alert;
	
	/**
	 * Create a new ErrorDialog.
	 * @param ctx - The context.
	 * @Note - This dialog has cancelable true by default.
	 */
	public SimpleDialog(Context ctx) {
		super(ctx);
		builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
	}
	
	/**
	 * Create a new ErrorDialog.
	 * @param ctx - The Context.
	 * @param cancelable - Specify if the dialog can be canceled.
	 */
	public SimpleDialog(Context ctx, boolean cancelable) {
		super(ctx);
		builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(cancelable);
	}
	
	/**
	 * Set the message to display.
	 * @param message - The message.
	 */
	public void setMessage(String message) {
		builder.setMessage(message);
	}
	
	/**
	 * Set Up the Ok Button.
	 * @param caption - The caption to display.
	 * @param listener - The listener to run when is clicked.
	 */
	public void setOkButton(String caption, DialogInterface.OnClickListener listener){
		builder.setPositiveButton(caption, listener);
	}

	/**
	 * Set Up the Cancel Button.
	 * @param caption - The caption to display.
	 * @param listener - The listener to run when is clicked.
	 */
	public void setCancelButton(String caption, DialogInterface.OnClickListener listener){
		builder.setNegativeButton(caption, listener);
	}
	
	/**
	 * Show the dialog.
	 */
	public void show(){
		alert = builder.create();
		alert.show();
	}
	

}
