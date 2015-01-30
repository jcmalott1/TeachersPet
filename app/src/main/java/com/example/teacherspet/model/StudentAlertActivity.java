package com.example.teacherspet.model;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.teacherspet.control.CoursesActivity;

public class StudentAlertActivity extends Activity {
	//Web page to connect to
    private static String url_student_alert = "https://morning-castle-9006.herokuapp.com/create_student_alert.php";
    //Id for intent
  	private static int REQUEST_CODE = 0;
	ArrayList<String> viewIDs;
    //Holds users ID
    String thisID;
    //Data to pass to web page
  	String[] itemNames;
  	//Data collecting from web page
  	String[] itemValues;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		viewIDs = intent.getStringArrayListExtra("viewIDs");
		thisID = intent.getStringExtra("sID");
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
	    		Toast.makeText(getApplicationContext(), "Course submitted", Toast.LENGTH_SHORT).show();
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No course submitted", Toast.LENGTH_SHORT).show();
	    	}
	    }
	    Intent i = new Intent(StudentAlertActivity.this, CoursesActivity.class);
	    startActivity(i);
	    finish();
	}
	
	/**
	 * Gives all data to models to find in database.
	 */
	private void startSearch(){
		itemNames = new String[viewIDs.size() + 2];
		itemValues = new String[viewIDs.size() + 2];
		loadValues();
				
		Intent i = new Intent(StudentAlertActivity.this, PostItemActivity.class);
		i.putExtra("itemNames", itemNames);
		i.putExtra("itemValues", itemValues);
		i.putExtra("url", url_student_alert);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Fill out values with what user entered.
	 */
	private void loadValues(){
		//Set user's id
		itemNames[0] = "thisID";
		itemValues[0] = thisID;
		itemNames[1] = "count";
		itemValues[1] = Integer.toString(viewIDs.size());
		//Set up all names and values
		for(int j = 2; j < itemNames.length; j++){
            itemNames[j] = "pid" + (j - 1);
			itemValues[j] = viewIDs.get((j - 2));
		}
	}
}
