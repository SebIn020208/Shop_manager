package com.shop_manager.ui;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.shop_manager.R;
import com.shop_manager.data.model.Sales;
import com.shop_manager.ui.SalesViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ChartActivity extends AppCompatActivity {
    private BarChart chart;
    private SalesViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        chart = findViewById(R.id.barChart);


        vm = new ViewModelProvider(this).get(SalesViewModel.class);
        vm.getAllSales().observe(this, new Observer<List<Sales>>() {
            @Override
            public void onChanged(List<Sales> sales) {
                showWeeklyChart(sales);
            }
        });
    }


    private void showWeeklyChart(List<Sales> sales) {
// 예시: 최근 7일 합계(간단 구현)
        long now = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long end = cal.getTimeInMillis() + 24L*60*60*1000; // 내일 0시


        final long DAY = 24L*60*60*1000;
        float[] sums = new float[7];
        for (Sales s : sales) {
            long diff = end - s.dateMillis;
            int dayIndex = (int)(diff / DAY);
            if (dayIndex >= 0 && dayIndex < 7) {
                sums[6 - dayIndex] += s.amount; // reverse order
            }
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) entries.add(new BarEntry(i, sums[i]));


        BarDataSet set = new BarDataSet(entries, "최근 7일 매출");
        BarData data = new BarData(set);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        XAxis x = chart.getXAxis();
        x.setGranularity(1f);
        chart.invalidate();
    }
}
