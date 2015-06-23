package com.lsquare.civicreporter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity2Activity extends Activity {
    ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        img1 = (ImageView) findViewById(R.id.imageButton);
        img2 = (ImageView) findViewById(R.id.imageButton2);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "submit complaint", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity2Activity.this, NewComplaint.class);
                startActivity(i);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "view complaint", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity2Activity.this, Submit.class);
                startActivity(i);
            }
        });

    }


}
