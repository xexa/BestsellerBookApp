package com.example.bookskursfinalapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("hardcover-fiction.json")
    Call<BooksApiResponse> getBooks(@Query("api-key") String key);
}
