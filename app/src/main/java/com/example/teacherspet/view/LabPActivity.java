package com.example.teacherspet.view;

import com.example.teacherspet.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * User interaction for Professor Lab Screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class LabPActivity extends Activity {
	//Auto completes text in TextView
	AutoCompleteTextView labView;
	//What auto complete is checking for
	String[] labEntries;
	//ListView to be displayed
	ArrayAdapter<String> labAdapter;

	/**
	 * When screen is created set to professor lab layout.
	 * Sets up auto-complete feature on lab TextView
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_18p_lab);
		
		labView = (AutoCompleteTextView) findViewById(R.id.autoLab);
		labEntries = getResources().getStringArray(R.array.lab_array);
		labAdapter = new ArrayAdapter<String>(this, R.layout.list_item, labEntries);
		labView.setAdapter(labAdapter);
	}
}
