package com.example.interview.views;

import android.os.Bundle;
import android.view.View;

import com.example.interview.adapters.SearchAdapter;
import com.example.interview.models.Hit;
import com.example.interview.R;
import com.example.interview.models.SearchResultData;
import com.example.interview.viewmodels.MyViewModel;
import com.example.interview.databinding.ActivitySearchBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private LiveData<SearchResultData> searchResultDataLiveData;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<Hit> hits = new ArrayList<>();
    private Long backClickTime = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setContentView(binding.getRoot());
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.setViewModel(myViewModel);

        init();
    }

    private void init() {
        searchResultDataLiveData = myViewModel.getSearchResultObserver();
        shimmerFrameLayout = binding.shimmerFrame;
        recyclerView = binding.recyclerView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupRecyclerView();

        myViewModel.isClicked.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                searchAdapter.updateList(new ArrayList<>());
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmerAnimation();
            }
        });

        searchResultDataLiveData.observe(this, new Observer<SearchResultData>() {
            @Override
            public void onChanged(SearchResultData searchResultData) {
                List<Hit> hitList = searchResultData.getHits();
                if (hitList != null && hitList.size() > 0) {
                    searchAdapter.updateList(hitList);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                } else {
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmerAnimation();
                }
            }
        });

        myViewModel.status.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                myViewModel.displayToast(getBaseContext(), s);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this, hits);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shimmerFrameLayout != null) {
            shimmerFrameLayout.stopShimmerAnimation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shimmerFrameLayout != null) {
            shimmerFrameLayout.stopShimmerAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        if (backClickTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            myViewModel.displayToast(this,
                    getResources().getString(R.string.press_back_message));
        }
        backClickTime = System.currentTimeMillis();
    }
}