package com.codepath.example.customadapterdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

public class CustomListActivity extends Activity {

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// products JSONArray
	JSONArray products = null;

	JSONArray json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_list);
		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllProducts().execute();

		//populateUsersList();
	}

	private void populateUsersList() {
		// Construct the data source
		ArrayList<DataPhone> arrayOfDataForiPhones =  new ArrayList<DataPhone>(); //DataPhone.getUsers();
		for (int i = 0; i < json.length(); i++) {
			try {
				arrayOfDataForiPhones.add(new DataPhone(json.getJSONObject(i).getString("Name")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// Create the adapter to convert the array to views
		CustomUsersAdapter adapter = new CustomUsersAdapter(this, arrayOfDataForiPhones);
		// Attach the adapter to a ListView
		ListView listView = (ListView) findViewById(R.id.lvUsers);
		listView.setAdapter(adapter);
	}

	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			json = jParser.makeHttpRequest("http://www.oslophone.com/server/CategoryParse.php", "GET", params);
			try {
				_("test"+json.getJSONObject(0).getString("Name"));
				_("test"+json.getJSONObject(1));

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {


			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/*
					  Updat`ing parsed JSON data into ListView
					 */
					populateUsersList();
				}
			});

		}

	}

	public void _(String s){
		Log.i("app", "Custom" + "##########" + s);
	}


}
