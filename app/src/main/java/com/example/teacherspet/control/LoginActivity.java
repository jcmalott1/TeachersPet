
package com.example.teacherspet.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;

/**
 * **Get rid of the start method
 * back end for user interaction for Login Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class LoginActivity extends Activity {
	//Web page trying to reach
    private final String url_list_users = "https://morning-castle-9006.herokuapp.com/list_users.php";
    //Id so intent knows who is calling it
    private static int REQUEST_CODE = 0;
    //Get id/username/password/accounttype from item
    private final int ID = 0;
    private final int USERNAME = 1;
    private final int PASSWORD = 5;
    private final int ACCOUNTTYPE =6;
	//New user button
	Button btn_newUser;
	//Store or modify preference
	SharedPreferences sharedPref;
	//Set up preference to store data
	Editor edit;
	//Data to pass to website
	String[] dataPassed;
	//Data collected from web page
	String[] dataNeeded;
	//User's username
	String username;
	//User's passowrd
	String password;

	/**
	 * When screen is created set to login layout.
	 * If new user button is clicked, go to new user screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1_login);
		
		btn_newUser = (Button) findViewById(R.id.btn_newUser);
		btn_newUser.setOnClickListener(new View.OnClickListener(){
			/**
			 * Change user interface from login screen to new user screen
			 * 
			 * @param view View that was interacted with by user.
			 */
			@Override
			public void onClick(View v){
				Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Checks to see if username and password are valid.
	 * If correct go to home screen.
	 * If not displays that login has failed
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		switch(view.getId()){
			case R.id.btn_login:
				makePref();
				startSearch();
				break;
			case R.id.btn_forgot:
				Intent intent = new Intent(LoginActivity.this, ForgotLoginActivity.class);
				startActivity(intent);
				break;
		}
	}
	
	/**
	 * Sets up data to be passed to model for a database search.
	 *
	 */
	private void startSearch(){
		//Get username/password text box
		EditText txtUser = (EditText) findViewById(R.id.fld_username);
		EditText txtPass = (EditText) findViewById(R.id.fld_pwd);
		//Get username/password in text fields
		username = txtUser.getText().toString();
		password = txtPass.getText().toString();
		//JSON name data is stored under
		String tag = "users";
		dataPassed = new String[]{};
		dataNeeded = new String[]{"id","name","accountnumber","college","email","password", "accounttype"};
		Intent i = new Intent(LoginActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_users);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Receives data from model that was received from the database.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//
    	Boolean passed =false;
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Number of users sent back
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	 for(int i = 0; i < count; i++){
	    		 //A single user
	    		 String[] item = data.getStringArrayExtra("item" + i);
	    		 if(username.equals(item[USERNAME]) && password.equals(item[PASSWORD])){
	    			 passed = true;
	    			 setPrefValue(item[ACCOUNTTYPE], item[ID]);
	    			 //Exit out of loop when found
	    			 break;
	    		 }
	    	 }
	     }
	    if(passed){
			 start(HomeActivity.class);
		 }
	    else{
	       Toast login_fail = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
		   login_fail.show();
		   start(LoginActivity.class);
	    }
	}
	
	/**
	 * Sets up a shared preferences to store information that maybe needed later.
	 */
	private void makePref(){
		//Creates a shared pref called MyPref and 0-> MODE_PRIVATE
		sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		//so the pref can be edit
		edit = sharedPref.edit();
	}
	
	/**
	 * Stores users' Account Type and id.
	 * 
	 * @param status Value to be stored.
	 */
	private void setPrefValue(String status, String id){
		//adds a key value pair to pref
	    edit.putString("status", status);
	    edit.putString("id", id);
		//records changes
	    edit.commit();
	} 
	
	/**
	 * Changes activity from this screen to the one passed and kills this screen.
	 * 
	 * @param toScreen Activity that you are going to.
	 */
	private void start(Class<?> toScreen){
		Intent intent = new Intent(LoginActivity.this, toScreen);
		startActivity(intent);
	}
}
