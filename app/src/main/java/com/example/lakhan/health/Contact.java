package com.example.lakhan.health;

/**
 * Created by lakhan on 14/2/18.
 */
public class Contact {


    //private variables
    int _id;
    String fees,cate,eme,time,loca,pri;
    String _fname;
    String rating;
    String contactNo;
    byte[] _img;
    double lat,log;




    // Empty constructor
    public Contact(){

    }

    public Contact(int id, String fname, byte[] img){
        this._id = id;
        this._fname = fname;
        this._img = img;

    }

    // constructor
    public Contact(int id, String fname,String rate,String cat,String feesa,String con,String ti,String emr,String loc,String pria, byte[] img){
        this._id = id;
        this._fname = fname;
        this._img = img;
        this.fees=feesa;
        this.rating=rate;
        this.cate=cat;
        this.contactNo=con;
        this.time=ti;
        this.eme=emr;
        this.loca=loc;
        this.pri=pria;



    }

    // constructor
    public Contact(String fname,String rate,String cat,String feesa,String con,String ti,String emr,String loc,String pria, byte[] img){

        this._fname = fname;
        this._img = img;
        this.fees=feesa;
        this.rating=rate;
        this.cate=cat;
        this.contactNo=con;
        this.time=ti;
        this.eme=emr;
        this.loca=loc;
        this.pri=pria;


    }

    public Contact(String fname,String rate,String cat,String feesa,String con,String ti,String emr,String loc,String pria){

        this._fname = fname;
        //this._img = img;
        this.fees=feesa;
        this.rating=rate;
        this.cate=cat;
        this.contactNo=con;
        this.time=ti;
        this.eme=emr;
        this.loca=loc;
        this.pri=pria;


    }

    // constructor
    public Contact(String fname,String rate,String cat,String feesa,String con,String ti,String emr,String loc,String pria,double logi,double lati, byte[] img){

        this._fname = fname;
        this._img = img;
        this.fees=feesa;
        this.rating=rate;
        this.cate=cat;
        this.contactNo=con;
        this.time=ti;
        this.eme=emr;
        this.loca=loc;
        this.pri=pria;
        this.lat = lati;
        this.log  = logi;


    }




    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }




    public String getRate(){
        return this.rating;
    }

    // setting id
    public void setRate(String rate){
        this.rating = rate;
    }





    public String getcate(){
        return this.cate;
    }

    // setting id
    public void setcate(String cate){
        this.cate = cate;
    }




    public String getFees(){
        return this.fees;
    }

    // setting id
    public void setFees(String fee){
        this.fees = fee;
    }


    public String getcontact(){
        return this.contactNo;
    }

    // setting id
    public void setcontact(String con){
        this.contactNo = con;
    }




    public String getemr(){
        return this.eme;
    }

    // setting id
    public void setemr(String e){
        this.eme = e;
    }



    public String getpri(){
        return this.pri;
    }

    // setting id
    public void setpri(String pr){
        this.pri = pr;
    }


    public String getloc(){
        return this.loca;
    }

    // setting id
    public void setloc(String lo){
        this.loca = lo;
    }


    public String gettime(){
        return this.time;
    }

    // setting id
    public void settime(String tim){
        this.time = tim;
    }


    // getting first name
    public String getFName(){
        return this._fname;
    }

    // setting first name
    public void setFName(String fname){
        this._fname = fname;
    }

    //getting profile pic
    public byte[] getImage(){
        return this._img;
    }

    //setting profile pic

    public void setImage(byte[] b){
        this._img=b;
    }



    public double getlog(){
        return this.log;
    }

    // setting id
    public void setlog(double l){
        this.log = l;
    }

    public double getlat(){
        return this.lat;
    }

    // setting id
    public void setlat(double la){
        this.lat = la;
    }



}
