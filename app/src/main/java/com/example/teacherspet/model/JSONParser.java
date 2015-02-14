package com.example.teacherspet.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Places or get data from web site that database it connected to.
 * If data is received then in is places in a JSON object to be
 * decoded.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 11/7/2014
 */
public class JSONParser {
	//Read stream coming from web page
	static InputStream is = null;
	//Convert string to JSON for ability to decode
	static JSONObject jObj = null;
	//JSON string to be converted
	static String json = "";
	
	/**
	 * Places data on web page or receives that data.
	 * 
	 * @param url Web page that one is connect in to.
	 * @param method Get or Post command
	 * @param params What is being sent to the database.
	 * @return
	 */
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params){
		try{
			//Places data on the web page
			if(method == "POST"){
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
			//Receives data from the database
			}else if(method == "GET"){
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch(ClientProtocolException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			//Read input from web page
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
            Log.d("JSON STRING: ", json);
		}catch(Exception e){
			Log.e("Error","Error converting result " + e.toString());
		}
		
		try{
			jObj = new JSONObject(json);
		}catch(JSONException e){
			Log.e("JSON Parser", "Error parsing data" + e.toString());
		}
		
		return jObj;
	}
}
