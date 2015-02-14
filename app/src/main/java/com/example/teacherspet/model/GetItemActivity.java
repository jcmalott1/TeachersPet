package com.example.teacherspet.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Finds the data that the user wants and sends it back in a item list.
 * 
 * Returns Extras: list#: Items that where returned from database
 *                 count: Number of rows returned
 *                 success: If any data was accessed from database
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014
 */
public class GetItemActivity extends Activity{
	//Tells how long something takes to be found
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS= "success";
	//ID for intent
	private static final int REQUEST_CODE = 0;
	//Data sending to the web page
	String[] itemsPassed;
	//Data needed back from webpage
	String[] itemsNeeded;
	//Web page that is being visited
	String url_to_go;
	//JSON tag holding data
	String JSONTag;
	//Holds JSON data
	JSONArray courses = null;
	//Calling Activity to web pages
	Intent calling = new Intent();
	//Reads data from web page
	JSONParser jsonParser = new JSONParser();
		
	/**
	 * When screen is created get all data from intent and set to its
	 * corresponding value. Then connect to database and find that information.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		itemsPassed = intent.getStringArrayExtra("data1");
		itemsNeeded = intent.getStringArrayExtra("data2");
		JSONTag = intent.getStringExtra("JSONTag");
		url_to_go = intent.getStringExtra("url");
		
		new LoadItem().execute();
	}
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadItem extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GetItemActivity.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * Connects to the database and retrieves the data that the user requested can returns it.
         * If data can't be found then a message about why is returned instead.
         * */
        @Override
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for(int i = 0; i < itemsPassed.length; i += 2){
            params.add(new BasicNameValuePair(itemsPassed[i], itemsPassed[(i+1)]));
             // Log.d("#DATA: ", itemsPassed[(i+1)]);
            }
            
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(url_to_go, "GET", params);
 
            // Check your log cat for JSON reponse
            //Log.d("Item: ", json.toString());
            
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                String sMessage = json.getString("message");
                Log.d("SUCCESS: ", "" + success);
                Log.d("MESSAGE: ", "" + sMessage);
 
                if (success == 0) {
                    // item found
                    // Getting Array of items
                    courses = json.getJSONArray(JSONTag);
                   Log.d("Items: ", courses.toString());
 
                    // looping through All items
                    for (int i = 0; i < courses.length(); i++) {
                        JSONObject c = courses.getJSONObject(i);
                        //store all items in a list
                        String[] items = new String[itemsNeeded.length];
                        for(int j = 0; j < itemsNeeded.length; j++){
                        	items[j] = c.getString(itemsNeeded[j]);
                        	Log.d("GET ITEM: ", items[j]);
                        }
                        //Store row 
                        calling.putExtra(("item" + i ), items);
                    }
                    //number of rows stored
                    calling.putExtra("count", (""+courses.length()));
                    //if call to database failed or not
                    calling.putExtra("success", success);
                } else{
                    //If data not found send back why
                    calling.putExtra("success", success);
                    calling.putExtra("message", sMessage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
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
    }
}
