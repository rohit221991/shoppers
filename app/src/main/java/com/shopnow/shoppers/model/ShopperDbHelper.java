package com.shopnow.shoppers.model;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShopperDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weather.db";

    public ShopperDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_MALL_TABLE =
                "CREATE TABLE " + ShopperContract.MallEntry.TABLE_NAME + " ("
                                + ShopperContract.MallEntry.COLUMN_ID  +  " INTEGER PRIMARY KEY ON CONFLICT REPLACE, "
                                + ShopperContract.MallEntry.COLUMN_LATITUDE + " REAL NOT NULL, "
                                + ShopperContract.MallEntry.COLUMN_LONGITUDE + " REAL NOT NULL, "
                                + ShopperContract.MallEntry.COLUMN_NAME + " TEXT NOT NULL, "
                                + ShopperContract.MallEntry.COLUMN_URI + ", "
                                + ShopperContract.MallEntry.COLUMN_ADDRESS + " TEXT NOT NULL " + ");";


        final String CREATE_SHOP_TABLE =
                "CREATE TABLE " + ShopperContract.ShopEntry.TABLE_NAME + " ("
                                + ShopperContract.ShopEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                                + ShopperContract.ShopEntry.COLUMN_MALL_ID + " INTEGER NOT NULL, "
                                + ShopperContract.ShopEntry.COLUMN_NAME + " TEXT NOT NULL, "
                                + ShopperContract.ShopEntry.COLUMN_CONTACT + " TEXT NOT NULL, "
                                + ShopperContract.ShopEntry.COLUMN_URI + ", "
                                + ShopperContract.ShopEntry.COLUMN_ADDRESS + " TEXT NOT NULL, "
                                + ShopperContract.ShopEntry.COLUMN_TIMING + " TEXT"
                                + ");";

        final String CREATE_OFFER_TABLE =
                "CREATE TABLE " + ShopperContract.OfferEntry.TABLE_NAME + " ("
                                + ShopperContract.OfferEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                                + ShopperContract.OfferEntry.COLUMN_MALL_ID + " INTEGER NOT NULL, "
                                + ShopperContract.OfferEntry.COLUMN_SHOP_ID + " INTEGER NOT NULL, "
                                + ShopperContract.OfferEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                                + ShopperContract.OfferEntry.COLUMN_TC + " TEXT NOT NULL, "
                                + ShopperContract.OfferEntry.COLUMN_URI + ", "
                                + ShopperContract.OfferEntry.COLUMN_VALID_FROM + " TEXT NOT NULL, "
                                + ShopperContract.OfferEntry.COLUMN_VALID_TO + " TEXT NOT NULL" + ");";

        final String CREATE_CATEGORY_TABLE =
                "CREATE TABLE " + ShopperContract.CategoryEntry.TABLE_NAME + " ("
                                + ShopperContract.CategoryEntry.COLUMN_MALL_ID + " INTEGER NOT NULL, "
                                + ShopperContract.CategoryEntry.COLUMN_SHOP_ID + " INTEGER NOT NULL, "
                                + ShopperContract.CategoryEntry.COLUMN_CATEGORY + " TEXT NOT NULL " + ");";


        sqLiteDatabase.execSQL(CREATE_MALL_TABLE);
        sqLiteDatabase.execSQL(CREATE_SHOP_TABLE);
        sqLiteDatabase.execSQL(CREATE_OFFER_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        database.execSQL("DROP TABLE IF EXISTS " + ShopperContract.ShopEntry.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + ShopperContract.MallEntry.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + ShopperContract.OfferEntry.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + ShopperContract.CategoryEntry.TABLE_NAME);
        onCreate(database);
    }
}
