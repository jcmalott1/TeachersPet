
package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.BasicActivity;

/**
 * Handles back end for user interaction with Login Screen. Checks that username and password
 * are correct before proceeding.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 10/7/2014 
 */
public class LoginActivity extends BasicActivity {
	//Web page trying to reach
    private final String url_list_users = "https://morning-castle-9006.herokuapp.com/list_users.php";
    //Get id/username/password/accounttype from item
    private final int ID = 0;
    private final int PASSWORD = 5;
    private final int ACCOUNTTYPE =6;
	//New user button
	Button btn_newUser;
	//User's username
	String username;
	//User's passowrd
	String password;

	/**
	 * When screen is created set to login layout.
	 * Creates new user button and if clicked, go to new user screen.
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
			 * @param v View that was interacted with by user.
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
	 * If not display that login has failed
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		switch(view.getId()){
			case R.id.btn_login:
				super.makePref();
				startSearch();
				break;
			case R.id.btn_forgot:
				super.start(this, ForgotLoginActivity.class, false);
				break;
		}
	}
	
	/**
	 * Sets up data to be passed to model for a database search.
	 *
	 */
	private void startSearch(){
		//Get username/password text fields
        username = ((EditText) findViewById(R.id.fld_username)).getText().toString();
        password = ((EditText) findViewById(R.id.fld_pwd)).getText().toString();
		//JSON name data is stored under
		String tag = "user";
		String [] dataPassed = new String[]{"user", username};
		String [] dataNeeded = new String[]{"id","name","accountnumber","college","email","password", "accounttype"};

        super.sendData(tag, dataPassed, dataNeeded, url_list_users, this, true);
	}
	
	/**
	 * Receives data from model to check if password is a match to the username given. If so proceed with application
     * if not then warn user.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//Check request that this is a response to
	    if (requestCode == 0) {
            int success = data.getIntExtra("success", -1);
            //0 means successful
            if(success == 0) {
                //Can only be one user stored with that login name
                String[] userInfo = data.getStringArrayExtra("item0");
                if(password.equals(userInfo[PASSWORD])){
                    super.setPrefValue(userInfo[ACCOUNTTYPE], userInfo[ID]);
                    super.start(this, HomeActivity.class, false);
                }else{
                    showToast("Username/Password No Match", false);
                }
            } else {
                showToast("No User Found", true);
            }
	     }
	}

    /**
     * Displays a toast on the screen.
     *
     * @param toast Message to be displayed.
     * @param resetAll True: clear out all fields.
     */
    private void showToast(String toast, Boolean resetAll){
        Toast login_fail = Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT);
        login_fail.show();
        //reset username/password field to nothing
        ((EditText)findViewById(R.id.fld_pwd)).setText("");
        if(resetAll) {
            ((EditText) findViewById(R.id.fld_username)).setText("");
        }
    }
}
