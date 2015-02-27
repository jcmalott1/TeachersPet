package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.ShowDetailActivity;

/**
 * Displays all students in the class to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 2/26/2014
 */
public class StudentsActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_users = "https://morning-castle-9006.herokuapp.com/find_users.php";

	/**
	 * Set layout to students activity then start search for student
     * information.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_15_students);
		startSearch();
	}

    /**
     * Sets up and send material needed to get data from database.
     *
     */
    private void startSearch(){
        String tag = "users";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name","email","type"};

        sendData(tag, dataPassed, dataNeeded, url_find_users, this, true);
    }

    /**
     * List all users in the class to the screen.
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
                ListView showUsers = (ListView) findViewById(R.id.users);

                int layout = R.layout.list_grade;
                int[] ids = new int[] {R.id.name,R.id.extra,R.id.extra2};
                showUsers.setAdapter(super.makeAdapter(data, dataNeeded, this, layout, ids));
                showUsers.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No users!!", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * When user is selected display that user information on different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String details = "Email: %Account: ";
        //Check if there account type is Student of Professor
        String type = "Professor";
        if(super.getNameorExtra(position, "type").equals("s")){
            type = "Student";
        }

        //Data has to be split by a % to be parsed
        String extra = super.getNameorExtra(position, "email") +"%"+ type;
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra("Name" , super.getNameorExtra(position, "name"));
        i.putExtra("Extra" , extra);
        i.putExtra("Details" , details);
        startActivity(i);
    }
}
