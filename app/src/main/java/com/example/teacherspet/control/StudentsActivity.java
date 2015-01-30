package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;
import com.example.teacherspet.model.SelectedItem;

/**
 * Back end for user interaction for Students Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class StudentsActivity extends ListActivity {
	//Key values pair for user info
	ArrayList<HashMap<String, String>> userList;
	//Web page to get JSON information about users
	private static final String url_list_users = "https://morning-castle-9006.herokuapp.com/list_users.php";
	//Id so intent knows who is calling it
    private static int REQUEST_CODE = 0;
    //Data to pass to website
  	String[] dataPassed;
  	//Data collected from web page
  	String[] dataNeeded;

	/**
	 * When screen is created set students layout.
	 * Add List View to Screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_15_students);
		startSearch();
	}
	
	/**
	 * When view is pressed get title of that view and passed that on to the
	 * next screen.
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
		//Action to change screens
	    Intent i = new Intent(this,SelectedItem.class);
	    //Title of view clicked
	    String text = (String) ((TextView) view).getText();
	   	
	    //Stored data to be passed to next screen
	   	i.putExtra("text", text);
	  	this.startActivity(i);	
	}
	
	/**
	 * Receives data from model that was received from the database.
	 * Takes this info and places on screen.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//Check request that this is response to
	    if (requestCode == 0) {
	    	 userList = new ArrayList<HashMap<String, String>>();
	    	//Number of users sent back
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	 for(int i = 0; i < count; i++){
	    		//A single user
	    		 String[] item = data.getStringArrayExtra("item" + i);
	    		 HashMap<String, String> map = new HashMap<String, String>();
	    		 for(int j = 0; j < dataNeeded.length; j++){
		    		// adding each child node to HashMap key => value
		             map.put(dataNeeded[j], item[j]);
	    		 }
	    		 //add row to list
	    		 userList.add(map);
	    	 }
	    	 
	    	ListAdapter adapter = new SimpleAdapter(
					StudentsActivity.this, userList,
					R.layout.student_list, dataNeeded, new int[]{
							R.id.uid,
							R.id.name, 
							R.id.accountnumber,
                		    R.id.college, 
                		    R.id.email, 
                		    R.id.password, 
                		    R.id.accounttype
					});
			setListAdapter(adapter);
	    }
	    else{
	    	//if no students are in class exit
	    	finish();
	    }
	}
	
	/**
	 * Sets up data to be passed to model for a database search.
	 *
	 */
	private void startSearch(){
		String tag = "users";
		dataPassed = new String[]{};
		dataNeeded = new String[]{"id","name","accountnumber","college","email","password", "accounttype"};
		Intent i = new Intent(StudentsActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_users);
		startActivityForResult(i, REQUEST_CODE);
	}
}
