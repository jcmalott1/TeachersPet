package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;
import com.example.teacherspet.model.StudentAlertActivity;

/**
 * Back end for user Interaction for student to add courses
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AddCourseSActivity extends Activity {
	//Colors for view
	int green;
	int white;
	//Get cid user entered
	EditText txtcID;
	//Data to pass to web page
	String[] dataPassed;
	//Data collecting from web page
	String[] dataNeeded;
	//IDs for courses
	ArrayList<String> viewIDs;
	//Web page to connect to
	private static String url_list_course = "https://morning-castle-9006.herokuapp.com/list_course.php";
	//Id for intent
	private static int REQUEST_CODE = 0;
	ArrayList<HashMap<String, String>> courseList;

	/**
	 * When screen is create set to student add course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_10s_add_course);
		
		courseList = new ArrayList<HashMap<String, String>>();
	}
	
	/**
	 * When button is clicked, collect all the text data and send to model
	 * to get data from database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		switch(view.getId()){
		case R.id.btn_find:
		   startSearch();
		   break;
		case R.id.btn_submit:
			SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
			String sid = sharedPref.getString("id", null);
			
			Intent i = new Intent(AddCourseSActivity.this, StudentAlertActivity.class);
			i.putExtra("viewIDs", viewIDs);
			i.putExtra("sID", sid);
			startActivity(i);
			finish();
			break;
		}
	}
	
	/**
	 * Called when Intent has returned from startActivityForResult
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {;
		//Check request that this is response to
	    if (requestCode == 0) {
	    	viewIDs = new ArrayList<String>();
			
	        // updating listview
	    	ListView courseView = (ListView) findViewById(R.id.courseView);
	        courseView.setAdapter(makeAdapter(data));  
	        courseView.setOnItemClickListener(new OnItemClickListener(){
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                       long id) {
	                changeColor(view);
	              }
	             });
	     }
	}
	
	/**
	 * Makes  an adapter to attached to Edit Text View.
	 * 
	 * @param data Intent being returned
	 * @return Adapter being attached to View
	 */
	private ListAdapter makeAdapter(Intent data){
		//Number of items passed back
		int count = Integer.parseInt(data.getStringExtra("count"));
		
		for(int i = 0; i < count; i++){
		   //Get items that where returned
	   	   String[] items = data.getStringArrayExtra("item" + i);
	   	 
	   	   // creating new HashMap
	       HashMap<String, String> map = new HashMap<String, String>();
	       for(int j = 0; j < dataNeeded.length; j++){
	           map.put(dataNeeded[j], items[j]);
	       }
	   	   courseList.add(map);
		}
	   	 
	    /**
	     * Updating parsed JSON data into ListView
	     */
	    return new SimpleAdapter(
	            AddCourseSActivity.this, courseList,
	            R.layout.list_course, dataNeeded,
	            new int[] {R.id.pid, R.id.pname, R.id.course,
	                	   R.id.section, R.id.term, R.id.time});
	}
	
	/**
	 * Changes the color of the listview and stores the pid of the list view.
	 * 
	 * @param view View of list being pressed
	 */
	private void changeColor(View view){
		 // TODO Auto-generated method stub
		int green = getResources().getColor(android.R.color.holo_green_light);
    	int white = getResources().getColor(android.R.color.white);
        int color = Color.TRANSPARENT;
    	Drawable background = view.getBackground();
        if(background instanceof ColorDrawable)
 	       color = ((ColorDrawable) background).getColor();
    	        
    	if(color != green){
    		view.setBackgroundColor(green);
    		viewIDs.add(((TextView) findViewById(R.id.pid)).getText().toString());
    	}
    	else{
    	    view.setBackgroundColor(white);
    	    viewIDs.remove(((TextView) findViewById(R.id.pid)).getText().toString());
    	}
	}
	
	/**
	 * Gives all data to models to find in database.
	 */
	private void startSearch(){
		//Get pid
		txtcID = (EditText) findViewById(R.id.cID);
		String cid = txtcID.getText().toString();
		//Name of JSON tag storing data
		String tag = "courses";
		dataPassed = new String[]{"cid",cid};
		dataNeeded = new String[]{"cid","pname","course","section","term","time"};
				
		Intent i = new Intent(AddCourseSActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_course);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
	}
}
