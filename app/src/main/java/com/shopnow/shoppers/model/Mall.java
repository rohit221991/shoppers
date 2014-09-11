package com.shopnow.shoppers.model;


public class Mall {

    private long mall_id;
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private String uri;


    // getter methods

    public long getMall_id(){return mall_id;}
    public String getName(){return name;}
    public double getLatitude(){return  latitude;};
    public double getLongitude(){return longitude;}
    public String getUri(){return uri;}
    public String getAddress(){return address;}


    // setter methods

    public void setMall_id(long mall_id){this.mall_id=mall_id;}
    public void setName(String name){this.name=name;}
    public void setLatitude(String latitude ){this.latitude=Double.parseDouble(latitude);}
    public void setLongitude(String longitude){this.longitude=Double.parseDouble(longitude); }
    public void setAddress(String address){this.address=address;}
    public void setUri(String uri){this.uri=uri;}



    public String toString(){return mall_id+","+name+","+latitude+","+longitude;}


}
