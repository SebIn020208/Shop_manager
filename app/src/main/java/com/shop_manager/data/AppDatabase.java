package com.shop_manager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shop_manager.data.model.Sales;


@Database(entities = {Sales.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SalesDao salesDao();


    private static volatile AppDatabase INSTANCE;


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "shop_manager-db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}