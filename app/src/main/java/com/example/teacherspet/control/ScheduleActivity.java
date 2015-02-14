package com.example.teacherspet.control;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.teacherspet.model.PetAdapter;
import com.example.teacherspet.model.SelectedItem;

/**
 * Back end for user interaction for Schedule Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class ScheduleActivity extends ListActivity {
	//List View to be displayed
	private PetAdapter scheduleArrayAdapter;
	//Items to be displayed in List View
	private String[] DAYS = {"Monday","Tuesday","Wednesday","Thursday","Friday"};

	/**
	 * When screen is created add List View to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//values_array comes from res/values/string
		//scheduleArrayAdapter = new PetAdapter(this, DAYS);
		//setListAdapter(scheduleArrayAdapter);
	}
	
	/**
	 * When view is pressed get title of that view and open new screen with
	 * that information as title.
	 * 
	 * @param l ListView where clicked happen.
	 * @param view The view that was clicked. 
	 * @param position Location of view in the list.
	 * @param id Row is of item clicked.
	 * @Override
	 */
	@Override
	public void onListItemClick(ListView l, View view, int position,
			long id) {
	      	Intent i = new Intent(this,SelectedItem.class);
	      	
	      	//Stored data to be passed to next screem
	      	i.putExtra("text", ((TextView) view).getText());
	      	this.startActivity(i);
	}
}
