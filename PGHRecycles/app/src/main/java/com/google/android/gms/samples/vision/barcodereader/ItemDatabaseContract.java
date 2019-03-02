package com.google.android.gms.samples.vision.barcodereader;

import android.provider.BaseColumns;

public final class ItemDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ItemDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class Items implements BaseColumns {
        public static final String TABLE_NAME = "Items";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_TYPE_ID = "type_id";
    }

    public static class LocationMap implements BaseColumns {
        public static final String TABLE_NAME = "LocationMap";
        public static final String COLUMN_NAME_MUNICIPALITY = "municipality";
        public static final String COLUMN_NAME_TYPE_ID = "type_id";
    }

    public static final String SQL_CREATE_ITEMS_TABLE =
            "CREATE TABLE " + Items.TABLE_NAME + " (" +
                    Items.COLUMN_NAME_BARCODE + " TEXT PRIMARY KEY, " +
                    Items.COLUMN_NAME_TYPE_ID + " INTEGER)";

    public static final String SQL_CREATE_LOCATION_MAP_TABLE =
            "CREATE TABLE " + LocationMap.TABLE_NAME + " (" +
                    LocationMap.COLUMN_NAME_MUNICIPALITY + " TEXT, " +
                    LocationMap.COLUMN_NAME_TYPE_ID + " INTEGER, " +
                    "PRIMARY KEY (" + LocationMap.COLUMN_NAME_MUNICIPALITY + ", " + LocationMap.COLUMN_NAME_TYPE_ID  +"))";
}