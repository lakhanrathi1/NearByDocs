package com.example.lakhan.health;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DataBase extends AppCompatActivity {


    private EditText fname, rate, fees, pri, eme, cont, time, lo, cate;
    private ImageView pic;
    private DatabaseHandler db;
    private String f_name, f_rate, f_fees, f_pri, f_eme, f_cont, f_time, f_lo, f_cate;
    private ListView lv;
    private dataAdapter data;
    private Contact dataModel;
    private Bitmap bp;
    private byte[] photo;
    RelativeLayout one ,sec;
    Context context;
    Drawable myDrawable;
    Bitmap anImage;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        context = getApplicationContext();

        db = new DatabaseHandler(this);

        lv = (ListView) findViewById(R.id.list12);
        pic = (ImageView) findViewById(R.id.imageView);
        fname = (EditText) findViewById(R.id.fname);
        fees = (EditText) findViewById(R.id.fees);
        pri = (EditText) findViewById(R.id.pri);
        time = (EditText) findViewById(R.id.time);
        eme = (EditText) findViewById(R.id.emergency);
        rate = (EditText) findViewById(R.id.rate);
        cont = (EditText) findViewById(R.id.contact);
        lo = (EditText) findViewById(R.id.location);
        cate = (EditText) findViewById(R.id.cate);

        one = (RelativeLayout) findViewById(R.id.form);
        sec = (RelativeLayout)findViewById(R.id.listlay);

        sec.setVisibility(View.INVISIBLE);
      //  addContact();


    }



    public void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri choosenImage = data.getData();

                    if (choosenImage != null) {

                        bp = decodeUri(choosenImage, 200);
                        pic.setImageBitmap(bp);
                    }
                }
        }
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    // function to get values from the Edittext and image
    private void getValues() {
/*        f_name = fname.getText().toString();
        f_rate = rate.getText().toString();
        f_fees = fees.getText().toString();
        f_eme = eme.getText().toString();
        f_cate = cate.getText().toString();
        f_time = time.getText().toString();
        f_pri = pri.getText().toString();
        f_lo = lo.getText().toString();
        f_cont = cont.getText().toString();

        //  Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
         //       R.drawable.dentist);
*/

        myDrawable = getResources().getDrawable(R.drawable.neurology);
        if (myDrawable == null )
            Toast.makeText(context, "drawable", Toast.LENGTH_LONG).show();

        anImage      = ((BitmapDrawable) myDrawable).getBitmap();

      // if(anImage!=null)
        photo = profileImage(anImage);


    }

    //Insert data to the database
    public void addContact() {
        getValues();
     //   db.addContacts(new Contact(f_name, f_rate,f_cate,f_fees,f_cont,f_time,f_eme,f_lo,f_pri));
       /* db.addContacts(new Contact("Dr.lakhan", "5","1","400","47878787","10-2","yes","pune","high", photo));

        db.addContacts(new Contact("Dr.lakhan rathi", "4.5","1","400","47878787","10-2","yes","pune","high", photo));
        db.addContacts(new Contact("Dr.vit pune", "6.5","2","500","47878787","10-2","yes","pune","high", photo));
        db.addContacts(new Contact("Dr.ok now", "2.5","3","200","4787454787","10-2","yes","pune","low", photo));
        db.addContacts(new Contact("Dr.final", "5","1","600","4785657","10-2","yes","pune","low", photo));
*/
     //  db.addContacts(new Contact(f_name, f_rate,f_cate,f_fees,f_cont,f_time,f_eme,f_lo,f_pri, photo));
       //
        // Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();
    }

    //Retrieve data from the database and set to the list view
    private void ShowRecords() {
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts1());
        data = new dataAdapter(this, contacts);

        lv.setAdapter(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);

                Toast.makeText(getApplicationContext(), String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void show(View view){

        //   Intent i = new Intent(getApplicationContext(),MainActivity.class);
        // startActivity(i);

        ShowRecords();
        sec.setVisibility(View.VISIBLE);
        one.setVisibility(View.INVISIBLE);
    }
    public void save(View view){

        addContact();
    }
    public void select(View view){

        selectImage();
    }



}
