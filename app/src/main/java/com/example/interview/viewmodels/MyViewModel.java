package com.example.interview.viewmodels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.interview.models.SearchApiCalling;
import com.example.interview.models.SearchResultData;


public class MyViewModel extends BaseObservable {
    public MutableLiveData<SearchResultData> searchResult;
    public MutableLiveData<String> status;

    public MyViewModel() {
        searchResult = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    /**
     * This one to update the user once server gives the search results.
     *
     * @return it returns the SearchResultData object when changes are comes.
     */
    public MutableLiveData<SearchResultData> getSearchResultObserver() {
        return searchResult;
    }

    /**
     * This one to update the user the search calling is Failed.
     *
     * @return returns the api data calling status if failed.
     */
    public MutableLiveData<String> statusObserver() {
        return status;
    }

    /**
     * This is the function to check the network connection of the device.
     * It simply avoid using resources by calling api with out internet connection.
     *
     * @param context it asking for context to get call getSystemService.
     */
    public boolean checkNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    /**
     * This method to display toast message from View
     *
     * @param context to pass value to toast method as a parameter
     * @param message to display the message to pass toast method.
     */
    public void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void callServer(String query) {
        SearchApiCalling calling = new SearchApiCalling();
        calling.callApi(query, searchResult, status);
    }

}
