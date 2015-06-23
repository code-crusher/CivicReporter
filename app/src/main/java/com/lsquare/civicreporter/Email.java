package com.lsquare.civicreporter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import everything you need


public class Email extends Activity {

    // Button sendButton;
    private ProgressDialog pDialog;
    TextView msgTextField, hg, jh;
    Double latitude, longitude;
    String lati, longi;

    /**
     * Called when the activity is first created.
     */
    String a, c, d, city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.activity_email);

        // make message text field object
        msgTextField = (TextView) findViewById(R.id.textView1);
        hg = (TextView) findViewById(R.id.textView2);
        jh = (TextView) findViewById(R.id.textView3);
        // make send button object
        // sendButton = (Button) findViewById(R.id.sendButton);
        Intent in = getIntent();
        a = in.getStringExtra("Area");
        c = in.getStringExtra("Cat");
        d = in.getStringExtra("Info");
        city = in.getStringExtra("City");

        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("lat");
        longitude = bundle.getDouble("long");

        lati = String.valueOf(latitude);
        longi = String.valueOf(longitude);

        msgTextField.setText(a);
        hg.setText(c);
        jh.setText(d);
        new GetContacts().execute();
    }
    //}

    // this is the function that gets called when you click the button
    // public void send(View v)
    // {
    // get the message from the message text box
    //String msg = msgTextField.getText().toString();

    // make sure the fields are not empty
    // if (msg.length()>0)
    //{
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Email.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected Void doInBackground(Void... arg0) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://app.nitinsinhame.net/email.php");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("uname", "sachin"));
                nameValuePairs.add(new BasicNameValuePair("area", a));
                nameValuePairs.add(new BasicNameValuePair("zone", " "));
                nameValuePairs.add(new BasicNameValuePair("city", city));
                nameValuePairs.add(new BasicNameValuePair("category", c));
                nameValuePairs.add(new BasicNameValuePair("description", d));
                nameValuePairs.add(new BasicNameValuePair("email", "sachinmalik413@gmail.com"));
                nameValuePairs.add(new BasicNameValuePair("lat", lati));
                nameValuePairs.add(new BasicNameValuePair("long", longi));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpclient.execute(httppost);
                //msgTextField.setText(""); // clear text box
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }


            // }
       /*else
        {
            // display message if text fields are empty
            Toast.makeText(getBaseContext(),"All field are required",Toast.LENGTH_SHORT).show();
        }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();


        }
    }

}