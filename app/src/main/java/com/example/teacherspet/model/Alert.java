package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.teacherspet.R;

/**
 * List all detailed information about an alert to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class Alert extends BasicActivity{
    //Holds data from activity that passed it
    Intent intent;
    String courseName;

    /**
     * Sets to default alert page.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertpage);

        intent = getIntent();
        setScreen(intent.getStringExtra(AppCSTR.ALERT_NAME), intent.getStringExtra(AppCSTR.ALERT_ACTION));
    }

    /**
     * Sets the title and details to the page and adds a submit button if further action needs
     * to be taken.
     *
     * @param action Y mean further action is needed.
     * @param title Title of the page.
     */
    private void setScreen(String title, String action){
        ((TextView) findViewById(R.id.title)).setText(title);
        if(action.equals("Y")){
            findViewById(R.id.bnt_submit).setVisibility(View.VISIBLE);
        }

        String[] alertInfo = intent.getStringExtra(AppCSTR.ALERT_DESCRIPTION).split("%");
        String alertMessage = "";
        if(alertInfo.length > 1)
           courseName = alertInfo[1];

        for(int j = 0; j < alertInfo.length; j++){
            alertMessage += "\n\n" + alertInfo[j];
        }

        ((TextView) findViewById(R.id.descript)).setText(alertMessage);
    }

    /**
     * Handles deleting and adding students to courses.
     *
     * @param view View that has been clicked.
     */
    public void onClicked(View view){
        //Intent i;
        switch(view.getId()){
            case R.id.bnt_delete:
                Intent i = new Intent(this, DeleteAlertActivity.class);
                i.putExtra(AppCSTR.ALERT_AID, intent.getStringExtra(AppCSTR.ALERT_AID));
                startActivity(i);
                finish();
                break;
            case R.id.bnt_submit:
                Intent j = new Intent(this, EnrollStudentActivity.class);
                j.putExtra(AppCSTR.ALERT_SID, intent.getStringExtra(AppCSTR.ALERT_SID));
                j.putExtra(AppCSTR.ALERT_CID, intent.getStringExtra(AppCSTR.ALERT_CID));
                j.putExtra(AppCSTR.ALERT_AID, intent.getStringExtra(AppCSTR.ALERT_AID));
                j.putExtra(AppCSTR.COURSE_NAME, courseName);
                startActivity(j);
                finish();
                break;
            default:
        }
    }
}
