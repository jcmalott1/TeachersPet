package com.example.teacherspet.view;

import com.example.teacherspet.R;
import com.example.teacherspet.model.PetAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * User interaction for Grades screen
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class GradesActivity extends ListActivity {
	//Holds grade information of student
    private String[] stringArray ;
    //List view of student grades
    private PetAdapter itemArrayAdapter;
    //Maximum length of array being stored
    private final static int MAX_ARRAY = 3;

    /**
	 * When screen is created set to Grades layout.
	 * Add students' grades as ListView to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_17p_grades);

	    stringArray = new String[]{"John Smith 98  3/3  0/3",
	    		                   "Sue Smith 70  2/3  1/3"};
	    itemArrayAdapter = new PetAdapter(this, stringArray);
	    setListAdapter(itemArrayAdapter);
	}
	
	/**
	 * When view is pressed get title of that view and open new screen with
	 * just the name from that title.
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
		    
		//Action for changing screens
	    Intent i = new Intent(this,SelectedGradesActivity.class);
	    //Get text from view pressed
	    String text = (String) ((TextView) view).getText();
	    //Splits between name and grade information
	   	String[] info = text.split(" ", MAX_ARRAY);
	   	//Name of student in that view
	   	String fullName = "";
	   	
	   	for(int j = 0; j < (MAX_ARRAY - 1); j++){
	   		fullName += info[j] + " ";
	   	}
	   	
	    //Stored name of student to pass to next screen
	   	i.putExtra("full_name", fullName);
	  	this.startActivity(i);	
	}
}
