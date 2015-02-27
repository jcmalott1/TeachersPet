package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.ShowGradesActivity;

/**
 * User interaction for Grades screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 2/22/2015
 */
public class GradesPActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Web page to connect to
    private static String url_find_grades = "https://morning-castle-9006.herokuapp.com/find_graded.php";
    String[] dataNeeded;

    /**
	 * When screen is created set to Grades layout.
	 * Add students' grades as ListView to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_17_grades);

        startSearch();
	}

    /**
     * Gets all the grades from this student.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "names";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"assignment", "position"};

        sendData(tag, dataPassed, dataNeeded, url_find_grades, this, true);
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

                ListView show = (ListView) findViewById(R.id.grades);
                int layout = R.layout.list_grade;
                int[] ids = new int[] {R.id.name, R.id.extra};
                show.setAdapter(super.makeAdapterArray(data, true, this, layout, ids));
                show.setOnItemClickListener(this);
            }
        }
    }

    /**
     * Sends all data to show on a different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent i = new Intent(this, ShowGradesActivity.class);
        i.putExtra("title", ((TextView) findViewById(R.id.name)).getText().toString());
        i.putExtra("position", ((TextView) findViewById(R.id.extra)).getText().toString().replaceAll("%", ""));
        startActivity(i);
    }
}
