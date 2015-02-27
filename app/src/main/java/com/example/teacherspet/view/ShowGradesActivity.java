package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;


/**
 * Displays all data given to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 2/24/2015
 */
public class ShowGradesActivity extends BasicActivity {
    String position, title;
    private static String url_find_student_grades = "https://morning-castle-9006.herokuapp.com/find_student_grades.php";
    String[] dataNeeded;

    /**
     * When screen is created set to show layout.
     * Get all data that was passed.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        position = intent.getStringExtra("position");

        startSearch();
    }

    /**
     * Gets all the grades from this student.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "grades";
        String[] dataPassed = new String[]{"cid", super.getCourseID(), "pos", position};
        dataNeeded = new String[]{"grade","name"};

        sendData(tag, dataPassed, dataNeeded, url_find_student_grades, this, true);
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

                String descript = "";
                int count = Integer.parseInt(data.getStringExtra("count"));
                for(int i = 0; i < count; i++){
                    String[] item = data.getStringArrayExtra("item" + i);
                    descript += "\n\n" + item[1] + ": " + item[0];
                }
                ((TextView) findViewById(R.id.title)).setText(title);
                ((TextView) findViewById(R.id.descript)).setText(descript);
            }
        }
    }
}
