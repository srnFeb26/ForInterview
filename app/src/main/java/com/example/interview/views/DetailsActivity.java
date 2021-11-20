package com.example.interview.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.interview.R;
import com.example.interview.adapters.SearchAdapter;
import com.example.interview.databinding.ActivityDetailsBinding;
import com.example.interview.models.Hit;
import com.example.interview.viewmodels.MyViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<Hit> hits = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setContentView(binding.getRoot());
        myViewModel = new MyViewModel();
        binding.setViewModel(myViewModel);
        init();
    }

    private void init(){
        String objectID = getIntent().getStringExtra("object_id");
        myViewModel.displayToast(getBaseContext(),""+objectID);

    }
}