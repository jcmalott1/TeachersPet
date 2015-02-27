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
 * @version 2/23/2015
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
        setScreen(intent.getStringExtra("Name"), intent.getStringExtra("Extra"),
                intent.getStringExtra("Details"));
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
        ((TextView) findViewById(R.id.title)).setText(title);

        String[] extras = extra.split("%");
        String[] details = detail.split("%");

        for(int i = 0; i < extras.length; i++){
            descript += "\n\n" + details[i] + extras[i];
        }
        ((TextView) findViewById(R.id.descript)).setText(descript);
    }
}
