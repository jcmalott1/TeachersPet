package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
/**
 * Find all Lab information for the course.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/1/2015
 */
public class LabActivity extends BasicActivity implements AdapterView.OnItemClickListener {
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_lab = "https://morning-castle-9006.herokuapp.com/find_lab.php";

	/**
	 * When screen is created set to lab layout.
	 * Sets up auto-complete feature on lab TextView
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_18_lab);

        startSearch();
	}

    /**
     * Send data to database to get back extra names and address.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "labs";
        //Log.d("CourseID: ", super.getCourseID());
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name","address","lid"};

        sendData(tag, dataPassed, dataNeeded, url_find_lab, this, true);
    }

    /**
     * List all extras in the course to the screen.
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
            int success = data.getIntExtra(AppCSTR.SUCCESS,-1);
            if(success == 0){
                ListView labs = (ListView) findViewById(R.id.lab);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};

                labs.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                labs.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No extras!!", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * When extra is selected send to web page.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String[] extra = super.getNameorExtra(position, "extra").split("%");
        String url = "http://" + extra[0];

        Intent i = new Intent(this, ShowLabActivity.class);
        i.putExtra("title", super.getNameorExtra(position, "name"));
        i.putExtra("url", url);
        i.putExtra("lid", extra[1]);
        startActivity(i);
    }
}
