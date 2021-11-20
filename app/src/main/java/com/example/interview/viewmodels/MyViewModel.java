package com.example.interview.viewmodels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.interview.R;
import com.example.interview.models.DetailsData;
import com.example.interview.models.SearchApiCalling;
import com.example.interview.models.SearchResultData;


public class MyViewModel extends BaseObservable {
    public MutableLiveData<SearchResultData> searchResult;
    private MutableLiveData<DetailsData> detailsData;
    public MutableLiveData<String> status;
    public String editTextValue = "";
    public MutableLiveData<String> isClicked;
    public MutableLiveData<String> goBack;

    public MyViewModel() {
        searchResult = new MutableLiveData<>();
        detailsData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        isClicked = new MutableLiveData<>();
        goBack = new MutableLiveData<>();
        goBack = new MutableLiveData<>();
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
     * This one to update the user once server gives the result.
     *
     * @return it returns the detail result data of particular object when changes are comes.
     */
    public MutableLiveData<DetailsData> getDetailsDataObserver() {
        return detailsData;
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

    /**
     * This method is responsible to call api for search result.
     * This method is calling from activity_search.xml file
     *
     * @param view is required to pass a parameter for toast message.
     */

    public void callServer(View view) {
        if (editTextValue.length() < 0) {
            Context context = view.getContext();
            displayToast(context, context.getResources().getString(R.string.enter_keyword));
        } else if (! checkNetworkConnection(view.getContext())) {
            Context context = view.getContext();
            displayToast(context, context.getResources().getString(R.string.no_internet));
        } else {
            isClicked.postValue("yes");
            SearchApiCalling calling = new SearchApiCalling();
            calling.callApi(editTextValue, searchResult, status);
        }
    }

    /**
     * This method is responsible to call api of detail data with comments.
     * @param objectID is a id of particular post.
     *                 Api calling is require to pass this as a parameter.
     */
    public void callDetailsServer(Context context, String objectID) {
        if (! checkNetworkConnection(context)) {
            displayToast(context, context.getResources().getString(R.string.no_internet));
        } else {
            SearchApiCalling calling = new SearchApiCalling();
            calling.callDetailsApi(objectID, detailsData, status);
        }

    }

    /**
     * This method is responsible to back button click.
     *
     */
    public void goBackClicked(){
        goBack.postValue("yes");
    }
}
