package com.example.lakhan.health;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lakhan on 19/2/18.
 */

public class card_adapter extends RecyclerView.Adapter<card_adapter.MyViewHolder> {


    private Context mContext;
    private List<Contact> albumList;

    public card_adapter(Context context, List<Contact> dblist) {

        this.albumList = new ArrayList<Contact>();
        this.mContext = context;
        this.albumList = dblist;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_viewfinal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Contact contact = albumList.get(position);
        holder.title.setText(contact.getFName());
        holder.count.setText(contact.getloc());
        holder.thumbnail.setImageBitmap(convertToBitmap(contact.getImage()));
        holder.overflow.setText(contact.getcontact());
        holder.fess.setText(contact.getFees());
        holder.time1.setText(contact.gettime());

        holder.my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel:" + contact.getcontact();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(number));
                mContext.startActivity(intent);

            }
        });



    }

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, overflow,fess,time1;
        public ImageView thumbnail;
        public Button my;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.Card_doctorName);
            count = (TextView) view.findViewById(R.id.Card_doctorLocation);
            thumbnail = (ImageView) view.findViewById(R.id.card_doctorImage);
            overflow = (TextView) view.findViewById(R.id.card_doctorNo);
            fess = (TextView)view.findViewById(R.id.card_doctorFees);
            time1 = (TextView)view.findViewById(R.id.card_doctorTime);
            my = (Button)view.findViewById(R.id.Callbutton);



        }
    }

}
