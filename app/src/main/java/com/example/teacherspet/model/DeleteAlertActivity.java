package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.teacherspet.control.AlertsActivity;

/**
 * Delete alerts from the database.
 */
public class DeleteAlertActivity extends BasicActivity {
	//Web page to connect to
    private static String url_delete_alert = "https://morning-castle-9006.herokuapp.com/delete_alert.php";
    //Id of alert
	String aid;
    //Data to pass to web page
    String [] itemNames;
    String [] itemValues;

    /**
     * Store alerts that need to be deleted and searched database for those alerts.
     *
     * @param savedInstanceState Most recently supplied data.
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		aid = intent.getStringExtra("aid");
        Log.d("AID:",aid);
		startSearch();
	}

    /**
     * Gathers data needed in order to delete alerts and sends to database.
     */
    private void startSearch(){
        String[] itemNames = new String[]{"aid"};
        String [] itemValues = new String[]{aid};

        sendData("", itemNames, itemValues, url_delete_alert, this, false);
    }
	
	/**
	 * Checks to see if the alerts were deleted successfully.
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
	    		Toast.makeText(getApplicationContext(), "Alert(s) Deleted", Toast.LENGTH_SHORT).show();
                super.start(this, AlertsActivity.class, true);
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No Alert(s) Deleted", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, AlertsActivity.class, true);
	}
}
