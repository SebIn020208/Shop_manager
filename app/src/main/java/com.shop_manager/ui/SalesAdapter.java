package com.shop_manager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shop_manager.R;
import com.shop_manager.data.model.Sales;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.VH> {
    private List<Sales> items;


    public void setItems(List<Sales> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new VH(v);
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Sales s = items.get(position);
        holder.tvAmount.setText(String.format("₩%d", s.amount));
        String date = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(new Date(s.dateMillis));
        holder.tvMeta.setText(s.category + " · " + date);
    }


    @Override
    public int getItemCount() { return items == null ? 0 : items.size(); }


    static class VH extends RecyclerView.ViewHolder {
        TextView tvAmount, tvMeta;
        VH(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvMeta = itemView.findViewById(R.id.tv_meta);
        }
    }
}
