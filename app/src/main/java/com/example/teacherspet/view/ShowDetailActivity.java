package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Displays all data given to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class ShowDetailActivity extends BasicActivity {

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
        setScreen(intent.getStringExtra(AppCSTR.SHOW_NAME), intent.getStringExtra(AppCSTR.SHOW_EXTRA),
                intent.getStringExtra(AppCSTR.SHOW_DETAIL));
    }

    /**
     * Sets the title of the page and along with description of that item.
     *
     * @param title Title of the page.
     * @param extra Description.
     * @param detail Title's for the description.
     */
    private void setScreen(String title, String extra, String detail){
        String descript = "";
        String[] extras = extra.split("%");
        String[] details = detail.split("%");
        //set title
        ((TextView) findViewById(R.id.title)).setText(title);

        for(int i = 0; i < extras.length; i++){
            descript += "\n\n" + details[i] + extras[i];
        }
        //Set description
        ((TextView) findViewById(R.id.descript)).setText(descript);
    }
}
