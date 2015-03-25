package com.example.teacherspet.control;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;

/**
 * Displays a user schedule.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2014
 */
public class ScheduleActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_schedule = "https://morning-castle-9006.herokuapp.com/find_schedule.php";

    /**
     * Set layout to attendance and look for students.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_schedule);

        //startSearch();
	}

    /**
     * Send data to database to get back students' name and id.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "schedule";
        //Log.d("CourseID: ", super.getCourseID());
        String[] dataPassed = new String[]{"uid", super.getID()};
        dataNeeded = new String[]{"time","course","day","dd","da","name"};

        sendData(tag, dataPassed, dataNeeded, url_find_schedule, this, true);
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
                ListView attendance = (ListView) findViewById(R.id.attnView);

                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
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
        super.changeColor(view, position, "studentID", false);
    }
}
