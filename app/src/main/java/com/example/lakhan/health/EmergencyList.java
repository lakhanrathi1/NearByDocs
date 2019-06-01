package com.example.lakhan.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EmergencyList extends AppCompatActivity {


    ListView lv;
    ArrayList<String> doctor_name;
    int touch;
  //  private DatabaseHandler db;
    private dataAdapter data;
    private Contact dataModel;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DatabaseHandler db;
    List<Contact> dblist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        Intent i = getIntent();
      //  touch = i.getStringExtra("tag");
       // name = i.getStringExtra("name");
        double lt  = i.getDoubleExtra("lat",0.0);
        double lg = i.getDoubleExtra("log",0.0);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Emergency");
        }


        db = new DatabaseHandler(this);
        dblist = new ArrayList<Contact>();
        dblist = db.getAllContactsem(lg,lt);

        recyclerView = (RecyclerView)findViewById(R.id.recycle1);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new card_adapter(this,dblist);
        recyclerView.setAdapter(adapter);

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
