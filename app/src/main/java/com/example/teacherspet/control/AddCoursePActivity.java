package com.example.teacherspet.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.PostItemActivity;

/**
 * Back end for user interaction for professor Add Course Screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AddCoursePActivity extends Activity {
	//URL to add table
	private static final String url_create_course = "https://morning-castle-9006.herokuapp.com/create_course.php";
	//Placing info in database
	PostItemActivity postItem;
	//Id so intent knows who is calling it
    private static int REQUEST_CODE = 0;
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
	 * Get Values and set to model to post to database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		itemNames = new String[]{"pid","pname","course","section","term","time"};
		itemValues = new String[itemNames.length];
		loadValues();
		
		Intent i = new Intent(AddCoursePActivity.this, PostItemActivity.class);
		i.putExtra("itemNames", itemNames);
		i.putExtra("itemValues", itemValues);
		i.putExtra("url", url_create_course);
		startActivityForResult(i, REQUEST_CODE);
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
	    	if(success == 1){
	    		Toast.makeText(getApplicationContext(), "Course created", Toast.LENGTH_SHORT).show();
	    	}else if(success == 0){
	    		Toast.makeText(getApplicationContext(), "No course created", Toast.LENGTH_SHORT).show();
	    	}
	    }
	    Intent i = new Intent(AddCoursePActivity.this, LoginActivity.class);
	    startActivity(i);
	    finish();
	}
	
	/**
	 * Fill out values with what user entered.
	 */
	private void loadValues(){
		EditText field;
		//get all values that the user entered
		int[] ids = new int[]{R.id.pid,R.id.pname,R.id.course,R.id.section,R.id.term,R.id.time};
		for(int j = 0; j < itemNames.length; j++){
			field = (EditText) findViewById(ids[j]);
			itemValues[j] = field.getText().toString();
		}
	}
}
