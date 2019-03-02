package com.example.pittrecycles.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.pittrecycles.entities.Recyclable;

@Dao
public interface RecyclableDao {

    @Query("select * from recs_table where barcode=(:barcode)")
    Recyclable getForBaecode(int barcode);

    @Query("select rec_type from recs_table where barcode=(:barcode)")
    Recyclable.RecyclableType getTypeForBarcode(String barcode);

    @Insert
    void insertAll(Recyclable...recyclables);
}
