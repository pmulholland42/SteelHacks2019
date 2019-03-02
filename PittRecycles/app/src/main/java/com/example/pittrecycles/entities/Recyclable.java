package com.example.pittrecycles.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recs_table")
public class Recyclable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "barcode")
    private int barCode;

    @ColumnInfo(name = "rec_type")
    private RecyclableType recyclableType;

    @ColumnInfo(name = "source")
    private Source source;

    public Recyclable() {

    }

    public int getId() {
        return id;
    }

    public RecyclableType getRecyclableType() {
        return recyclableType;
    }

    public Source getSource() {
        return source;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecyclableType(RecyclableType recyclableType) {
        this.recyclableType = recyclableType;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public enum RecyclableType {
        PLASTIC_1, PLASTIC_2, PLASTIC_3, PLASTIC_4, PLASTIC_5, PLASTIC_6, PLASTIC_7
    }

    public enum Source {
        REMOTE, CROWD_SOURCE, LOCAL
    }

}
