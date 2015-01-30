package com.example.teacherspet.view;

import com.example.teacherspet.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * User interaction for Settings Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class SettingsActivity extends Activity {

	/**
	 * When screen is created set to settings layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_8_settings);
	}
}
