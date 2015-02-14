package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.R;

/**
 * Allows professor to add a course to database.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AddCoursePActivity extends BasicActivity {
	//URL to add table
	private static final String url_create_course = "https://morning-castle-9006.herokuapp.com/create_course.php";
    //Name of field in database
    String[] itemNames;
    //Values to place into those fields
    String[] itemValues;

	/**
	 * When screen is created set to professor add course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_9p_add_course);
	}
	
	/**
	 * Get values for course and send to database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		itemNames = new String[]{"id","cid","pname","course","term","time","section"};
		itemValues = new String[itemNames.length];
		loadValues();

        super.sendData("", itemNames, itemValues, url_create_course, this, false);
	}

    /**
     * FIll in data with what user entered.
     */
    private void loadValues(){
        //get all values that the user entered
        int[] ids = new int[]{R.id.cid,R.id.pname,R.id.course,R.id.term,R.id.time,R.id.section};
        //First entry is user id
        itemValues[0] = super.getID();
        for(int j = 1; j < itemNames.length; j++){
            itemValues[j] = ((EditText) findViewById(ids[(j - 1)])).getText().toString();
        }
    }

	/**
	 * Tells if course was added or not and warns user of the result.
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
	    	//0 means course was added
	    	int success = data.getIntExtra("success", -1);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Course created", Toast.LENGTH_SHORT).show();
	    	}else{
	    		Toast.makeText(getApplicationContext(), "No course created", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, AddCoursePActivity.class, true);
	}
}
