package com.lsquare.civicreporter;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Submit extends ListActivity {
    private ProgressDialog pDialog;


    private static String url = "http://www.app.nitinsinhame.net/viewdata1.php";
    /*
                "id":"2",
                "name":"Nitin",
                "city":"",
                "zone":"",
                "area":"Vikas nagar",
                "category":"health",
                "description":"Swine Flu",
                "photo":"",
                "file":"",
                "status":"",
                "date":"2015-03-15 11:18:15"
        */
    private static final String TAG_CONTACTS = " ";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_CITY = "city";
    private static final String TAG_ZONE = "zone";
    private static final String TAG_AREA = "area";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_FILE = "file";
    private static final String TAG_STATUS = "status";
    private static final String TAG_REG_DATE = "date";

    JSONArray json_array = null;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent in = getIntent();
        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();


       /* lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();

                Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_EMAIL, cost);

                startActivity(in);

            }
        });*/


        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Submit.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //@Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();


            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    // JSONObject jsonObj = new JSONObject(jsonStr);


                    json_array = new JSONArray(jsonStr);


                    for (int i = 0; i < json_array.length(); i++) {
                        JSONObject c = json_array.getJSONObject(i);

                        String name = c.getString(TAG_NAME);
                        String id = c.getString(TAG_ID);
                        String city = c.getString(TAG_CITY);
                        String zone = c.getString(TAG_ZONE);
                        String area = c.getString(TAG_AREA);
                        String category = c.getString(TAG_CATEGORY);
                        String description = c.getString(TAG_DESCRIPTION);
                        String status = c.getString(TAG_STATUS);
                        String date = c.getString(TAG_REG_DATE);


                        HashMap<String, String> contact = new HashMap<String, String>();


                        contact.put(TAG_NAME, name);
                        contact.put(TAG_ID, id);
                        contact.put(TAG_CITY, city);
                        contact.put(TAG_ZONE, zone);
                        contact.put(TAG_AREA, area);
                        contact.put(TAG_CATEGORY, category);
                        contact.put(TAG_DESCRIPTION, description);
                        contact.put(TAG_STATUS, status);
                        contact.put(TAG_REG_DATE, date);
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Log.e("ServiceHandler", "Couldn't get any data from the url");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(Submit.this, contactList, R.layout.list_item, new String[]{TAG_ID, TAG_NAME, TAG_CITY, TAG_ZONE, TAG_AREA, TAG_CATEGORY, TAG_DESCRIPTION, TAG_STATUS, TAG_REG_DATE}, new int[]{
                    R.id.name, R.id.id, R.id.city, R.id.area, R.id.zone, R.id.cat, R.id.desc, R.id.status, R.id.reg});

            setListAdapter(adapter);
        }

    }

}
