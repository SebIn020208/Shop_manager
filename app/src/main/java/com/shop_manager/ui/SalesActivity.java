package com.shop_manager.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.shop_manager.ui.ChartActivity;
import com.shop_manager.R;
import com.shop_manager.data.model.Sales;


import java.util.List;


public class SalesActivity extends AppCompatActivity {
    private SalesViewModel vm;
    private com.shop_manager.ui.SalesAdapter adapter;
    private TextView tvTotal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);


        tvTotal = findViewById(R.id.tv_total);
        RecyclerView rv = findViewById(R.id.rv_sales);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new com.shop_manager.ui.SalesAdapter();
        rv.setAdapter(adapter);


        vm = new ViewModelProvider(this).get(SalesViewModel.class);
        vm.getAllSales().observe(this, new Observer<List<Sales>>() {
            @Override
            public void onChanged(List<Sales> sales) {
                adapter.setItems(sales);
// 합계(오늘) 계산은 간단히 client-side로 처리 가능
                long todayStart = com.shop_manager.util.DateUtils.startOfDayMillis(System.currentTimeMillis());
                long todayEnd = com.shop_manager.util.DateUtils.endOfDayMillis(System.currentTimeMillis());
                vm.getSumBetween(todayStart, todayEnd).observe(SalesActivity.this, sum -> {
                    long total = sum == null ? 0 : sum;
                    tvTotal.setText("오늘 매출: ₩" + total);
                });
            }
        });


        findViewById(R.id.fab_add).setOnClickListener(v -> {
            com.shop_manager.ui.AddSaleDialogFragment dlg = new com.shop_manager.ui.AddSaleDialogFragment(vm);
            dlg.show(getSupportFragmentManager(), "add_sale");
        });


        Button btnChart = findViewById(R.id.btn_chart);
        btnChart.setOnClickListener(v -> startActivity(new android.content.Intent(this, AddSaleDialogFragment.ChartActivity.class)));


// FCM topic 구독 예시: (옵션)
        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("customers_nearby");
    }
}