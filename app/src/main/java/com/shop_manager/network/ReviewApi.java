package com.shop_manager.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReviewApi {

    @GET("reviews") // 샘플 엔드포인트
    Call<List<ReviewItem>> getReviews(
            @Query("appId") String appId,
            @Query("maxResults") int maxResults
    );
}
