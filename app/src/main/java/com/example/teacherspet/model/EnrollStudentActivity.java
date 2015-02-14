package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Delete alerts from the database.
 */
public class EnrollStudentActivity extends BasicActivity {
	//Web page to connect to
    private static String url_enroll_student = "https://morning-castle-9006.herokuapp.com/enroll_student.php";
    //Id of alert
	String sid;
    String cid;
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
		sid = intent.getStringExtra("sid");
        cid = intent.getStringExtra("cid");
        aid = intent.getStringExtra("aid");
        Log.d("aid:", aid);
		startSearch();
	}

    /**
     * Gathers data needed in order to delete alerts and sends to database.
     */
    private void startSearch(){
        String[] itemNames = new String[]{"sid","cid"};
        String [] itemValues = new String[]{sid,cid};

        sendData("", itemNames, itemValues, url_enroll_student, this, false);
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
	    		Toast.makeText(getApplicationContext(), "Student Added", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EnrollStudentActivity.this, DeleteAlertActivity.class);
                i.putExtra("aid", aid);
                startActivity(i);
                finish();
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No Student Added", Toast.LENGTH_SHORT).show();
                finish();
	    	}
	    }
	}
}
