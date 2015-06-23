package com.lsquare.civicreporter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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


public class Register extends Activity implements View.OnClickListener {
    SQLiteDatabase db = null;
    Button bsub;
    Spinner sp, sp1;
    EditText name, phone, address, email, pass;
    String name1, phone1, address1, city, ar1, email1, pass1;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sp = (Spinner) findViewById(R.id.spinner);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.editText);
        name.setOnClickListener(this);
        phone.setOnClickListener(this);
        address.setOnClickListener(this);
        sp1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.area1, R.layout.spinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.area2, R.layout.spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.area3, R.layout.spinner);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sp1.setAdapter(adapter1);
                        break;
                    case 1:
                        sp1.setAdapter(adapter2);
                        break;
                    case 2:
                        sp1.setAdapter(adapter3);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // db = openOrCreateDatabase("lsquare", MODE_PRIVATE, null);
        // db.execSQL("create table if not exists login1(city varchar,area varchar,name varchar,phone varchar,address varchar)");
        bsub = (Button) findViewById(R.id.button1);
        bsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = (Spinner) findViewById(R.id.spinner);
                sp1 = (Spinner) findViewById(R.id.spinner2);
                city = sp.getSelectedItem().toString();
                ar1 = sp1.getSelectedItem().toString();
                name1 = name.getText().toString();
                phone1 = phone.getText().toString();
                address1 = address.getText().toString();
                email1 = email.getText().toString();
                pass1 = pass.getText().toString();

                if (name1.trim().equals("")) {
                    Toast.makeText(Register.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else if (phone1.trim().equals("")) {
                    Toast.makeText(Register.this, "Please enter phone no", Toast.LENGTH_SHORT).show();
                } else if (address1.trim().equals("")) {
                    Toast.makeText(Register.this, "Please enter address", Toast.LENGTH_SHORT).show();
                } else {
                    // db.execSQL("insert into login1 values('" + c + "','" + ar1 + "','" + n + "','" + p + "','" + a + "')");
                    //db.close();
                    new PutContacts().execute();
                    name.setText(" ");
                    phone.setText(" ");
                    address.setText(" ");

                }
            }
        });


    }

    private class PutContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected Void doInBackground(Void... arg0) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://app.nitinsinhame.net/User_Register.php");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("email", email1));
                nameValuePairs.add(new BasicNameValuePair("password", pass1));
                nameValuePairs.add(new BasicNameValuePair("name", name1));
                nameValuePairs.add(new BasicNameValuePair("area", ar1));
                nameValuePairs.add(new BasicNameValuePair("city", city));
                nameValuePairs.add(new BasicNameValuePair("address", address1));
                nameValuePairs.add(new BasicNameValuePair("phone", phone1));
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
            Toast.makeText(Register.this, "Registered successfully.", Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name:
                name.setText("");
                break;
            case R.id.phone:
                phone.setText("");
                break;
            case R.id.editText:
                address.setText("");
                break;
            default:
                break;

        }
    }
}
