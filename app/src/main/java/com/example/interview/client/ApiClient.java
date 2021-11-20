package com.example.interview.client;


import com.example.interview.models.SearchResultData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
    @GET("search")
    Call<SearchResultData> getSearchResult(@Query("query")String query);
}
