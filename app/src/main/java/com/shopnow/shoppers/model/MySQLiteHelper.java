package com.shopnow.shoppers.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo pc on 06-09-2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {


    public static final String TABLE_POSITION = "MALL";
    public static final String COLUMN_ID = "MALL_ID";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_NAME =  "MALL_NAME";
    public static final String COLUMN_URI = "URI";
    public static final String COLUMN_ADDRESS = "ADDRESS";


    private static final String DATABASE_NAME = "Mall.db";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MALL = "create table " + TABLE_POSITION + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LATITUDE
            + " REAL NOT NULL, " + COLUMN_LONGITUDE +" REAL NOT NULL, " + COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_URI + ", "+COLUMN_ADDRESS+" TEXT NOT NULL "+ ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_MALL);
    }


    // on delete is not here
    // on update


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        onCreate(db);
    }
}
