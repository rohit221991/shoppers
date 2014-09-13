package com.shopnow.shoppers.model;


public class ShopperContract {

    public static final class MallEntry{

        public static final String TABLE_NAME = "MALL";
        public static final String COLUMN_ID = "MALL_ID";
        public static final String COLUMN_LATITUDE = "LATITUDE";
        public static final String COLUMN_LONGITUDE = "LONGITUDE";
        public static final String COLUMN_NAME =  "MALL_NAME";
        public static final String COLUMN_URI = "URI";
        public static final String COLUMN_ADDRESS = "ADDRESS";

        public static String[] getMallColumns(){
            String[] Columns = {COLUMN_ID,COLUMN_LATITUDE,COLUMN_LONGITUDE,COLUMN_NAME,COLUMN_URI,COLUMN_ADDRESS};
            return Columns;
        }

    }

    public static final class ShopEntry{

        public static final String TABLE_NAME = "SHOPS";
        public static final String COLUMN_ID = "SHOP_ID";
        public static final String COLUMN_MALL_ID = "MALL_ID";
        public static final String COLUMN_NAME = "SHOP_NAME";
        public static final String COLUMN_CONTACT = "CONTACT";
        public static final String COLUMN_URI = "URI";
        public static final String COLUMN_ADDRESS = "ADDRESS";
        public static final String COLUMN_TIMING = "TIMING";

        public static String[] getShopColumns(){
             String[] columns = {COLUMN_ID,COLUMN_MALL_ID,COLUMN_NAME,COLUMN_CONTACT,COLUMN_URI,COLUMN_ADDRESS,COLUMN_TIMING};
            return columns;
        }
    }


    public static final class OfferEntry{

        public static final String TABLE_NAME = "OFFERS";
        public static final String COLUMN_ID = "OFFER_ID";
        public static final String COLUMN_MALL_ID = "MALL_ID";
        public static final String COLUMN_SHOP_ID = "SHOP_ID";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_TC = "TC";
        public static final String COLUMN_URI = "URI";
        public static final String COLUMN_VALID_FROM = "FromTime";
        public static final String COLUMN_VALID_TO = "ToTime";
    }

    public static final class CategoryEntry{

        public static final String TABLE_NAME = "CATEGORY";
        public static final String COLUMN_MALL_ID = "MALL_ID";
        public static final String COLUMN_SHOP_ID = "SHOP_ID";
        public static final String COLUMN_CATEGORY = "CATEGORY";
    }
}


