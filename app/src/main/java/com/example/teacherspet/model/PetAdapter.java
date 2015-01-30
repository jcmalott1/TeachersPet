package com.example.teacherspet.model;

import com.example.teacherspet.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

/**
 * Custom Array Adapter
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class PetAdapter extends ArrayAdapter<String>{
	//takes input as XML file and builds the View objects from it
	private LayoutInflater inflater;
	
	/**
	 * Set up custom ArrayAdapter with custom list
	 * 
	 * @param activity Activity to display List View on.
	 * @param items Items to be displayed.
	 */
	 public PetAdapter(Activity activity, String[] items){
		 super(activity, R.layout.list_item, items);
         inflater = activity.getWindow().getLayoutInflater();
	 }
}
