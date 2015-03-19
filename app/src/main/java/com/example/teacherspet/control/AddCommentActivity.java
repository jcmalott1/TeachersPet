package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.model.BasicActivity;

public class AddCommentActivity extends BasicActivity {
    private static String url_add_comment = "https://morning-castle-9006.herokuapp.com/create_comment.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendItems();
    }

    /**
     * Sends items to model to access the database and get data that is needed.
     */
    private void sendItems() {
        //Name of JSON tag storing data
        String[] itemNames = new String[]{"cid","rid","lid","name","descript","pid"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, url_add_comment, this, false);
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
                Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(this, "Comment Error", Toast.LENGTH_SHORT);
            }
            finish();
        }
    }

    /**
     *
     * @return
     */
    private String[] getValues(){
        Intent i = getIntent();

        String cid = super.getCourseID();
        String rid = i.getStringExtra("rid");
        String lid = i.getStringExtra("lid");
        String name = i.getStringExtra("name");
        String descript = i.getStringExtra("descript");
        String pid = super.getID();

        return new String[]{cid,rid,lid,name,descript,pid};
    }
}
