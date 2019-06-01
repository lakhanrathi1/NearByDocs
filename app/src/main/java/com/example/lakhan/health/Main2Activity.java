package com.example.lakhan.health;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    static TextView my;
    Geocoder geocoder;
    List<Address> addresses;
    static final int REQUEST_LOCATION = 1;
    static double lat;
    DataBase d;
    static double log ;
    private DatabaseHandler db;
    private byte[] photo;

    LocationManager locationManager;
    private FirebaseAuth mAuth;
    GPS gps;
    public static ProgressDialog mlogprogress;

    ImageView changeLocation;
    Drawable myDrawable;
    Bitmap anImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();

        db = new DatabaseHandler(this);
        my = (TextView)findViewById(R.id.ok);

        mlogprogress = new ProgressDialog(this);


        myDrawable = getResources().getDrawable(R.drawable.neurology);
        if (myDrawable == null )
            Toast.makeText(getApplicationContext(), "drawable", Toast.LENGTH_LONG).show();

        anImage      = ((BitmapDrawable) myDrawable).getBitmap();

        addContact();

        gps = new GPS(Main2Activity.this);


        changeLocation = (ImageView) findViewById(R.id.change_locationButto);
       changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
               i.putExtra("lat",lat);
                i.putExtra("log",log);

                startActivity(i);

            }
        });

    }
    public void doctor_list(View view){

                ImageView t = (ImageView) view;
                String touch = t.getTag().toString();

                Intent i = new Intent(getApplicationContext(),SimpleList.class);

        i.putExtra("tag",touch);
       i.putExtra("name",touch);
        i.putExtra("log",log);
        i.putExtra("lat",lat);

        startActivity(i);

    }


    @Override
    protected void onResume() {
        super.onResume();
        gps.getLocation();
        if(gps.canGetLocation()) {

            mlogprogress = new ProgressDialog(this);
            mlogprogress.setTitle("Getting your location");
            mlogprogress.setMessage("Thank You For Patient");
            mlogprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mlogprogress.setCanceledOnTouchOutside(false);
            mlogprogress.show();

            lat = gps.getLatitude();
            log = gps.getLongitude();
            Log.i("INnnnnnnnnnn",""+lat);
            Log.i("INnnnnnnnnnn",""+log);



        }else {
            gps.showSettingsAlert();
        }


        geocoder =new Geocoder(this, Locale.getDefault());
        try {


            addresses = geocoder.getFromLocation(lat,log,1);

            String add = addresses.get(0).getAddressLine(0);
            String ok = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();

            String final_loc = ""+ok+"," +city;
          //  Toast.makeText(getApplicationContext(),""+lat+" "+log,Toast.LENGTH_LONG).show();
            my.setText(final_loc);
            mlogprogress.dismiss();
            mlogprogress.hide();
        }catch (Exception e){

            e.printStackTrace();
        }




    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainactivity,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.refreshButton){
            super.onResume();
            //this.onCreate(null);


            finish();
          startActivity(getIntent());


        }
        if(item.getItemId()==R.id.exitButton){

            exit_fun();

        }
        if (item.getItemId() == R.id.signOutButton){

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

            finish();
        }






        return true ;



    }


    public void onBackPressed() {

        exit_fun();

    }



    public void exit_fun(){


        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure")
                .setMessage("Do you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();




    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentuser = mAuth.getCurrentUser();


        if (currentuser == null){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }

        }


    public void emergency_click(View view){

        Intent i = new Intent(getApplicationContext(),EmergencyList.class);
        i.putExtra("log",log);
        i.putExtra("lat",lat);

        startActivity(i);



    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

    private void getValues() {

        photo = profileImage(anImage);


    }

    public void addContact() {
        getValues();
      // db.addContacts(new Contact("Dr.lakhan", "5","Cardiologist","400","47878787","10-2","yes","pune","high", photo));
        db.addContacts(new Contact("Dr.lakhan", "5","Cardiologist","400","47878787","10-2","yes","pune","low",73.865740,18.470207, photo));
        db.addContacts(new Contact("Dr.lakhan rathi", "5","Cardiologist","400","47878787","10-2","no","pune","high",73.863959,18.471041, photo));


        db.addContacts(new Contact("Dr.lakhan vit", "4.5","Cardiologist","400","47878787","10-2","no","pune","high",73.865740,18.470207, photo));
        db.addContacts(new Contact("Dr.vit pune", "6.5","Dentist","500","47878787","10-2","yes","pune","high",73.864002,18.468395, photo));
        db.addContacts(new Contact("Dr.ok now", "2.5","General Surgeon","200","4787454787","10-2","yes","pune","low",73.869538,18.468538, photo));
        db.addContacts(new Contact("Dr.final", "5","Cardiologist","600","4785657","10-2","yes","pune","low",73.863423,18.472303, photo));


        db.addContacts(new Contact("Dr.final dita", "5.6","Cardiologist","600","4785657","10-2","yes","pune","low",73.856170,18.472730, photo));
        db.addContacts(new Contact("Dr.my", "5.8","Dentist","600","4785657","10-2","yes","pune","high",73.875268,18.475335, photo));
        db.addContacts(new Contact("Dr.finaldata", "8","General Surgeon","600","4785657","10-2","yes","pune","low",73.862050,18.478164, photo));

    }





}

