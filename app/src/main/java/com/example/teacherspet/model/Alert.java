package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teacherspet.R;

/**
 * Lsyout for the Alert Page. Displays information about alert.
 *
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class Alert extends BasicActivity{
    //Holds details about alert
    final int DESCRIPTION = 2;
    final int SENDERNAME = 0;
    final int SENDERID = 1;
    final int COURSEID = 3;
    //HIDs for sender, course, and alert
    String aid;
    String sid;
    String cid;
    //Url to get data from
    private static String url_alert = "https://morning-castle-9006.herokuapp.com/alert.php";

    /**
     * Sets to default alert page.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertpage);
        Intent intent = getIntent();
        aid = intent.getStringExtra("aid");
        setScreen(intent.getStringExtra("alert"), intent.getStringExtra("action"));
        startSearch();
    }

    /**
     * Sets the title of the page and adds a submit button if further action needs
     * to ne taken.
     *
     * @param action Y mean further action is needed.
     * @param title Title of the page.
     */
    private void setScreen(String title, String action){
        ((TextView) findViewById(R.id.title)).setText(title);
        if(action.equals("Y")){
            ((Button) findViewById(R.id.bnt_submit)).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Send search data to the database.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "alert";
        String[] dataPassed = new String[]{"aid", aid};
        String[] dataNeeded = new String[]{"name","sid","descript","cid"};

        super.sendData(tag, dataPassed, dataNeeded, url_alert, this, true);
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
                String[] item = data.getStringArrayExtra("item0");
                sid = item[SENDERID];
                cid = item[COURSEID];
                String[] alertInfo = item[DESCRIPTION].split("%");
                String alertMessage = "Sender: " + item[SENDERNAME];

                for(int i = 0; i < alertInfo.length; i++){
                    alertMessage += "\n\n" + alertInfo[i];
                }

                ((TextView) findViewById(R.id.descript)).setText(alertMessage);
            } else {
                //Do nothing, user will see no alerts in his box.
            }
        }
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
                i.putExtra("aid", aid);
                startActivity(i);
                finish();
                break;
            case R.id.bnt_submit:
                Intent j = new Intent(this, EnrollStudentActivity.class);
                j.putExtra("sid", sid);
                j.putExtra("cid", cid);
                j.putExtra("aid", aid);
                startActivity(j);
                finish();
                break;
            default:
        }
    }
}
