package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Takes attendance for the day.
 *
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class SubmitATTN extends BasicActivity{
    //Url to get data from
    private static String url_attendance = "https://morning-castle-9006.herokuapp.com/submit_attendance.php";

    /**
     * Gets the ids of students who are present and place them on rooster.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Nothing needs to be pass or given back
        String[] empty = new String[0];
        Log.d("SEND SUBMIT: ", "GO");
        super.sendData("", empty, empty, url_attendance, this, false);
    }

    /**
     * Creates a list of all alerts that the user has in the database.
     *
     * @param requestCode Number that was assigned to the intent being called.
     * @param resultCode  RESULT_OK if successful, RESULT_CANCELED if failed
     * @param data        Intent that was just exited.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        //Check request that this is response to
        if (requestCode == 0) {
            int success = data.getIntExtra("success", -1);
            if (success == 0) {
                Toast.makeText(this,"Attendance Taken",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Attendance Not taken",Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
