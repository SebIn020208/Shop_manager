package com.shop_manager.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.shop_manager.data.model.Sales;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalesRepository {
    private SalesDao dao;
    private LiveData<List<Sales>> allSales;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public SalesRepository(Context ctx) {
        AppDatabase db = AppDatabase.getInstance(ctx);
        dao = db.salesDao();
        allSales = dao.getAllSales();
    }

    public LiveData<List<Sales>> getAllSales() { return allSales; }

    public void insert(final Sales sale) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(sale);
            }
        });
    }

    public LiveData<Long> getSumBetween(long from, long to) {
        return dao.getSumBetween(from, to);
    }
}

