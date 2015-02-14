package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.model.RegisterATTN;
import com.example.teacherspet.model.SubmitATTN;

/**
 * List all user that are registered for current class.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AttendancePActivity extends BasicActivity implements AdapterView.OnItemClickListener{
	//Data collecting from web page
	String[] dataNeeded;
	//Web page to connect to
	private static String url_find_students = "https://morning-castle-9006.herokuapp.com/find_students.php";

	/**
	 * Set layout to attendance and look for students.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_13p_attendance);
		startSearch();
	}
	
	/**
	 * Send data to database to get back students' name and id.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "students";
        //Log.d("CourseID: ", super.getCourseID());
		String[] dataPassed = new String[]{"courseID", super.getCourseID(),"pid", super.getID()};
		dataNeeded = new String[]{"studentName","studentID","status"};

        sendData(tag, dataPassed, dataNeeded, url_find_students, this, true);
	}
	
	/**
	 * List all students in the class to the screen.
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
            int success = data.getIntExtra("success",-1);
            if(success == 0){
                super.clearViewID();
                ListView attendance = (ListView) findViewById(R.id.attnView);

                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.itemList};
                attendance.setAdapter(super.makeAdapter(data, dataNeeded, this, layout, ids));
                attendance.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No students!!", Toast.LENGTH_SHORT);
            }
        }
	}

    /**
     * When student is selected change background color and add id to the list.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //Log.d("VIEW 2: ", "" + view.getId());
        super.changeATTNColor(view, position, "studentID");
    }

    /**
     * Submit all the students that are present for that day.
     *
     * @param view Button that was clicked.
     */
    public void onClicked(View view){
        int viewID = view.getId();

        if(viewID == R.id.bnt_register){
            takeRegister(true);
        } else if(viewID == R.id.bnt_submit){
            Thread timer = new Thread() {
                public void run(){
                    try {
                        takeRegister(false);
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            timer.start();
            super.start(this, SubmitATTN.class, true);
        }
    }

    /**
     * Take register if any IDs where given.
     *
     * @param finish End activity when this is over.
     */
    private void takeRegister(Boolean finish){
        Intent i = new Intent(this, RegisterATTN.class);
        i.putExtra("greenIDs", super.getViewID("green"));
        i.putExtra("redIDs", super.getViewID("red"));
        startActivity(i);
        if(finish){
            finish();
        }
    }
}
