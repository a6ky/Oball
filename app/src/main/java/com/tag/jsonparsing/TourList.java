package com.tag.jsonparsing;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TourList extends ListActivity {

	JSONArray json = null;
	private static final String TAG_NAME = "t_name";
	private static final String TAG_ID = "t_id";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String sName = intent.getStringExtra("func");
		String url = sName;

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		JSONParser jParser = new JSONParser();

		json = jParser.getJSONFromUrl(url);

		if (json != null) {
			try {

				for (int i = 0; i < json.length(); i++) {
					JSONObject c = json.getJSONObject(i);

					HashMap<String, String> map = new HashMap<String, String>();

					String name = c.getString(TAG_NAME);
					String id = c.getString(TAG_ID);

					map.put(TAG_NAME, name);
					map.put(TAG_ID, id);

					contactList.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else
		{
			JSONObject json1 = jParser.getJSONFrObj(url);
			try {
				String err_msg = json1.getString("error_message");
				Toast.makeText(this,err_msg, Toast.LENGTH_LONG).show();

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		if (contactList.size() != 0) {

			ListAdapter adapter = new SimpleAdapter(this, contactList,
					R.layout.list_item,
					new String[]{TAG_NAME}, new int[]{
					R.id.name});

			setListAdapter(adapter);

			ListView lv = getListView();

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();

					HashMap<String, String> map = (HashMap<String, String>) getListAdapter().getItem(position);

					Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
					in.putExtra(TAG_NAME, map.get(TAG_NAME));
					in.putExtra(TAG_ID, map.get(TAG_ID));
					startActivity(in);

				}

			});
		} else {
			Intent in = new Intent(TourList.this, SearchActivity.class);
			startActivity(in);
		}


	}

}