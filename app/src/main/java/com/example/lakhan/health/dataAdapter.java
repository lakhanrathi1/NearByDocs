package com.example.lakhan.health;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lakhan on 14/2/18.
 */

public class dataAdapter extends ArrayAdapter<Contact> {
    Context context;
    ArrayList<Contact> mcontact;


    public dataAdapter(Context context, ArrayList<Contact> contact){
        super(context, R.layout.listcontacts, contact);
        this.context=context;
        this.mcontact=contact;
    }

    public  class  Holder{
        TextView nameFV;
        TextView rating;
        ImageView pic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listcontacts, parent, false);

            viewHolder.nameFV = (TextView) convertView.findViewById(R.id.txtViewer);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rateView);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.imgView);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }


        viewHolder.nameFV.setText("Name: "+data.getFName());
        viewHolder.rating.setText("Rating: "+data.getRate()+"/5");
        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));


        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}

