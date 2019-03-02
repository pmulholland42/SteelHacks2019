package com.example.pittrecycles.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Recyclable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "barcode")
    private String barCode;

    @ColumnInfo(name = "rec_type")
    private RecyclableType recyclableType;

    @ColumnInfo(name = "source")
    private Source source;

    public enum RecyclableType {
        PLASTIC_1, PLASTIC_2, PLASTIC_3, PLASTIC_4, PLASTIC_5, PLASTIC_6, PLASTIC_7
    }

    public enum Source {
        REMOTE, CROWD_SOURCE, LOCAL
    }

}
