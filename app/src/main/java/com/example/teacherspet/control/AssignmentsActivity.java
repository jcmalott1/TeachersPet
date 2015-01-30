package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;
import com.example.teacherspet.model.SelectedItem;

/**
 * Back end for user Interaction for Assignment Screen.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AssignmentsActivity extends ListActivity {
	//Holds assignments to be displayed
	//private String[] assignments;
	ArrayList<HashMap<String,String>> homeworkList;
	//Get user ID
    private final int PID = 1;
	//Access preference data
	SharedPreferences sharedPref;
	//User Id that was preference
	String sid;
	//For adding view to screen
	//private PetAdapter assignArrayAdapter;
	//Data to pass to web page
    String[] dataPassed;
    //Data collecting from web page
	String[] dataNeeded;
	//Web page to connect to
	private static String url_list_homework = "https://morning-castle-9006.herokuapp.com/list_assignments.php";
	//Id for intent
	private static int REQUEST_CODE = 0;
	

	/**
	 * When screen is created set to assignment layout.
	 * Add assignment ListView to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_19_assignments);
		homeworkList = new ArrayList<HashMap<String, String>>();
		findHomework();
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
	 *
	@Override
	public void onListItemClick(ListView l, View view, int position,
			long id) {
		    
		//Change from this screen to Selected Item screen
	    Intent i = new Intent(this,SelectedItem.class);
	    String text = (String) ((TextView) view).getText();
	   	
	    //Put the text from the view as an extra
	   	i.putExtra("text", text);

	   	//Start the activity
	  	this.startActivity(i);	
	}*/
	
	/**
	 * Sends items to model to access the database and get data that is needed.
	 */
	private void findHomework(){
		//Get pid
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		sid = sharedPref.getString("id", null);
		//Name of JSON tag storing data
		String tag = "homeworks";
		dataPassed = new String[]{"sid",sid};
		dataNeeded = new String[]{"name","post","due","grade","sgrade"};
				
		Intent i = new Intent(AssignmentsActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_homework);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Called when Intent has returned from startActivityForResult
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
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	for(int i = 0; i < count; i++){
	    	   String[] items = data.getStringArrayExtra("item" + i);
	    	   HashMap<String, String> map =new HashMap<String, String>();
	    	   for(int j = 0; j < dataNeeded.length; j++){
	    		   map.put(dataNeeded[j], items[j]);
	    	   }
	    	   homeworkList.add(map);
	    	}
            /**
             * Updating parsed JSON data into ListView
             */
            ListAdapter adapter = new SimpleAdapter(
                    AssignmentsActivity.this, homeworkList,
                    R.layout.list_homework, dataNeeded,
                    new int[] {R.id.hname, R.id.hpost, R.id.hdue,
                    		    R.id.hsgrade, R.id.hgrade});
            // updating listview
            setListAdapter(adapter);  
	     }
	}
}
