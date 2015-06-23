package com.lsquare.civicreporter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by vatsal on 3/9/2015.
 */
public class MapActivity extends FragmentActivity {

    Location location;
    GoogleMap mMap;
    String area, cat, info, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getActionBar().hide();

        Intent in = getIntent();
        area = in.getStringExtra("Area");
        city = in.getStringExtra("City");
        cat = in.getStringExtra("Cat");
        info = in.getStringExtra("Info");

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            setUpMapLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Network not Available", Toast.LENGTH_LONG).show();

        }


    }

    public void submitMap(View v) {

        Bundle bundle = new Bundle();
        bundle.putDouble("lat", location.getLatitude());
        bundle.putDouble("long", location.getLongitude());


        Intent intent = new Intent(this, Email.class);
        intent.putExtra("Area", area);
        intent.putExtra("Cat", cat);
        intent.putExtra("City", city);
        intent.putExtra("Info", info);
        intent.putExtras(bundle);

        startActivity(intent);
        //store the location
    }

    private void setUpMapLocation() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                Toast.makeText(getApplicationContext(), "Loading Location....", Toast.LENGTH_LONG).show();
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub
                        location = arg0;
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("My Complaint"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 17));

                    }
                });
            }

        }
    }

}
