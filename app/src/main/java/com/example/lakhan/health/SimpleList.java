package com.example.lakhan.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SimpleList extends AppCompatActivity {

    ListView lv;
    ArrayList<String> doctor_name;
    static   String touch,name;
  //  private DatabaseHandler db;
    private dataAdapter data;
    private Contact dataModel;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DatabaseHandler db;
    List<Contact> dblist;
    ActionBar actionBar;


    //  private DatabaseHelper databaseHelper;
   // private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        Intent i = getIntent();
        touch = i.getStringExtra("tag");
        name = i.getStringExtra("name");
        double lt  = i.getDoubleExtra("lat",0.0);
        double lg = i.getDoubleExtra("log",0.0);



        //actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(name);
        }


        //getActionBar().setHomeButtonEnabled(true);


       // touch = i.getExtra("tag");

        db = new DatabaseHandler(this);
        dblist = new ArrayList<Contact>();
        dblist = db.getAllContactsCard_dis(lg,lt,touch);

        recyclerView = (RecyclerView)findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new card_adapter(this,dblist);
        recyclerView.setAdapter(adapter);



       // lv = (ListView)findViewById(R.id.doctorList);
        //ShowRecords();



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
