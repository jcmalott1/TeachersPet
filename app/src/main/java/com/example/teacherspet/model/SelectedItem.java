package com.example.teacherspet.model;

import com.example.teacherspet.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Sets up a screen based off data that was passed to it from a previous 
 * screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class SelectedItem extends Activity {

	/**
	 * When screen is created set to schedule layout. Gets data that was 
	 * stored from the last screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set the layout for this activity.
		setContentView(R.layout.activity_7_schedule);
		
		//Get data that was stored from last screen
		Bundle extras = getIntent().getExtras();
		
		//See if any extra data was passed, if so set that data is a 
		//Text View.
		if (extras != null)
		{
			String text = extras.getString("text");
			TextView tv1 = (TextView) this.findViewById(R.id.textView1);
			tv1.setText(text);
		}
	}
}

