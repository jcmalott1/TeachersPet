package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;

/**
 * Back end for user interaction for Courses Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class CoursesActivity extends Activity {
	//Button to add course
	Button btn_addCourse;
	//To access and modify preference data
	SharedPreferences sharedPref;
	//Data that was preference
	String pref;
	String id;
	//Number for the course (cs150)
	String courseNumber;
	//Data to pass to web page
    String[] dataPassed;
	//Data collecting from web page
	String[] dataNeeded;
	//Web page to connect to
	private static String url_list_courses = "https://morning-castle-9006.herokuapp.com/list_courses.php";
	//Id for intent
    private static int REQUEST_CODE = 0;
	ArrayList<HashMap<String, String>> courseList;
    
	/**
	 * When screen is created set to courses layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Access a preference file and require that only this application access it
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		//Get string that is stored under status
		pref = sharedPref.getString("status", null);
		id = sharedPref.getString("id", null);
		
		setContentView(R.layout.activity_6_courses);
		courseList = new ArrayList<HashMap<String, String>>();
		
		startSearch();
	}

	/**
	 * If course button clicked, go to course screen.
	 * If add course is clicked go to appropriate screen based on 
	 * user being a student or professor.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		//Action to hold screen change.
		Intent intent;
		//Default screen to change to.
		Class<?> toScreen = HomeActivity.class;
		
		switch(view.getId()){
		    //case R.id.btn_course:
		    	//toScreen = CourseActivity.class;
		    	//break;
		    case R.id.btn_addCourse:
		    	if(pref.equals("s"))
		    	  toScreen = AddCourseSActivity.class;
		    	else if(pref.equals("p")){
		    	  toScreen = AddCoursePActivity.class;
		    	}
		}
		
		 intent = new Intent(CoursesActivity.this, toScreen);
		 startActivity(intent);
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
	    	int success = data.getIntExtra("success", -1);
	    	if(success == 1){
	    		Button [] buttons = {(Button) findViewById(R.id.btn_course0), (Button) findViewById(R.id.btn_course1),
	    				(Button) findViewById(R.id.btn_course2), (Button) findViewById(R.id.btn_course3)};
	    		int count = Integer.parseInt(data.getStringExtra("count"));
	    		
	    		for(int i = 0; i < count; i++){
	    	        String[] item = data.getStringArrayExtra("item" + i);
	    	        Log.d("COURSE: ", item[0]);
	    	        //Button cButton = (Button) findViewById(R.id.btn_course0);
	    	        buttons[i].setText(item[0]);
	    	        buttons[i].setVisibility(1);
	    	        
	    	        buttons[i].setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(CoursesActivity.this, CourseActivity.class);
	    	       		    startActivity(intent);
						}
	    	        });
	    		}
	    	}
	     }
	}
	
	/**
	 * Gives all data to models to find in database.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "courseNumber";
		dataPassed = new String[]{"ID",id};
		dataNeeded = new String[]{"course"};
				
		Intent i = new Intent(CoursesActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_courses);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
	}
}
