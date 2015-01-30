package com.example.teacherspet.control;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.DeleteAlertActivity;
import com.example.teacherspet.model.GetItemActivity;

/**
 * User interaction for alert screen.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class AlertsActivity extends Activity {
	//To access and modify preference data
	SharedPreferences sharedPref;
	//Data that was preference
	String thisID;
	//Data to pass to web page
    String[] dataPassed;
	//Data collecting from web page
    String[] dataNeeded;
    //IDs for courses
  	ArrayList<String> viewIDs;
    //Id for intent
  	private static int REQUEST_CODE = 0;
  	ArrayList<HashMap<String, String>> alertList;
    //Web page to connect to
  	private static String url_list_alerts = "https://morning-castle-9006.herokuapp.com/list_alerts.php";

	/**
	 * When screen is created set to alert layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_5_alerts);
		//Access a preference file and require that only this application access it
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		//Get string that is stored under status
		thisID = sharedPref.getString("id", null);
		//Log.d("UID: ", uid);
		alertList = new ArrayList<HashMap<String, String>>();
		
		startSearch();
	}
	
	/**
	 * When button is clicked, collect all the text data and send to model
	 * to get data from database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		switch(view.getId()){
		case R.id.btn_submit:
			Intent i = new Intent(AlertsActivity.this, DeleteAlertActivity.class);
			i.putExtra("viewIDs", viewIDs);
			startActivity(i);
			finish();
			break;
		}
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
	         Intent data) {;
		//Check request that this is response to
	    if (requestCode == 0) {
	    	int success = data.getIntExtra("success", -1);
	    	if(success == 1){
		    	viewIDs = new ArrayList<String>();
		    	ListView courseView = (ListView) findViewById(R.id.alertView);
		        courseView.setAdapter(makeAdapter(data));
		        courseView.setOnItemClickListener(new OnItemClickListener(){
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view, int position,
		                       long id) {
		                changeColor(view);
		              }
		             });
	    	}
	     }
	}
	
	/**
	 * Gives all data to models to find in database.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "alerts";
		dataPassed = new String[]{"uid",thisID};
		dataNeeded = new String[]{"sname","course","term","time","pid","sid"};
				
		Intent i = new Intent(AlertsActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_alerts);
		//starts a activity but knows it will be returning something
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Makes  an adapter to attached to Edit Text View.
	 * 
	 * @param data Intent being returned
	 * @return Adapter being attached to View
	 */
	private ListAdapter makeAdapter(Intent data){
		//Get items that where returned
		int count = Integer.parseInt(data.getStringExtra("count"));
		
		for(int j = 0; j < count; j++){
			//single alert
		   	String[] items = data.getStringArrayExtra("item" + j);
		   	// creating new HashMap
		    HashMap<String, String> map = new HashMap<String, String>();
		    for(int i = 0; i < dataNeeded.length; i++){
		        map.put(dataNeeded[i], items[i]);
		        //Log.d("NAME: ",dataNeeded[i]);
		        //Log.d("VALUE: ",items[i]);
		    }
		   	alertList.add(map);
		}
	   	 
	    /**
	     * Updating parsed JSON data into ListView
	     */
	    return new SimpleAdapter(
	            AlertsActivity.this, alertList,
	            R.layout.list_alert, dataNeeded,
	            new int[] {R.id.sname, R.id.course, R.id.term, R.id.time,
	                	   R.id.pid, R.id.otherID});
	}
	
	/**
	 * Changes the color of the listview and stores the pid of the list view.
	 * 
	 * @param view View of list being pressed
	 */
	private void changeColor(View view){
		 // TODO Auto-generated method stub
		int green = getResources().getColor(android.R.color.holo_green_light);
    	int white = getResources().getColor(android.R.color.white);
        int color = Color.TRANSPARENT;
    	Drawable background = view.getBackground();
        if(background instanceof ColorDrawable)
 	       color = ((ColorDrawable) background).getColor();
    	        
    	if(color != green){
    		view.setBackgroundColor(green);
    		viewIDs.add(((TextView) findViewById(R.id.pid)).getText().toString());
    	}
    	else{
    	    view.setBackgroundColor(white);
    	    viewIDs.remove(((TextView) findViewById(R.id.pid)).getText().toString());
    	}
	}
}
