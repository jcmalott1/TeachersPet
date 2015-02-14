package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.R;

/**
 * Back end for user interaction for Home Screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class HomeActivity extends BasicActivity {

	/**
	 * When screen is created set to professor add course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_4_home);
	}
	
	/**
	 * Find view being pressed and changed to that screen. 
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		//Action to change screens
		Intent intent;
		//Screen to change to
		Class<?> toScreen;
		
		switch(view.getId()){
		    case R.id.btn_alerts:
		    	toScreen = AlertsActivity.class;
		    	break;
		    case R.id.btn_courses:
		    	toScreen = CoursesActivity.class;
		    	break;
		    case R.id.btn_schedule:
		    	toScreen = ScheduleActivity.class;
		    	break;
		    	/**Not a must waiting till later to implement
		    case R.id.btn_settings:
		    	toScreen = SettingsActivity.class;
		    	break;*/
		    default:
		   		toScreen = HomeActivity.class;
		}

        super.start(this, toScreen, false);
	}
}
