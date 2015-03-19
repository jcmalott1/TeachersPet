package com.example.teacherspet.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Basic methods that can be used in any activity class.
 *
 *  @author Johnathon Malott, Kevin James
 * @version 1/31/2015
 */
public class BasicActivity extends Activity {
    //So Intent knows who it is calling
    private static final int REQUEST_CODE = 0;
    //To access and modify preference data
    SharedPreferences sharedPref;
    //Set up preference to store data
    SharedPreferences.Editor edit;
    //Store items on a list view
    ArrayList<HashMap<String, String>> alertList;
    //Ids of selected items
    ArrayList<String> redIDs = new ArrayList<String>();
    ArrayList<String> greenIDs = new ArrayList<String>();

    /**
     * Changes activity from fromScreen to toScreen. Also if kill is true then destroy screen
     * coming from.
     *
     * @param fromScreen Activity that you are coming from.
     * @param toScreen Activity that you are going to.
     * @param kill true if screen coming from should de destroyed
     */
    protected void start(Context fromScreen,Class<?> toScreen, Boolean kill){
        Intent intent = new Intent(fromScreen, toScreen);
        startActivity(intent);
        if(kill){
            finish();
        }
    }

    /**
     * Takes data entered and is sent to the database to be retrieved.
     *
     * @param tag Name of contains that user wants to receive. Leave blank if a post method needed.
     * @param data1 Get: Data needed to pass info/ Post: Values being set
     * @param data2 Get: Data needed back/ Post: Values wanting to send
     * @param url Php to get or post information
     * @param callingActivity Activity that is invoking this method
     * @param getItem True if needed to be a Get method/ False for Post method
     */
    protected void sendData(String tag, String [] data1, String [] data2, String url,
                           Context callingActivity, Boolean getItem){
        //sending data
        Intent i;
        if(getItem){
            i = new Intent(callingActivity, GetItemActivity.class);
            i.putExtra("JSONTag", tag);
        }else{
            i = new Intent(callingActivity, PostItemActivity.class);
        }
        i.putExtra("data1", data1);
        i.putExtra("data2", data2);
        i.putExtra("url", url);
        startActivityForResult(i, REQUEST_CODE);
    }

//************************************* PREF START **************************************************

    /**
     * Sets up a shared preferences to store information that maybe needed later.
     */
    protected void makePref(){
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
    protected void setPrefValue(String status, String id){
        //adds a key value pair to pref
        edit.putString("accountType", status);
        edit.putString("id", id);
        //records changes
        edit.commit();
    }

    /**
     * Stores what course the user is under from the courses screen.
     *
     * @param courseID ID for course that user is currently under.
     */
    protected void setCourseID(String courseID){
        //adds a key value pair to pref
        edit.putString("courseID", courseID);
        //records changes
        edit.commit();
    }

    /**
     * Return the users' ID for what course they are under.
     *
     * @return CourseID for the class the user is currently under.
     */
    protected String getCourseID(){
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //Log.d("ID", sharedPref.getString("id", null));
        return sharedPref.getString("courseID", null);
    }

    /**
     * Return the User id store in device memory.
     *
     * @return User ID.
     */
    protected String getID(){
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //Log.d("ID", sharedPref.getString("id", null));
        return sharedPref.getString("id", null);
    }

    /**
     * Return the User id store in device memory.
     *
     * @return User ID.
     */
    protected String getType(){
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //Log.d("TYPE", sharedPref.getString("accountType", null));
        return sharedPref.getString("accountType", null);
    }

//************************************* PREF END **************************************************

//********************************* SELECT ITEM START *********************************************

    /**
     * Changes the color of the listview and stores the alert id of the list view.
     *
     * @param view View of list being pressed
     */
    protected void changeATTNColor(View view, int position, String key){
        int green = getResources().getColor(android.R.color.holo_green_light);
        int red = getResources().getColor(android.R.color.holo_red_light);
        int white = getResources().getColor(android.R.color.white);
        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();

        if(background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        if(color == green){
            view.setBackgroundColor(red);
            greenIDs.remove(getList().get(position).get(key));
            //Log.d("COLORID: ", getList().get(position).get(key));
            redIDs.add(getList().get(position).get(key));
        }else if(color == red){
            view.setBackgroundColor(white);
            //Log.d("COLORID: ", getList().get(position).get(key));
            redIDs.remove(getList().get(position).get(key));
        }else{
            view.setBackgroundColor(green);
            //Log.d("COLORID: ", getList().get(position).get(key));
            greenIDs.add(getList().get(position).get(key));
        }
    }

    /**
     * Changes the color of the listview and stores the alert id of the list view.
     *
     * @param view View of list being pressed
     */
    protected void changeColor(View view, int position, String key){
        int green = getResources().getColor(android.R.color.holo_green_light);
        int white = getResources().getColor(android.R.color.white);
        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();

        if(background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        if(color != green){
            view.setBackgroundColor(green);
            //Log.d("COLORID: ", getList().get(position).get(key));
            greenIDs.add(getList().get(position).get(key));
        }else{
            view.setBackgroundColor(white);
            greenIDs.remove(getList().get(position).get(key));
        }
    }

    /**
     * Returns the ids of item that was selected.
     *
     * @return Ids for selected items.
     */
    protected ArrayList<String> getViewID(String color){
        if(color.equals("green")){
            return greenIDs;
        }
        return redIDs;
    }

    /**
     * Deletes all ids in the list.
     */
    protected void clearViewID(){
        redIDs.clear();
        greenIDs.clear();
    }

//********************************** SELECT ITEM END ***********************************************

//********************************** ADAPTER START *************************************************

    /**
     * Makes  an adapter to attached to Edit Text View.
     *
     * @param data Intent being returned
     * @return Adapter being attached to View
     */
    protected ListAdapter makeAdapter(Intent data, String[] dataNeeded, Context activity,
                                    int layout, int[] ids){
        //Get items that where returned
        int count = Integer.parseInt(data.getStringExtra("count"));
        //Log.d("Count: ", "" + count);
        alertList = new ArrayList<HashMap<String, String>>();

        for(int j = 0; j < count; j++){
            //single alert
            String[] items = data.getStringArrayExtra("item" + j);
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            for(int i = 0; i < dataNeeded.length; i++){
                map.put(dataNeeded[i], items[i]);
                //Log.d("NAME: ", dataNeeded[i]);
                //Log.d("VALUE: ",items[i]);
            }
            alertList.add(map);
        }

        /**
         * Details of how the adapter will be laid out.
         */
        return new SimpleAdapter(
                activity, alertList,
                layout, dataNeeded, ids);
    }

    /**
     * Makes  an adapter to a list view.
     *
     * @param data Intent being returned
     * @return Adapter being attached to View
     */
    protected ListAdapter makeAdapterArray(Intent data, Boolean passE, Context activity,
                                      int layout, int[] ids){

        alertList = new ArrayList<HashMap<String, String>>();
        String[] extras = null;
        //Key values for the mapping
        String[] keyNames = {"name"};
        String[] item = data.getStringArrayExtra("item0");
        //Take off brackets of string array from database, what is to be displayed in list view.
        String[] names = arrayParser(item[0]);
        int count = names.length;
        Log.d("NAME: ", "" + count);

        if(passE) {
            //All  other data passed besides name
            extras = getExtraInfo(item, count);
            //Log.d("ANAMES: ", "" + names[0]);
            keyNames = new String[]{"name","extra"};
        }

        for(int i = 0; i < count; i++){
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", names[i]);
            Log.d("NAME: ", names[i]);
            if(passE) {
                map.put("extra", extras[i]);
                Log.d("Extra: ", extras[i]);
            }
            alertList.add(map);
            //Log.d("Extra: ", extras[i]);
        }
        /**
         * Details of how the adapter will be laid out.
         */
        return new SimpleAdapter(
                activity, alertList,
                layout, keyNames, ids);
    }


    /**
     * Takes all the assignment data and groups the data together by each assignment.
     *
     * @param item Holds all assignment data for student.
     * @param colLength Length that each array is going to be.
     * @return All assignments grouped by assignment.
     */
    private String[] getExtraInfo(String[] item, int colLength){
        String[][] data = new String[item.length][colLength];
        //Log.d("ColLength: ", "" + colLength);
        //Log.d("ItemLength: ", "" + item.length);
        String[] extras  = new String[item.length];
        String dataString = "";

        //getting all the data back except grade names
        for(int i = 0; i < item.length - 1; i++) {
            data[i] = arrayParser(item[i+1]);
        }

        //Store all data for 1 assignment together
        for(int i = 0; i < colLength; i++) {
            for(int j = 0; j < (item.length - 1); j++) {
                dataString += data[j][i] + "%";
            }
            Log.d("Data: ", dataString);
            extras[i] = dataString;
            dataString = "";
        }

        return extras;
    }

    /**
     * Takes array from database and turns into string array in java.
     * @param item Array String from databse
     * @return Array String in java
     */
    protected String[] arrayParser(String item){
       return item.replaceAll("\\{|\\}|\\[|\\]|(\\d+\":)|\"", "").split(",");
        //return item2.replaceAll("\"", "").split(",");
    }


    /**
     * List that holds all data needed from the listview
     *
     * @return List of data stored.
     */
    private ArrayList<HashMap<String, String>>  getList(){
        return alertList;
    }

    /**
     * Gets the name for an item or that extras along with it
     * @param position Location in the view.
     * @param key Value that user is trying to reach.
     * @return
     */
    protected String getNameorExtra(int position, String key){
        return getList().get(position).get(key);
    }

//********************************** ADAPTER END ****************************************************
}
