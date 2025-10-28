package com.shop_manager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shop_manager.R;
import com.shop_manager.network.ReviewItem;
import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewItem> items = new ArrayList<>();

    public void setItems(List<ReviewItem> reviews) {
        items.clear();
        items.addAll(reviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = items.get(position);
        holder.tvAuthor.setText(item.getAuthorName());
        holder.tvComment.setText(item.getComment());
        holder.tvRating.setText("⭐ " + item.getRating());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvComment, tvRating;
        ViewHolder(View v) {
            super(v);
            tvAuthor = v.findViewById(R.id.tv_author);
            tvComment = v.findViewById(R.id.tv_comment);
            tvRating = v.findViewById(R.id.tv_rating);
        }
    }
}
