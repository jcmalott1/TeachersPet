package com.example.teacherspet.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.PostItemActivity;

/**
 * Back endd for user interaction for New User Screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class NewUserActivity extends Activity {
	//URL to add user to database
    private static final String url_create_user = "https://morning-castle-9006.herokuapp.com/create_user.php";
    //Id so intent knows who is calling it
    private static int REQUEST_CODE = 0;
    //Name of field in database
    String[] itemNames;
    //Values to place into those fields
    String[] itemValues;
    //Checks if student/professor account was checked
    CheckBox student;
    CheckBox professor;
	
	/**
	 * When screen is created set to new user layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3_new_user);
			
		student = (CheckBox) findViewById(R.id.checkS);
	    professor = (CheckBox) findViewById(R.id.checkP);
	}
	
	/**
	 * Change user interface from New User to Home screen
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		//Action to hold screen change.
		if(view.getId() == R.id.btn_submit){
			if(student.isChecked() || professor.isChecked()){
				sendItems();
			}
		}
			
			/**Action to hold screen change.
			if(view.getId() == R.id.btn_submit) {
				Intent foo = new Intent(NewUserActivity.this, Check920Activity.class);
				foo.putExtra("920", inputAccountnumber.getText().toString());
				startActivityForResult(foo, 1);
				if (isValidInput(inputName, inputAccountnumber, inputCollege, inputEmail, inputPassword)) {
					sendItems();
				}
			}*/
	}
	
	/**
	 * Send items to Post Activity to deliver to database from php file
	 */
	public void sendItems(){
		itemNames = new String[]{"name","accountnumber","college","email","password","accounttype"};
		itemValues = getValues();
		
		Intent i = new Intent(NewUserActivity.this, PostItemActivity.class);
		i.putExtra("itemNames", itemNames);
		i.putExtra("itemValues", itemValues);
		i.putExtra("url", url_create_user);
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
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Tells if items where added or not. 1 mean successful
	    	int success = data.getIntExtra("success", -1);
	    	if(success == 1){
	    		Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
	    	}else if(success == 0){
	    		Toast.makeText(getApplicationContext(), "No account created", Toast.LENGTH_SHORT).show();
	    	}
	    }
	    Intent i = new Intent(NewUserActivity.this, LoginActivity.class);
	    startActivity(i);
	    finish();
	}
	
	/**
	 * Get all data the user filled out and return it.
	 * 
	 * @return A list of all values entered by user.
	 */
	private String[] getValues(){
		EditText inputName = (EditText) findViewById(R.id.fld_name);
		EditText inputAccountnumber = (EditText) findViewById(R.id.fld_id);
		EditText inputCollege = (EditText) findViewById(R.id.fld_school);
		EditText inputEmail = (EditText) findViewById(R.id.fld_email);
		EditText inputPassword = (EditText) findViewById(R.id.fld_password);
		
		String name = inputName.getText().toString();
        String accountnumber = inputAccountnumber.getText().toString();
        String college = inputCollege.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        //Student by default
        String accounttype = "s";
        
        if(student.isChecked()){
			accounttype = "s";
		}else if(professor.isChecked()){
			accounttype = "p";
		}
        
        return (new String[]{name,accountnumber,college,email,password,accounttype});
	}
	
	/*
	 * This method checks that the given input for the NewUserActivity is in a valid format. If any argument is not valid,
	 * then a toast will displayed to the screen containing the error message. 
	 * 
	 * @return boolean
	 * @param inputname - Name
	 * 		  inputAccountnumber - '920' student id
	 * 		  inputCollege - College Name
	 * 		  inputEmail - Email address
	 * 		  inputPassword - Password
	 **/
	public boolean isValidInput(String name, String accountnumber, String college, String email, String password) {
        // Holds toast response
        String response;
        // Name must NOT have numbers in it
        if (name.matches(".*\\d+.*")) {
        	response = "Name cannot contain numbers.";
        }
        // Check account number for length, and to see it contains '920'
        else if (accountnumber.length() != 9) {
        	response = "Invalid account number length.";
        }
        else if (!accountnumber.matches("920\\d{6}")) {
        	response = "Account number must start with '920'.";
        }
        // College name must NOT have numbers in it
        else if (college.matches(".*\\d+.*")) {
        	response = "College name cannot contain numbers.";
        }
        // Email must have '.edu' suffix
        else if (!email.matches("\\w+@.+\\.edu")) {
        	response = "Email must have '.edu' suffix.";
        }
        // Minimum password length is 7
        else if (password.length() < 7) {
        	response = "Password must be at least 7 characters long.";
        }
        else {
        	// Success
        	return true;
        }
        // Invalid input
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        return false;
	}

}
