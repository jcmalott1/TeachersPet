package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.ShowDetailActivity;


/**
 * Find all data on user assignments.
 *
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class AssignmentsActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_assignments = "https://morning-castle-9006.herokuapp.com/find_assignments.php";


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
        if(getType().equals("p")){
            ((Button)findViewById(R.id.bnt_assignment)).setVisibility(View.VISIBLE);
        }
        startSearch();
    }



    /**
     * Sends items to model to access the database and get data that is needed.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "assignments";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"names", "dd", "da", "gradet", "dscript"};

        sendData(tag, dataPassed, dataNeeded, url_find_assignments, this, true);
    }

    /**
     * Sets the name of the assignment to be displayed in list view.
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
                ListView assignments = (ListView) findViewById(R.id.assignments);
                int layout = R.layout.list_grade;
                int[] ids = new int[] {R.id.name, R.id.extra};

                assignments.setAdapter(super.makeAdapterArray(data, true, this, layout, ids));
                assignments.setOnItemClickListener(this);
            }
        }
    }

    /**
     * Send all data to show on a different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String details = "Due Date: %Date Assigned: %Grade Total: %Description: ";
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra("Name", super.getNameorExtra(position, "name"));
        i.putExtra("Extra", super.getNameorExtra(position, "extra"));
        i.putExtra("Details", details);
        startActivity(i);
    }

    /**
     * Get values for course and send to database.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        super.start(this, AddAssignmentActivity.class,false);
    }
}
