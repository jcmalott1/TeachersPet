package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.InformationActivity;
import com.example.teacherspet.view.LabActivity;

/**
 * Back end for user interaction for Course Screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class CourseActivity extends BasicActivity {
     String accountType;
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

		//Get the type of account user has
		accountType = super.getType();
        Log.d("Type", accountType);
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
        //
        Boolean typeP = accountType.equals("p");
		
		switch(view.getId()){
		    case R.id.btn_attendance:
                if(typeP)
		    	    toScreen = AttendancePActivity.class;
                else{
                    toScreen = AttendanceSActivity.class;
                }
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
		    	if(typeP) {
                    //Log.d("GRADES: ", "SENDING");
                    toScreen = GradesPActivity.class;
                }
		    	else
		    		toScreen = GradesSActivity.class;
		    	break;
		    case R.id.btn_lab:
		    	//Screen is different depending if user is student or professor
		        toScreen = LabActivity.class;
		    	break;
		    case R.id.btn_info:
		    	toScreen = InformationActivity.class;
		    	break;
		    default:
		   		toScreen = HomeActivity.class;
		}
		
		 intent = new Intent(this, toScreen);
		 startActivity(intent);
	}
}
