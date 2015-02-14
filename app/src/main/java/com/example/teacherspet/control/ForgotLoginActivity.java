package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.R;
import com.example.teacherspet.model.Mail;

/**
 * Handles back end of user interaction for Forgot Login Screen. If user is in database it will sent
 * an email with password in it.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class ForgotLoginActivity extends BasicActivity {
	//Web page trying to reach
	private final String url_list_users = "https://morning-castle-9006.herokuapp.com/list_users.php";
	//Get userid/email/username/password from item
	private final int EMAIL = 1;
	private final int USERNAME = 0;
    private final int PASSWORD = 2;

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
	 * Get 920 number entered and pass it to the database.
	 *
	 */
	private void startSearch(){
		//Get id number entered
		EditText txtID = (EditText) findViewById(R.id.userID);
		String userID = txtID.getText().toString();
        //Set up and send data
		String tag = "user";
		String [] dataPassed = new String[]{"userID", userID};
		String [] dataNeeded = new String[]{"name","email","password"};
        super.sendData(tag, dataPassed, dataNeeded, url_list_users, this, true);
	}
	
	/**
	 * If 920# was found then email will be sent to that account address. If not found then
     * user will be notified.
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
            int success = data.getIntExtra("success", -1);
            //0 Means successful
            if(success == 0){
                //Can only be one user with that 920#
                String[] item = data.getStringArrayExtra("item0");
                String username = item[USERNAME];
                String password = item[PASSWORD];
                String email = item[EMAIL];

                try {
                    sendMail(email, username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Toast not_found = Toast.makeText(getApplicationContext(), "ID not found.", Toast.LENGTH_SHORT);
                not_found.show();
                super.start(this, ForgotLoginActivity.class, true);
            }
	     }
	}
	
	/**
	 * Sends out an email to the user about username/password.
	 */
	private void sendMail(String email, String user, String pass){
		//Sending email
		 //new SendTask().execute();
		 Mail mail = new Mail();
	     mail.setTo(new String[]{email});
	     mail.setSubject("Lost password");
	     mail.setBody("Username: " + user + " Password: " + pass);
	     mail.sendMail();
	     Toast mail_sent = Toast.makeText(getApplicationContext(), "Email sent!", Toast.LENGTH_SHORT);
		 mail_sent.show();
	}
}

