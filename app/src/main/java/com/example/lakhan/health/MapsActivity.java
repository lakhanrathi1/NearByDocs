package com.example.lakhan.health;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener, LocationListener {


    private GoogleMap mMap;
    Marker marker;
    int location = -1;
    LocationManager locationManager;
    String provider;
    MarkerOptions options;
    static Double lat;
    static Double log ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i  = getIntent();
        lat = i.getDoubleExtra("lat",0.0);
        log = i.getDoubleExtra("log",0.0);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        Toast.makeText(getApplicationContext(),"To change location press on location for a sec",Toast.LENGTH_LONG).show();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        options = new MarkerOptions()
                .title("You")
                .position(new LatLng(lat,
                        log));
        moveToCurrentLocation(new LatLng(lat, log));

        marker = mMap.addMarker(options);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(lat, log);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (location == -1 && location == 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }






    @Override
    public void onLocationChanged(Location location) {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 10));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder =
                new Geocoder(MapsActivity.this);
        List<Address> list;
        try {
            list = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude, 1);
            Main2Activity.lat = latLng.latitude;
            Main2Activity.log = latLng.longitude;
        } catch (IOException e) {
            return;
        }
        Address address = list.get(0);
        String add = list.get(0).getAddressLine(0);
        String ok = list.get(0).getSubLocality();
        String city = list.get(0).getLocality();

        String final_loc = ""+ok+"," +city;
        Main2Activity.my.setText(final_loc);

        if (marker != null) {
            marker.remove();
        }

      options = new MarkerOptions()
                .title(address.getLocality())
                .position(new LatLng(latLng.latitude,
                        latLng.longitude));

        marker = mMap.addMarker(options);
        Toast.makeText(getApplicationContext(),"Your location changed to:"+final_loc,Toast.LENGTH_LONG).show();
    }



    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);


    }

}

