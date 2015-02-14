package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.Alert;
import com.example.teacherspet.model.BasicActivity;

/**
 * Handles warning the user of an update to the system that pertains to them.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AlertsActivity extends BasicActivity implements AdapterView.OnItemClickListener {
    //Data that was preference
    String thisID;
    //Data needed from database
    String[] dataNeeded;
    //Web page to connect to
    private static String url_alerts = "https://morning-castle-9006.herokuapp.com/alerts.php";

    /**
     * When screen is created set to alert layout, find user id, and start
     * looking for alerts.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_alerts);
        //Get string that is stored under id
        thisID = super.getID();
        //Log.d("ID: ", thisID);

        startSearch();
    }

    /**
     * Send search data to the database.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "alerts";
        String[] dataPassed = new String[]{"id", thisID};
        dataNeeded = new String[]{"alert","aid","action"};

        super.sendData(tag, dataPassed, dataNeeded, url_alerts, this, true);
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
        ;
        //Check request that this is response to
        if (requestCode == 0) {
            int success = data.getIntExtra("success", -1);
            if (success == 0) {
                super.clearViewID();
                ListView courseView = (ListView) findViewById(R.id.alertView);
                //Get and pass data to make list adapter
                int layout = R.layout.list_alert;
                int[] ids = new int[] {R.id.alert, R.id.aid, R.id.action};
                courseView.setAdapter(super.makeAdapter(data, dataNeeded, this, layout ,ids));
                courseView.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this,"No Alerts!!",Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * Finds view that user has selected and adds/removes that view from a listed of views already
     * selected.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent i = new Intent(this, Alert.class);
        i.putExtra("aid", ((TextView) findViewById(R.id.aid)).getText().toString());
        i.putExtra("action", ((TextView) findViewById(R.id.action)).getText().toString());
        i.putExtra("alert", ((TextView) findViewById(R.id.alert)).getText().toString());
        startActivity(i);
        finish();
    }
}
