package com.example.teacherspet.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;

/**
 * User Interaction for Attendance Screen.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AttendanceActivity extends ListActivity {
	//private String[] assignments;
	ArrayList<HashMap<String,String>> studentList;
    //Access preference data
	SharedPreferences sharedPref;
	//User Id that was preference
	String type;
	String thisID;
	//Data to pass to web page
	String[] dataPassed;
	//Data collecting from web page
	String[] dataNeeded;
	//Web page to connect to
	private static String url_list_students = "https://morning-castle-9006.herokuapp.com/list_students.php";
	//Id for intent
	private static int REQUEST_CODE = 0;
	//For listing students on screen
	//private PetAdapter notLoggedAdapter;
	//Holds the names of students in class
	//String[] notLoggeds = new String[]{"John Smith","Jane Doe"};

	/**
	 * When screen is created set to attendance layout.
	 * List students as views on screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_13_attendance);
		studentList = new ArrayList<HashMap<String, String>>();
		findStudents();
	}
	
	/**
	 * Sends items to model to access the database and get data that is needed.
	 */
	private void findStudents(){
		//Get pid
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		thisID = sharedPref.getString("id", null);
		Log.d("", "" + thisID);
		//Name of JSON tag storing data
		String tag = "users";
		dataPassed = new String[]{};
		dataNeeded = new String[]{"sids"};
				
		Intent i = new Intent(AttendanceActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_students);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
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
	         Intent data) {
		//Check request that this is response to
	    if (requestCode == 0) {
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	for(int i = 0; i < count; i++){
	    	   String[] items = data.getStringArrayExtra("item" + i);
	    	   HashMap<String, String> map =new HashMap<String, String>();
	    	   for(int j = 0; j < dataNeeded.length; j++){
	    		   Log.d("Name: ", "" + dataNeeded[j]);
	    		   Log.d("Value: ", "" + items[j]);
	    		   map.put(dataNeeded[j], items[j]);
	    	   }
	    	   studentList.add(map);
	    	}
            /**
             * Updating parsed JSON data into ListView
             */
            ListAdapter adapter = new SimpleAdapter(
                    AttendanceActivity.this, studentList,
                    R.layout.list_student, dataNeeded,
                    new int[] {R.id.sname});
            // updating listview
            setListAdapter(adapter);  
	     }
	}
	
	/**
	 * When view is pressed change to grey to green.
	 * 
	 * @param l ListView where clicked happen.
	 * @param view The view that was clicked. 
	 * @param position Location of view in the list.
	 * @param id Row is of item clicked.
	 * @Override
	 */
	@Override
	public void onListItemClick(ListView l, View view, int position,
			long id) {
		
		//view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
	}
}
