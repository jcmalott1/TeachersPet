package com.example.teacherspet.view;

import com.example.teacherspet.R;
import com.example.teacherspet.model.PetAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * User interaction for Information Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class InformationActivity extends ListActivity {
	//Stored class room information
	private String[] info;
	//Store ListView to be displayed
	private PetAdapter infoAdapter;

	/**
	 * When screen is created set to information layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_14_information);
		info = new String[]{"Course Outline", "Syllabus"};
		
		infoAdapter = new PetAdapter(this, info);
		setListAdapter(infoAdapter);
	}
	
	/**
	 * When view is pressed get file and display it.
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
		//Title of view that was pressed
	    String text = (String) ((TextView) view).getText();
	    //Where to display image
	    ImageView myImageView = (ImageView)findViewById(R.id.displayInfo);
	      	
	   	switch(text){
	    case "Course Outline":
	      	myImageView.setImageResource(R.drawable.course_outline);
	        break;
	    case "Syllabus":
	      	myImageView.setImageResource(R.drawable.syllabus);
	      	break;
	    default: 
	      	}
	   	//Get rid of ListView on current screen
	   	setListAdapter(null);
    }
}
