package com.shopnow.shoppers.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MallDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_LATITUDE,
            MySQLiteHelper.COLUMN_LONGITUDE,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_ADDRESS,
            MySQLiteHelper.COLUMN_URI

    };

    //constructor
    public MallDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    //adding new mall to database

    public long addMallToDatabase( double longitude, double latitude , String address, String mall_name,String uri){
        Log.d(getClass().getSimpleName(), " INSERT LOCATION REQUEST");
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_LATITUDE , latitude);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);
        values.put(MySQLiteHelper.COLUMN_ADDRESS , address);
        values.put(MySQLiteHelper.COLUMN_NAME, mall_name);
        values.put(MySQLiteHelper.COLUMN_URI, uri);
        return database.insert(MySQLiteHelper.TABLE_POSITION, null, values);
    }

    public List<Mall> getAllSavedMall() {
        List<Mall> malls = new ArrayList<Mall>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POSITION,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mall mall = convertCursorToMall(cursor);
            malls.add(mall);
            cursor.moveToNext();
        }
        cursor.close();
        return malls;
    }


    private Mall convertCursorToMall(Cursor cursor) {
        Mall mall = new Mall();
        mall.setMall_id(cursor.getLong(0));
        mall.setLatitude(new String(cursor.getString(1)));
        mall.setLongitude(new String(cursor.getString(2)));
        mall.setName(cursor.getString(3));
        mall.setUri(new String(cursor.getString(4)));
        mall.setAddress(cursor.getString(5));
        return mall;
    }

// delete the mall

    public void deleteMall(Mall mall) {
        long id = mall.getMall_id();
        System.out.println("Mall deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_POSITION, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Mall createMall( double longitude, double latitude, String address, String name,String uri) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME,name );
        values.put(MySQLiteHelper.COLUMN_LONGITUDE,longitude );
        values.put(MySQLiteHelper.COLUMN_LATITUDE,latitude );
        values.put(MySQLiteHelper.COLUMN_ADDRESS,address );
        values.put(MySQLiteHelper.COLUMN_URI,uri);
        long insertId = database.insert(MySQLiteHelper.TABLE_POSITION, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POSITION,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Mall newMall = convertCursorToMall(cursor);
        cursor.close();
        return newMall;
    }



}
