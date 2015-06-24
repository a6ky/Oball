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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VkGamesParsing extends ListActivity {

    private static final String TAG_NAME = "t_name";
    private static final String TAG_ID = "trn_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String sName = intent.getStringExtra("func");

        String url = sName;

        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        JSONParser jParser = new JSONParser();

        JSONArray json = jParser.getJSONFromUrl(url);

        try {

            for(int i = 0; i < json.length(); i++) {
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

        ListAdapter adapter = new SimpleAdapter(this, contactList,
                R.layout.list_item,
                new String[] { TAG_NAME }, new int[] {
                R.id.name});

        setListAdapter(adapter);

        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>)getListAdapter().getItem(position);
                Intent in = new Intent(getApplicationContext(),SingleVk.class);
                in.putExtra(TAG_NAME, map.get(TAG_NAME));
                in.putExtra(TAG_ID, map.get(TAG_ID));
                startActivity(in);

            }
        });
    }
}