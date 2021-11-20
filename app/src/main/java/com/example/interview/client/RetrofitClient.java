package com.example.interview.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static ApiClient apiClient = null;
    private static final String BASE_URL = "https://hn.algolia.com/api/v1/";

    public static synchronized ApiClient getApiClient() {
        if (apiClient == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiClient = retrofit.create(ApiClient.class);
        }
        return apiClient;
    }
}
