package com.example.interview.models;

import androidx.lifecycle.MutableLiveData;

import com.example.interview.R;
import com.example.interview.client.ApiClient;
import com.example.interview.client.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchApiCalling {

    /**
     * This method is responsible to call api to get search result.
     *
     * @param objectID     is a id from user clicked result list.
     * @param searchResult is a mutable live data of model class.
     *                     this is responsible to update the recyclerview.
     * @param status       is a mutable String.
     *                     this is responsible for update the user when server gives Error.
     */
    public void callApi(String objectID,
                        MutableLiveData<SearchResultData> searchResult,
                        MutableLiveData<String> status) {
        ApiClient apiClient = RetrofitClient.getApiClient();
        Call<SearchResultData> call = apiClient.getSearchResult(objectID);
        call.enqueue(new Callback<SearchResultData>() {
            @Override
            public void onResponse(Call<SearchResultData> call,
                                   Response<SearchResultData> response) {
                if (response.code() == 200) {
                    if (response.code() == 200) {
                        searchResult.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResultData> call, Throwable t) {
                status.postValue("Failed");
            }
        });


    }


    /**
     * This method is responsible to call api to get search result.
     *
     * @param query        is a string it search by the user.
     * @param searchResult is a mutable live data of model class.
     *                     this is responsible to update the recyclerview.
     * @param status       is a mutable String.
     *                     this is responsible for update the user when server gives Error.
     */
    public void callDetailsApi(String query,
                               MutableLiveData<SearchResultData> searchResult,
                               MutableLiveData<String> status) {
        ApiClient apiClient = RetrofitClient.getApiClient();
        Call<SearchResultData> call = apiClient.getSearchResult(query);
        call.enqueue(new Callback<SearchResultData>() {
            @Override
            public void onResponse(Call<SearchResultData> call,
                                   Response<SearchResultData> response) {
                if (response.code() == 200) {
                    if (response.code() == 200) {
                        searchResult.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResultData> call, Throwable t) {
                status.postValue("Failed");
            }
        });


    }
}
