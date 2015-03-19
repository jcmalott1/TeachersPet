package com.example.teacherspet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.control.AddCommentActivity;
import com.example.teacherspet.model.BasicActivity;

public class ShowLabActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_comments = "https://morning-castle-9006.herokuapp.com/find_comments.php";
    //Web page to find lab
    String url = "";
    String lid = "";

    /**
     * When screen is created set to alert layout, find user id, and start
     * looking for alerts.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_18_2_lab);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        lid = intent.getStringExtra("lid");

        //Set title
        ((TextView) findViewById(R.id.title)).setText(intent.getStringExtra("title"));

        if(super.getType().equals("p")){
            ((Button) findViewById(R.id.bnt_submit)).setVisibility(View.VISIBLE);
            ((ScrollView) findViewById(R.id.addScroll)).setVisibility(View.VISIBLE);
        }else{
            ((Button) findViewById(R.id.bnt_pdf)).setVisibility(View.VISIBLE);
            ((ListView) findViewById(R.id.commentView)).setVisibility(View.VISIBLE);
            startSearch();
        }
    }

    /**
     * Send search data to the database.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "comments";
        String[] dataPassed = new String[]{"uid", super.getID(),"cid", super.getCourseID(),"lid",lid};
        dataNeeded = new String[]{"name","descript"};

        super.sendData(tag, dataPassed, dataNeeded, url_find_comments, this, true);
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
                ListView courseView = (ListView) findViewById(R.id.commentView);
                //Get and pass data to make list adapter
                int layout = R.layout.list_grade;
                int[] ids = new int[] {R.id.name, R.id.extra};
                courseView.setAdapter(super.makeAdapter(data, dataNeeded, this, layout ,ids));
                courseView.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No Comments!!", Toast.LENGTH_SHORT);
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
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra("Name", super.getNameorExtra(position, "name"));
        i.putExtra("Extra", super.getNameorExtra(position, "descript"));
        i.putExtra("Details", "Lab Description:\n");
        startActivity(i);
    }

    /**
     * Get values for course and send to database.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        if(view.getId() == R.id.bnt_pdf) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }else{
            Intent i = new Intent(this, AddCommentActivity.class);
            i.putExtra("rid", ((EditText) findViewById(R.id.student)).getText().toString());
            i.putExtra("name", ((EditText) findViewById(R.id.cName)).getText().toString());
            i.putExtra("lid", lid);
            i.putExtra("descript", ((EditText) findViewById(R.id.cDescript)).getText().toString());
            startActivity(i);
        }
    }
}
