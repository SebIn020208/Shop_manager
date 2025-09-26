package com.shop_manager.ui;


import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shop_manager.data.SalesRepository;
import com.shop_manager.data.model.Sales;


import java.util.List;


public class SalesViewModel extends AndroidViewModel {
    private SalesRepository repo;
    private LiveData<List<Sales>> allSales;


    public SalesViewModel(@NonNull Application application) {
        super(application);
        repo = new SalesRepository(application);
        allSales = repo.getAllSales();
    }


    public LiveData<List<Sales>> getAllSales() { return allSales; }
    public void insert(Sales s) { repo.insert(s); }
    public LiveData<Long> getSumBetween(long from, long to) { return repo.getSumBetween(from, to); }
}