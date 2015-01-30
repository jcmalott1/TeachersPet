package com.example.teacherspet.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Collects data from user and inserts into database.
 * 
 * Return Extras:  success: 1 is successful, 0 is fail
 * @author Johnathon Malott
 * @version 11/10/2014
 */
public class PostItemActivity extends Activity{
	private static final String TAG_SUCCESS= "success";
	//ID for intent
	private static final int REQUEST_CODE = 0;
	//Tells how long something takes to be found
	private ProgressDialog pDialog;
	//Names of the fields in the database
	String[] itemNames;
	//Values to be placed into names
	String[] itemValues;
	//URL to perform action on database
	String url_to_go;
	//Calling Activity to web pages
	Intent calling = new Intent();
	//Reads data from web page
    JSONParser jsonParser = new JSONParser();
	
    /**
	 * When screen is created get all data from intent and set to its
	 * corresponding value. Then connect to database through PHP file.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		itemNames = intent.getStringArrayExtra("itemNames");
		itemValues = intent.getStringArrayExtra("itemValues");
		url_to_go = intent.getStringExtra("url");
		//Log.d("URL: ", url_to_go);
		new PostItem().execute();
	}
	
	class PostItem extends AsyncTask<String, String, String>{
		/**
		 * Done before starting background thread
		 */
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(PostItemActivity.this);
			pDialog.setMessage("Creating new item...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		/**
         * After completing background task Dismiss the progress dialog and sends back data the
         * user needs.
         * **/
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
        	//Send data back to calling activity
           setResult(REQUEST_CODE, calling);
           //Close this activity
           finish();
        }
        
        /**
		 * Creates a new row or rows in database.
		 * 
		 * @param args Parameters sent to task on execution.
		 * @return Nothing
		 */
		@Override
		protected String doInBackground(String... args){
			// Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
			for(int i = 0; i < itemNames.length; i++){
	            params.add(new BasicNameValuePair(itemNames[i], itemValues[i]));
	            //Log.d("Param: ", "" + itemValues[i]);
            }
            // getting JSON Object and sending items to web page
            JSONObject json = jsonParser.makeHttpRequest(url_to_go,
                    "POST", params);
            
            //Log.d("Course: ", json.toString());
            try {
            	//Find out if row was created
                int success = json.getInt(TAG_SUCCESS);
                String success2 = json.getString("message");
                Log.d("Success: ", "" + success);
                Log.d("Message: ", "" + success2);
                //1 means that it passed
                calling.putExtra("success", success);
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
		}
	}
}
