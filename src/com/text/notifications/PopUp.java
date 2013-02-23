package com.text.notifications;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class PopUp extends Activity {

	//Properties.
	private String text;
	private Context ctx;
	
	//Constructors.
	public PopUp(){}
	
	/**
	 * Set the application context.
	 * @param ctx - the application context
	 */
	public PopUp(Context ctx){
		this.ctx = ctx;
	}
	
	
	//Methods.
	/**
	 * Set the context.
	 * @param ctx - The context.
	 */
	public void setContext(Context ctx){
		this.ctx = ctx;
	}
	
	/**
	 * Set text.
	 * @param text - The text to display.
	 */
	public void setText(String text){
		this.text = text;
	}
	
	/**
	 * Shows message.
	 */
	public void Show(){
		Toast.makeText(ctx,text,Toast.LENGTH_LONG).show();
	}
	/**
	 * Shows message.
	 * @param text - The text to display.
	 */
	public void show(String text){
		Toast.makeText(ctx,text,Toast.LENGTH_LONG).show();
	}
	
}//End showMessage.
