package com.example.lakhan.health;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakhan on 14/2/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private DataBase db;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contact";

    Contact contact;

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_RATING = "frate";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_POTO = "poto";
    private static final String KEY_CATE = "cate";
    private static final String KEY_FEES = "fees";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_TIME = "time";
    private static final String KEY_EMR = "emr";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_PRI = "pri";
    private static final String KEY_LOG = "log";
    private static final String KEY_LAT = "lat";

    double lat1,log1;
    String emer = "yes";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_FNAME +" TEXT,"
                + KEY_RATING +" TEXT,"
                + KEY_CATE +" TEXT,"
                + KEY_FEES +" TEXT,"
                + KEY_CONTACT +" TEXT,"
                + KEY_TIME +" TEXT,"
                + KEY_EMR +" TEXT,"
                + KEY_LOCATION +" TEXT,"
                + KEY_PRI +" TEXT,"
                + KEY_LOG +" DOUBLE,"
                + KEY_LAT +" DOUBLE,"
                + KEY_POTO  +" BLOB"+ ")";
        db.execSQL(CREATE_TABLE_CONTACTS);



    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addContacts(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_RATING,contact.getRate());
        values.put(KEY_CATE,contact.getcate());
        values.put(KEY_FEES, contact.getFees());
        values.put(KEY_CONTACT, contact.getcontact());
        values.put(KEY_TIME, contact.gettime());
        values.put(KEY_EMR, contact.getemr());
        values.put(KEY_LOCATION, contact.getloc());
        values.put(KEY_PRI, contact.getpri());
        values.put(KEY_POTO, contact.getImage() );
        values.put(KEY_LOG,contact.getlog());
        values.put(KEY_LAT,contact.getlat());


        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    /**
     *Updating single contact
     **/

    public int updateContact(Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_RATING, contact.getRate());
        values.put(KEY_POTO, contact.getImage());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     *Deleting single contact
     **/

    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

    //Method which return card===================================
    public Contact showCard(String touch) {
        Contact contact = new Contact();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE id = '"+ touch + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contact.setFName(cursor.getString(1));
                contact.setFees(cursor.getString(4));
                contact.setImage(cursor.getBlob(10));
                contact.setcontact(cursor.getString(5));
                contact.setloc(cursor.getString(8));
                contact.settime(cursor.getString(6));


            } while (cursor.moveToNext());
        }

        return contact;
    }


    //Method which return list emerency  wise===================================
    public List<Contact> getAllContactsem(double lo , double la) {

        lat1 = la;
        log1 = lo;
        List<Contact> contactList = new ArrayList<Contact>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE emr = '"+ emer + "' AND pri = 'high'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {

                double log2 = cursor.getDouble(10);
                double lat2 = cursor.getDouble(11);


                if (distance(lat1, log1, lat2, log2) < 1) {

                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setFName(cursor.getString(1));
                    //contact.setRate(cursor.getString(2));
                    contact.setImage(cursor.getBlob(12));
                    contact.settime(cursor.getString(6));
                    contact.setFees(cursor.getString(4));
                    contact.setcontact(cursor.getString(5));
                    contact.setloc(cursor.getString(8));

                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }


        cursor.close();

        // Select All Query
        selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE emr = '"+ emer + "' AND pri = 'low'";

        //  SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {


                double log2 = cursor.getDouble(10);
                double lat2 = cursor.getDouble(11);



                if (distance(lat1, log1, lat2, log2) < 1) {

                    Contact contact = new Contact();

                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setFName(cursor.getString(1));
                    //contact.setRate(cursor.getString(2));
                    contact.setImage(cursor.getBlob(12));
                    contact.settime(cursor.getString(6));
                    contact.setFees(cursor.getString(4));
                    contact.setcontact(cursor.getString(5));
                    contact.setloc(cursor.getString(8));

                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return contactList;


    }







    public List<Contact> getAllContactsCard_dis (double lo,double la,String touch){

        lat1 = la;
        log1 = lo;
        List<Contact> contactList = new ArrayList<Contact>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE cate = '"+ touch + "' AND pri = 'high'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {

                double log2 = cursor.getDouble(10);
                double lat2 = cursor.getDouble(11);


                if (distance(lat1, log1, lat2, log2) < 1) {

                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setFName(cursor.getString(1));
                    //contact.setRate(cursor.getString(2));
                    contact.setImage(cursor.getBlob(12));
                    contact.settime(cursor.getString(6));
                    contact.setFees(cursor.getString(4));
                    contact.setcontact(cursor.getString(5));
                    contact.setloc(cursor.getString(8));

                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }


        cursor.close();

        // Select All Query
         selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE cate = '"+ touch + "' AND pri = 'low'";

      //  SQLiteDatabase db = this.getWritableDatabase();
         cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {


                double log2 = cursor.getDouble(10);
                double lat2 = cursor.getDouble(11);



                if (distance(lat1, log1, lat2, log2) < 1) {

                    Contact contact = new Contact();

                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setFName(cursor.getString(1));
                    //contact.setRate(cursor.getString(2));
                    contact.setImage(cursor.getBlob(12));
                    contact.settime(cursor.getString(6));
                    contact.setFees(cursor.getString(4));
                    contact.setcontact(cursor.getString(5));
                    contact.setloc(cursor.getString(8));

                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return contactList;

    }





    //Method which return complete list===================================
    public List<Contact> getAllContacts1() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFName(cursor.getString(1));
                contact.setRate(cursor.getString(2));
                contact.setImage(cursor.getBlob(10));



                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    private double distance(double lat1, double log1, double lat2, double log2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(log2-log1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }


}
