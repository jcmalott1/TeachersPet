package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.teacherspet.control.CoursesActivity;

import java.util.ArrayList;

/**
 * Adds an alerts to the professor of course that was submitted of a students wanting to enroll.
 */
public class StudentAlertActivity extends BasicActivity {
	//Web page to connect to
    private static String url_student_alert = "https://morning-castle-9006.herokuapp.com/create_student_alert.php";
	//ArrayList<String> viewIDs;
    //Holds student ID
    ArrayList<String> viewIDs;
    //String studentID;
    //Data to pass to web page
  	String[] itemNames;
  	//Data collecting from web page
  	String[] itemValues;

    /**
     * Get data that was passed to this intent and stores it.
     *
     * @param savedInstanceState Most recently supplied data
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		viewIDs = intent.getStringArrayListExtra("viewIDs");
        Log.d("COUNT:", "" + viewIDs.size());
		startSearch();
	}

    /**
     * Send data to database to receive some information.
     */
    private void startSearch(){
        itemNames = new String[viewIDs.size() + 2];
        itemValues = new String[viewIDs.size() + 2];
        loadValues();

        //Log.d("SID: ", itemValues[0]);
        //Log.d("COUNT: ", itemValues[1]);
        super.sendData("", itemNames, itemValues, url_student_alert, this, false);
    }

    /**
     * Loads values database is looking for.
     */
    private void loadValues(){
        //Set user's id
        itemNames[0] = "studentID";
        itemValues[0] = super.getID();
        itemNames[1] = "count";
        itemValues[1] = Integer.toString(viewIDs.size());
        //Set up all names and values
        for(int j = 2; j < itemNames.length; j++){
            itemNames[j] = "cid" + (j - 1);
            itemValues[j] = viewIDs.get((j - 2));
        }
    }
	
	/**
	 * Receives data from database and notifies user if course has been submitted .
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
	    	//Log.d("Alert Success", "" + success);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Course submitted", Toast.LENGTH_SHORT).show();
	    	}else {
	    		Toast.makeText(getApplicationContext(), "Course not submitted", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, CoursesActivity.class, true);
	}
}
