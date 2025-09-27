package com.shop_manager.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.shop_manager.R;
import com.shop_manager.data.model.Sales;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddSaleDialogFragment extends DialogFragment {
    private SalesViewModel viewModel;


    public AddSaleDialogFragment(SalesViewModel vm) {
        this.viewModel = vm;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_sale, null);
        final EditText etAmount = v.findViewById(R.id.et_amount);
        final EditText etCategory = v.findViewById(R.id.et_category);
        final EditText etNote = v.findViewById(R.id.et_note);


        b.setTitle("매출 추가");
        b.setView(v);
        b.setPositiveButton("저장", (dialog, which) -> {
            String a = etAmount.getText().toString();
            if (TextUtils.isEmpty(a)) return;
            int amount = Integer.parseInt(a);
            String category = etCategory.getText().toString();
            String note = etNote.getText().toString();
            Sales s = new Sales(System.currentTimeMillis(), amount, category, note);
            viewModel.insert(s);
        });
        b.setNegativeButton("취소", null);
        return b.create();
    }

    public static class ChartActivity extends AppCompatActivity {
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
            long end = cal.getTimeInMillis() + 24L * 60 * 60 * 1000; // 내일 0시


            final long DAY = 24L * 60 * 60 * 1000;
            float[] sums = new float[7];
            for (Sales s : sales) {
                long diff = end - s.dateMillis;
                int dayIndex = (int) (diff / DAY);
                if (dayIndex >= 0 && dayIndex < 7) {
                    sums[6 - dayIndex] += s.amount; // reverse order
                }
            }


            ArrayList<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i < 7; i++) entries.add(new BarEntry(i, sums[i]));
        }
    }
}