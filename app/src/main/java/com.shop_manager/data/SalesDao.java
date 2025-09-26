package com.shop_manager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.shop_manager.data.model.Sales;


import java.util.List;


@Dao
public interface SalesDao {
    @Insert
    void insert(Sales sale);


    @Query("SELECT * FROM sales ORDER BY dateMillis DESC")
    LiveData<List<Sales>> getAllSales();


    @Query("SELECT SUM(amount) FROM sales WHERE dateMillis BETWEEN :from AND :to")
    LiveData<Long> getSumBetween(long from, long to);
}
