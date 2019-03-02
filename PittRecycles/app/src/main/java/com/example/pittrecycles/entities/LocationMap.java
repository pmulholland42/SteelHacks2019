package com.example.pittrecycles.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location_map_table")
public class LocationMap {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "recyclable_type")
    private Recyclable.RecyclableType recyclableType;

    @ColumnInfo(name = "municipality")
    private Municipality municipality;

}
