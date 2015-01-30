package com.example.teacherspet.view;

import com.example.teacherspet.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Set up Grade Screen from data that was passed from previous screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class SelectedGradesActivity extends Activity {
	
	/**
	 * When screen is created set to grade layout and display data that was
	 * passed.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set the layout for this activity.
		setContentView(R.layout.activity_28_grade);
		//View to display data
		TextView tv1 = (TextView) this.findViewById(R.id.studentName);;
		//Default name to be displayed
		String text = "Jane Doe";
		//Get data stored from previous screen
		Bundle extras = getIntent().getExtras();
		
		//If and data was passed set it to the Text View
		if (extras != null)
		{
			text = extras.getString("full_name");
		}
		
		tv1.setText(text);
	}
}

