package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Allows professor to submit attendance.
 *
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class RegisterATTN extends BasicActivity{
    //Ids of students in course who are present.
    ArrayList<String> presentIDs;
    ArrayList<String> lateIDs;
    //Url to get data from
    private static String url_attendance = "https://morning-castle-9006.herokuapp.com/register_attendance.php";

    /**
     * Gets the ids of students who are present and place them on rooster.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        presentIDs = intent.getStringArrayListExtra("greenIDs");
        lateIDs = intent.getStringArrayListExtra("redIDs");

        startSearch();
    }

    /**
     * Send search data to the database.
     */
    private void startSearch() {
        //2 for the first two initial entries
        int lengthP = presentIDs.size();
        int total = lengthP + lateIDs.size();
        int start;
        int end;
        String [] itemNames = new String[total + 3];
        String [] itemValues = new String[total + 3];

        //Store data that needs to be sent
        itemNames[0] = "countP";
        itemValues[0] = "" + lengthP;
        itemNames[1] = "total";
        itemValues[1] = "" + total;
        itemNames[2] = "courseID";
        itemValues[2] = super.getCourseID();

        start = 3;
        end = lengthP + 3;
        for(int i = start; i < end; i++){
            itemNames[i] = "pid" + (i-start);
            itemValues[i] = presentIDs.get((i-start));
            Log.d("Green ID:", presentIDs.get((i-start)));
        }
        start = end;
        end = total + 3;
        for(int i = start; i < end; i++){
            itemNames[i] = "lid" + (i-start);
            itemValues[i] = lateIDs.get((i-start));
        }

        super.sendData("", itemNames, itemValues, url_attendance, this, false);
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
                Toast.makeText(this,"Register Taken",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Register Not taken",Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
