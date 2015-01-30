package com.example.teacherspet.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.GetItemActivity;
import com.example.teacherspet.model.Mail;

/**
 * Handles back end of user interaction for Forgot Login Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class ForgotLoginActivity extends Activity {
	//Web page trying to reach
	private final String url_list_users = "https://morning-castle-9006.herokuapp.com/list_users.php";
	//So Intent knows who it is calling
	private static int REQUEST_CODE = 0;
	//Get userid/email/username/password from item
	private final int STUDENTID = 2;
	private final int EMAIL = 4;
	private final int USERNAME = 1;
    private final int PASSWORD = 5;
	//Data to pass to web page
	String[] dataPassed;
	//Data collected from web page
	String[] dataNeeded;
	//Holds users info
	String userID;
	String email;
	String username;
	String password;

	/**
	 * When screen is created set to forgot login layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2_forgot_login);
	}
	
	/**
	 * Get the information that the user entered and pass along to model. 
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		startSearch();
	}
	
	/**
	 * Sets up data to be passed to the database.
	 *
	 */
	private void startSearch(){
		//Get username/password text box
		EditText txtID = (EditText) findViewById(R.id.userID);
		//Get username/password in text fields
		userID = txtID.getText().toString();
		//Authorized names
		//String[] names = getResources().getStringArray(R.array.login_array);
		String tag = "users";
		dataPassed = new String[]{};
		dataNeeded = new String[]{"id","name","accountnumber","college","email","password", "accounttype"};
		Intent i = new Intent(ForgotLoginActivity.this, GetItemActivity.class);
		i.putExtra("dataPassed", dataPassed);
		i.putExtra("dataNeeded", dataNeeded);
		i.putExtra("JSONTag", tag);
		i.putExtra("url", url_list_users);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	/**
	 * Takes data retrieved from the database and check if 920 is a correct number.
	 * If it is a email is sent to that 920's email address on file.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		Boolean passed = false;
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Number of rows returned
	    	int count = Integer.parseInt(data.getStringExtra("count"));
	    	for(int i = 0; i < count; i++){
	    		 //A single user
	    		 String[] item = data.getStringArrayExtra("item" + i);
	    		 if(userID.equals(item[STUDENTID])){
	    			 passed = true;
	    			 username = item[USERNAME];
	    			 password = item[PASSWORD];
	    			 email = item[EMAIL];
	    			 break;
	    		 }
	    	 }
	     }
	    if(passed){
			 try {
				 sendMail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
	    else{
	       Toast not_found = Toast.makeText(getApplicationContext(), "ID not found.", Toast.LENGTH_SHORT);
		   not_found.show();
		   start(ForgotLoginActivity.class);
	    }
	}
	
	/**
	 * Changes activity from this screen to the one passed and kills this screen.
	 * 
	 * @param toScreen Activity that you are going to.
	 */
	private void start(Class<?> toScreen){
		Intent intent = new Intent(ForgotLoginActivity.this, toScreen);
		startActivity(intent);
		finish();
	}
	
	/**
	 * Send data to model to send mail to mail server.
	 */
	private void sendMail(){
		//Sending email
		 //new SendTask().execute();
		 Mail mail = new Mail();
	     mail.setTo(new String[]{email});
	     mail.setSubject("Lost password");
	     mail.setBody("Username: " + username + " Password: " + password);
	     mail.sendMail();
	     Toast mail_sent = Toast.makeText(getApplicationContext(), "Email sent!", Toast.LENGTH_SHORT);
		   mail_sent.show();
	}
}

