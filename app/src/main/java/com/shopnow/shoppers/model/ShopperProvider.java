package com.shopnow.shoppers.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ShopperProvider {

    private SQLiteDatabase database;
    private ShopperDbHelper dbHelper;


    public ShopperProvider(Context context){
        dbHelper = new ShopperDbHelper(context);
    }

    public void openDatabase() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    public long addMallEntryToDatabase( long ID, double longitude, double latitude , String address, String mall_name, String uri ){
        Log.d(getClass().getSimpleName(), " INSERT MALL ENTRY REQUEST");
        ContentValues values = new ContentValues();
        values.put(ShopperContract.MallEntry.COLUMN_ID, ID);
        values.put(ShopperContract.MallEntry.COLUMN_LATITUDE, latitude);
        values.put(ShopperContract.MallEntry.COLUMN_LONGITUDE, longitude);
        values.put(ShopperContract.MallEntry.COLUMN_ADDRESS, address);
        values.put(ShopperContract.MallEntry.COLUMN_NAME, mall_name);
        values.put(ShopperContract.MallEntry.COLUMN_URI, uri);
        return database.insert(ShopperContract.MallEntry.TABLE_NAME, null, values);
    }

    public long addShopEntryToDatabase( long ID, long MALL_ID, String address, String shop_name, String uri, String contact, String timing ){
        Log.d(getClass().getSimpleName(), " INSERT SHOP ENTRY REQUEST");
        ContentValues values = new ContentValues();
        values.put(ShopperContract.ShopEntry.COLUMN_ID, ID);
        values.put(ShopperContract.ShopEntry.COLUMN_MALL_ID, MALL_ID);
        values.put(ShopperContract.ShopEntry.COLUMN_ADDRESS, address);
        values.put(ShopperContract.ShopEntry.COLUMN_NAME, shop_name);
        values.put(ShopperContract.ShopEntry.COLUMN_URI, uri);
        values.put(ShopperContract.ShopEntry.COLUMN_CONTACT, contact);
        values.put(ShopperContract.ShopEntry.COLUMN_TIMING, timing);
        return database.insert(ShopperContract.ShopEntry.TABLE_NAME, null, values);
    }

    public long addOfferEntryToDatabase( long MALL_ID, long SHOP_ID, long offer_id, String description, String TC, String uri, String from, String to){
        Log.d(getClass().getSimpleName(), " INSERT OFFER ENTRY REQUEST");
        ContentValues values = new ContentValues();
        values.put(ShopperContract.OfferEntry.COLUMN_ID, offer_id);
        values.put(ShopperContract.OfferEntry.COLUMN_MALL_ID, MALL_ID);
        values.put(ShopperContract.OfferEntry.COLUMN_SHOP_ID, SHOP_ID);
        values.put(ShopperContract.OfferEntry.COLUMN_DESCRIPTION, description);
        values.put(ShopperContract.OfferEntry.COLUMN_TC, TC);
        values.put(ShopperContract.OfferEntry.COLUMN_URI, uri);
        values.put(ShopperContract.OfferEntry.COLUMN_VALID_FROM, from);
        values.put(ShopperContract.OfferEntry.COLUMN_VALID_TO, to);
        return database.insert(ShopperContract.OfferEntry.TABLE_NAME, null, values);
    }


    public long addCategoryEntryToDatabase( int MALL_ID, int SHOP_ID,  String category ){
        Log.d(getClass().getSimpleName(), " INSERT CATEGORY ENTRY REQUEST");
        ContentValues values = new ContentValues();
        values.put(ShopperContract.CategoryEntry.COLUMN_MALL_ID, MALL_ID);
        values.put(ShopperContract.CategoryEntry.COLUMN_SHOP_ID, SHOP_ID);
        values.put(ShopperContract.CategoryEntry.COLUMN_CATEGORY, category);
        return database.insert(ShopperContract.CategoryEntry.TABLE_NAME, null, values);
    }

    public List<Mall> getMallList(){
        List<Mall> malls = new ArrayList<Mall>();
        Cursor cursor = database.query(ShopperContract.MallEntry.TABLE_NAME,
                ShopperContract.MallEntry.getMallColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mall mall = convertCursorToMall(cursor);
            malls.add(mall);
            cursor.moveToNext();
        }
        cursor.close();
        return malls;
    }

    public List<Shop> getShopList(){
        List<Shop> shops = new ArrayList<Shop>();
        Cursor cursor = database.query(ShopperContract.ShopEntry.TABLE_NAME,
                ShopperContract.ShopEntry.getShopColumns(), null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Shop shop = convertCursorToShop(cursor);
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    // implement it
    public List<Shop> getShopListInAMall( int MALL_ID){
        List<Shop> shops = new ArrayList<Shop>();
        String query = "SELECT * FROM " + ShopperContract.ShopEntry.TABLE_NAME + " WHERE " + ShopperContract.ShopEntry.COLUMN_MALL_ID + " = " + MALL_ID;
        Cursor cursor = database.rawQuery(query , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Shop shop = convertCursorToShop(cursor);
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    public List<Offer> getAllOffers( int MALL_ID, int SHOP_ID){
        List<Offer> offers = new ArrayList<Offer>();
        String query = "SELECT * FROM " + ShopperContract.OfferEntry.TABLE_NAME + " WHERE " + ShopperContract.OfferEntry.COLUMN_MALL_ID + " = " + MALL_ID + " AND " + ShopperContract.OfferEntry.COLUMN_SHOP_ID + " = " + SHOP_ID;
        Cursor cursor = database.rawQuery(query , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Offer offer = convertCursorToOffer(cursor);
            offers.add(offer);
            cursor.moveToNext();
        }
        cursor.close();
        return offers;
    }


    private Offer convertCursorToOffer(Cursor cursor){
       Offer offer = new Offer();
        offer.setMALL_ID(cursor.getInt(1));
        offer.setSHOP_ID(cursor.getInt(2));
        offer.setID(cursor.getInt(0));
        offer.setDESCRIPTION(cursor.getString(3));
        offer.setTC(cursor.getString(4));
        offer.setVALID_FROM(cursor.getString(6));
        offer.setVALID_TO(cursor.getString(7));
        return offer;
    }


    private Mall convertCursorToMall(Cursor cursor) {
        Mall mall = new Mall();
        mall.setMall_id(cursor.getInt(0));
        mall.setLatitude(new String(cursor.getString(1)));
        mall.setLongitude(new String(cursor.getString(2)));
        mall.setName(cursor.getString(3));
        mall.setUri(new String(cursor.getString(4)));
        mall.setAddress(cursor.getString(5));
        return mall;
    }

    private Shop convertCursorToShop(Cursor cursor) {
        Shop shop = new Shop();
        shop.setShop_id(cursor.getInt(0));
        shop.setMall_id(cursor.getInt(1));
        shop.setName(cursor.getString(2));
        shop.setContact(cursor.getString(3));
        shop.setUri(cursor.getString(4));
        shop.setAddress(cursor.getString(5));
        shop.setTiming(cursor.getString(6));
        return shop;
    }

    // complete it
    public void deleteMallEntryFromDatabase( int id) {
        database.delete(ShopperContract.MallEntry.TABLE_NAME, ShopperContract.MallEntry.COLUMN_ID + " = " + id, null);
    }



}
