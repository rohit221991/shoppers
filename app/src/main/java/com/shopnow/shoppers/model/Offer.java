package com.shopnow.shoppers.model;


public class Offer {

    private int MALL_ID;
    private int SHOP_ID;
    private int ID;

    public String DESCRIPTION;
    public String TC;
    public String URI;
    public String VALID_FROM;
    public String VALID_TO;

    public int getMALL_ID() {
        return MALL_ID;
    }

    public void setMALL_ID(int MALL_ID) {
        this.MALL_ID = MALL_ID;
    }

    public int getSHOP_ID() {
        return SHOP_ID;
    }

    public void setSHOP_ID(int SHOP_ID) {
        this.SHOP_ID = SHOP_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
    }

    public String getVALID_FROM() {
        return VALID_FROM;
    }

    public void setVALID_FROM(String VALID_FROM) {
        this.VALID_FROM = VALID_FROM;
    }

    public String getVALID_TO() {
        return VALID_TO;
    }

    public void setVALID_TO(String VALID_TO) {
        this.VALID_TO = VALID_TO;
    }
}

