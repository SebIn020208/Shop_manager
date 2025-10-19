package com.shop_manager.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Sales {
    @PrimaryKey(autoGenerate = true)
    public long id;


    // epoch millis
    public long dateMillis;
    public int amount;
    public String category;
    public String note;


    public Sales(long dateMillis, int amount, String category, String note) {
        this.dateMillis = dateMillis;
        this.amount = amount;
        this.category = category;
        this.note = note;
    }
}
