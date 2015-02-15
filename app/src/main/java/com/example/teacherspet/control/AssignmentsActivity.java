package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;


/**
 * Back end for user Interaction for Assignment Screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class AssignmentsActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Holds assignments to be displayed
    //private String[] assignments;
    ArrayList<HashMap<String, String>> homeworkList;
    //Get user ID
    private final int PID = 1;
    //Access preference data
    SharedPreferences sharedPref;
    //User Id that was preference
    String sid;
    //For adding view to screen
    //private PetAdapter assignArrayAdapter;
    //Data to pass to web page
    String[] dataPassed;
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_assignments = "https://morning-castle-9006.herokuapp.com/find_assignments.php";
    //Id for intent
    private static int REQUEST_CODE = 0;


    /**
     * When screen is created set to assignment layout.
     * Add assignment ListView to screen.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_19_assignments);
        startSearch();
    }

    /**
     * When view is pressed get title of that view and open new screen with
     * that information as title.
     *
     * @param l ListView where clicked happen.
     * @param view The view that was clicked.
     * @param position Location of view in the list.
     * @param id Row is of item clicked.
     * @Override
     *
     @Override public void onListItemClick(ListView l, View view, int position,
     long id) {

     //Change from this screen to Selected Item screen
     Intent i = new Intent(this,SelectedItem.class);
     String text = (String) ((TextView) view).getText();

     //Put the text from the view as an extra
     i.putExtra("text", text);

     //Start the activity
     this.startActivity(i);
     }*/

    /**
     * Sends items to model to access the database and get data that is needed.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "assignments";
        dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"graden", "dd", "da", "gradet", "dscript", "aid"};

        sendData(tag, dataPassed, dataNeeded, url_find_assignments, this, true);
    }

    /**
     * Called when Intent has returned from startActivityForResult
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
                String item = data.getStringExtra("item0");
                ListView assignments = (ListView) findViewById(R.id.assignments);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.itemList};

                assignments.setAdapter(super.makeAdapterArray(data, dataNeeded, this, layout, ids));
                assignments.setOnItemClickListener(this);
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
        //super.changeATTNColor(view, position, "graden");
    }
}
