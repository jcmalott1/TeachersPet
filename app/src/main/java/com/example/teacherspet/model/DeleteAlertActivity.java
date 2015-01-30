package com.example.teacherspet.model;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.teacherspet.control.HomeActivity;

public class DeleteAlertActivity extends Activity{
	//Web page to connect to
    private static String url_delete_alert = "https://morning-castle-9006.herokuapp.com/delete_sAlert.php";
    //Id for intent
  	private static int REQUEST_CODE = 0;
	ArrayList<String> viewIDs;
    //Data to pass to web page
  	String[] itemNames;
  	//Data collecting from web page
  	String[] itemValues;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		viewIDs = intent.getStringArrayListExtra("viewIDs");
		startSearch();
	}
	
	/**
	 * Receives data from model that was received from the database.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Tells if items where added or not. 1 mean successful
	    	int success = data.getIntExtra("success", -1);
	    	Log.d("Alert Success", "" + success);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Alert submitted", Toast.LENGTH_SHORT).show();
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No Alert deleted", Toast.LENGTH_SHORT).show();
	    	}
	    }
	    Intent i = new Intent(DeleteAlertActivity.this, HomeActivity.class);
	    startActivity(i);
	    finish();
	}
	
	/**
	 * Gives all data to models to find in database.
	 */
	private void startSearch(){
		itemNames = new String[viewIDs.size() + 1];
		itemValues = new String[viewIDs.size() + 1];
		loadValues();
				
		Intent i = new Intent(DeleteAlertActivity.this, PostItemActivity.class);
		i.putExtra("itemNames", itemNames);
		i.putExtra("itemValues", itemValues);
		i.putExtra("url", url_delete_alert);
		//starts a activity but knows it will be returning something
		Log.d("STARTING SEARCH", "ITEMS SENT");
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Fill out values with what user entered.
	 */
	private void loadValues(){
		//Set user's id
		itemNames[0] = "count";
		itemValues[0] = Integer.toString(viewIDs.size());
		//Set up all names and values
		for(int j = 1; j < itemNames.length; j++){
            itemNames[j] = "pid" + (j);
			itemValues[j] = viewIDs.get((j - 1));
		}
	}
}
