package com.example.teacherspet.control;

import com.example.teacherspet.R;
import com.example.teacherspet.view.AttendanceActivity;
import com.example.teacherspet.view.GradesActivity;
import com.example.teacherspet.view.InformationActivity;
import com.example.teacherspet.view.LabPActivity;
import com.example.teacherspet.view.LabSActivity;
import com.example.teacherspet.view.SelectedGradesActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Back end for user interaction for Course Screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class CourseActivity extends Activity {
	//To access and modify preference data
	SharedPreferences sharedPref;
	//Data that was stored
	String pref;

	/**
	 * When screen is created set to course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_11_course);
		
		//Access a preference file and require that only this application access it
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		//Get string that is stored under status
		pref = sharedPref.getString("status",null);
	}

	/**
	 * Find which view is being pressed and change to that screen. 
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		//Action to change screens.
		Intent intent;
		//Screen that will be changed to.
		Class<?> toScreen;
		
		switch(view.getId()){
		    case R.id.btn_attendance:
		    	toScreen = AttendanceActivity.class;
		    	break;
		    case R.id.btn_assignments:
		    	toScreen = AssignmentsActivity.class;
		    	break;
		    case R.id.btn_students:
		    	toScreen = StudentsActivity.class;
		    	break;
		    /**Not a must, waiting till later to implement
		    case R.id.btn_plan:
		    	toScreen = CourseActivity.class;
		    	break;*/
		    case R.id.btn_grades:
		    	//Screen is different depending if user is student or professor
		    	if(pref.equals("student"))
		    	    toScreen = SelectedGradesActivity.class;
		    	else
		    		toScreen = GradesActivity.class;
		    	break;
		    case R.id.btn_lab:
		    	//Screen is different depending if user is student or professor
		    	if(pref.equals("student"))
		    	    toScreen = LabSActivity.class;
		    	else
		    		toScreen = LabPActivity.class;
		    	break;
		    case R.id.btn_info:
		    	toScreen = InformationActivity.class;
		    	break;
		    default:
		   		toScreen = HomeActivity.class;
		}
		
		 intent = new Intent(CourseActivity.this, toScreen);
		 startActivity(intent);
	}
}
