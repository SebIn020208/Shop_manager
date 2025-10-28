package com.shop_manager.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shop_manager.R;
import com.shop_manager.network.RetrofitClient;
import com.shop_manager.network.ReviewApi;
import com.shop_manager.network.ReviewItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView rvReviews;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        rvReviews = findViewById(R.id.rv_reviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter();
        rvReviews.setAdapter(adapter);

        loadReviews();
    }

    private void loadReviews() {
        Retrofit retrofit = RetrofitClient.getClient("https://example.com/api/"); // 샘플 URL
        ReviewApi api = retrofit.create(ReviewApi.class);

        api.getReviews("com.shopmanager", 10).enqueue(new Callback<List<ReviewItem>>() {
            @Override
            public void onResponse(Call<List<ReviewItem>> call, Response<List<ReviewItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setItems(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReviewItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
