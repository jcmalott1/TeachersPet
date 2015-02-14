package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Finds courses that user in enrolled in and displays them to the screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class CoursesActivity extends BasicActivity {
    //Magic number to find the correct item.
    final int COURSE = 0;
    final int COURSEID = 1;
    String courseID;
	//String pref;
	String id;
	//Web page to connect to
	private static String url_list_courses = "https://morning-castle-9006.herokuapp.com/list_courses.php";
	ArrayList<HashMap<String, String>> courseList;
    
	/**
	 * Sets screen to courses layout, finds user ID, and start searching for
     * user enrolled courses..
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//User ID number
		id = super.getID();
		
		setContentView(R.layout.activity_6_courses);
		courseList = new ArrayList<HashMap<String, String>>();
		
		startSearch();
	}

    /**
     * Send data to database that it needs to retrieve information.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "courseNumber";
        String [] dataPassed = new String[]{"ID",id};
        String [] dataNeeded = new String[]{"course","courseID"};

        super.sendData(tag, dataPassed, dataNeeded, url_list_courses, this, true);
    }
	
	/**
	 * Takes courses that user is enrolled in and displays them on the screen.
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
            //0 means successful
	    	if(success == 0){
                //Find all buttons that are hidden on screen
	    		Button [] buttons = {(Button) findViewById(R.id.btn_course0), (Button) findViewById(R.id.btn_course1),
                        (Button) findViewById(R.id.btn_course2), (Button) findViewById(R.id.btn_course3)};
                //number of courses found
	    		int count = Integer.parseInt(data.getStringExtra("count"));
	    		
	    		for(int i = 0; i < count; i++){
	    	        String[] item = data.getStringArrayExtra("item" + i);
	    	        //Log.d("COURSE: ", item[0]);
	    	        //Button cButton = (Button) findViewById(R.id.btn_course0);
	    	        buttons[i].setText(item[COURSE]);
                    buttons[i].setTag(item[COURSEID]);
	    	        buttons[i].setVisibility(View.VISIBLE);

                    //if class selected take to next screen
	    	        buttons[i].setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
                            //Log.d("COURSEID", (String) v.getTag());
                            //From basic activity
                            makePref();
                            setCourseID((String) v.getTag());
							start(CoursesActivity.this, CourseActivity.class, false);
						}
	    	        });
	    		}
	    	}else{
                //User is enrolled in no classes at this time
                Toast.makeText(this, "No Class Enrollment", Toast.LENGTH_SHORT).show();
            }
	     }
	}

    /**
     * If add course button is hit find out what account user is and go
     * to add course screen for that type.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        //Default screen to change to.
        Class<?> toScreen = HomeActivity.class;
        if(R.id.btn_addCourse == view.getId()){
            //Type of account it is
            String type = super.getType();
            if(type.equals("s"))
                toScreen = AddCourseSActivity.class;
            else if(type.equals("p")){
                toScreen = AddCoursePActivity.class;
            }
        }
        super.start(this, toScreen, false);
    }
}
