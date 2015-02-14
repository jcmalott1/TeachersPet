package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Show attendance for a single student.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AttendanceSActivity extends BasicActivity {
	String[] dataNeeded;
	//Web page to connect to
	private static String url_list_student = "https://morning-castle-9006.herokuapp.com/find_student.php";

	/**
	 * When screen is created set to attendance layout.
	 * Then search for student attendance information.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_13s_attendance);
		startSearch();
	}
	
	/**
	 * Send student info to get attendance data.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "student";
		String[] dataPassed = new String[]{"courseID", super.getCourseID(), "sid", super.getID()};
        Log.d("CourseID: ", super.getCourseID());
        Log.d("ID: ", super.getID());
		dataNeeded = new String[]{"present","late","t_status","total_days","pname","course","date","status"};

        super.sendData(tag, dataPassed, dataNeeded, url_list_student, this, true);
	}
	
	/**
	 * Set content of page to information that was returned.
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
            int success = data.getIntExtra("success", -1);
            if(success == 0){
                String[] item = data.getStringArrayExtra("item0");
                getATTNPage(item);
            }
	    }
	}

    /**
     * Takes information from database and displays it onto the screen.
     *
     * @param item Information from database
     */
    private void getATTNPage(String[] item){
        //magic numbers
        final int STATUS = 7;
        final int DATE = 6;
        final int COURSE= 5;
        final int PROFESSOR_NAME= 4;
        final int TOTAL_DAYS= 3;
        final int TODAY_STATUS= 2;
        final int LATE= 1;
        final int PRESENT= 0;

        //Get todays date in the right format
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //Take off brackets of string array from database
        String today = item[DATE].replaceAll("\\{|\\}","");
        String storeStatus = item[STATUS].replaceAll("\\{|\\}","");

        //If more than one day is record, then get all days and there status
        String[] dates = today.split(",");
        String[] statuss = storeStatus.split(",");

        //Turn database Strings into Integers
        int total = Integer.parseInt(item[TOTAL_DAYS]);
        int present = Integer.parseInt(item[PRESENT]);
        int late = Integer.parseInt(item[LATE]);

        //Check if attendance is already taken and get status for the day
        String taken, status;
        if(total != 0 && dates[(total - 1)].equals(date.format(new Date()))){
            taken = "Taken";
            status = statuss[(total - 1)];
        }else{
            status = item[TODAY_STATUS];
            taken = "Not Taken";
        }

        //Find out what each letter means from database
        if(status.equals("p")){
            status = "Present";
        }else if(status.equals("l")){
            status = "Late";
        }else if(status.equals("t")){
            status = "Tardy";
        }else{
            status = "Not Taken";
        }
        //Log.d("Date: ", dates[total - 1]);
        //Log.d("Date: ", "" + total);

        //Make message to display on screen
        String message = "Professor: \t" + item[PROFESSOR_NAME] +
                "\n\nCurrent Status: 	" + status +
                "\n\nAttandance Taken: \t" + taken +
                "\n\nDays Present: \t" + present +
                "\n\nDays Late: \t" + late +
                "\n\nDays Tardy: \t" + (total - (present + late)) +
                "\n\nDays Total: \t" + total;

        //display on screen
        ((TextView) findViewById(R.id.attnTitle)).setText(item[COURSE]);
        ((TextView) findViewById(R.id.attnView)).setText(message);
    }
}
