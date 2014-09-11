package com.shopnow.shoppers.model;


public class Shop {

    private int shop_id;
    private int mall_id;
    private String name;
    private String contact;
    private String uri;
    private String address;

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public long getMall_id() {
        return mall_id;
    }

    public void setMall_id(int mall_id) {
        this.mall_id = mall_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
