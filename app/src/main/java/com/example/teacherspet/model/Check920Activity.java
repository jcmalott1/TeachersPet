package com.example.teacherspet.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Check920Activity extends Activity {
	//Id for intent
    private static int REQUEST_CODE = 1;
    //Data to pass to website
  	String[] dataPassed;
  	//Data collected from web page
  	String[] dataNeeded;
  	//Index for '920' number
  	private final int ACCOUNT_NUMBER = 2;
  	//Holds given '920' number
  	private String accountnumber;
	
  	//URL to list users from database
  	private static final String url_list_users = "https://morning-castle-9006.herokuapp.com/create_user.php";
  	
  	/**
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		accountnumber = intent.getStringExtra("920");
	}
	
	/**
	 * This method checks to see if the given '920' number is already in the database, if there is a '920' number that already
	 * exists that is the same, it will prompt the user for another valid '920' number.
	 * 
	 * @param num the given '920' number
	 * @return boolean which determines if it exists already
	 */
	public void get920(int num) {
		String tag = "users";
		dataPassed = new String[]{};
		dataNeeded = new String[]{"id","name","accountnumber"};
		Intent i = new Intent(Check920Activity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_users);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Receives data from database and finds if it matches the data given.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		// Holds boolean value, whether '920' is found or not
    	Boolean passed = false;
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Number is users sent back
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	for(int i = 0; i < count; i++){
	    		 //A single user
	    		 String[] item = data.getStringArrayExtra("item" + i);
	    		 if(accountnumber.equals(item[ACCOUNT_NUMBER])){
	    			 passed = true;
	    			 //Exit out of loop when found
	    			 break;
	    		 }
	    	}
	    	Intent intent = new Intent();
	    	intent.putExtra("passed", passed);
	    	setResult(REQUEST_CODE, intent);
	    }
	}
}

