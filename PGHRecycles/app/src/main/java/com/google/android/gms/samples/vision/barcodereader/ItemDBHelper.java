package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.samples.vision.barcodereader.ItemDatabaseContract;

public class ItemDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemDatabase.db";

    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemDatabaseContract.SQL_CREATE_ITEMS_TABLE);
        db.execSQL(ItemDatabaseContract.SQL_CREATE_LOCATION_MAP_TABLE);
        System.out.println(ItemDatabaseContract.SQL_CREATE_LOCATION_MAP_TABLE);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
