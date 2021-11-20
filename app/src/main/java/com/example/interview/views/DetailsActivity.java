package com.example.interview.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.interview.R;
import com.example.interview.adapters.DetailsAdapter;
import com.example.interview.databinding.ActivityDetailsBinding;
import com.example.interview.models.Children;
import com.example.interview.models.DetailsData;
import com.example.interview.viewmodels.MyViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private DetailsAdapter detailsAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout mainFrame;
    private List<Children> children ;
    private LiveData<DetailsData> detailsDataLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setContentView(binding.getRoot());
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.setViewModel(myViewModel);
        children = new ArrayList<>();
        init();
    }

    private void init() {
        recyclerView = binding.recyclerView;
        shimmerFrameLayout = binding.shimmerFrame;
        shimmerFrameLayout.startShimmerAnimation();
        mainFrame = binding.mainFrame;

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupRecyclerView();
        backClick();
        String objectID = getIntent().getStringExtra("object_id");
        detailsDataLiveData = myViewModel.callDetailsServer(this, objectID);
        detailsDataLiveData.observe(this, new Observer<DetailsData>() {
            @Override
            public void onChanged(DetailsData detailsData) {

                binding.title.setText(getResources().getString(R.string.title_text)
                        +"  "+ detailsData.getTitle());

                binding.author.setText(getResources().getString(R.string.author_text)
                        +"  "+ detailsData.getAuthor());

                binding.points.setText(getResources().getString(R.string.points_text)
                        +"  "+ detailsData.getPoints());

                if (detailsData.getText() != null && detailsData.getText().length() > 0) {
                    binding.storyText.setText(getResources().getString(R.string.story_text) +
                            "\n" + HtmlCompat.fromHtml(""+detailsData.getText(), 0));
                } else {
                    binding.storyText.setText(getResources().getString(R.string.story_text) +
                            "\n" + getResources().getString(R.string.user_story_error));
                }

                children = detailsData.getChildren();
                if (children == null){
                    children = new ArrayList<>();
                }
                for (int i = 0; i < children.size(); i++) {
                    if (children.get(i).getAuthor() == null) {
                        children.remove(i);
                    }
                }
                detailsAdapter.updateList(children);
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                mainFrame.setVisibility(View.VISIBLE);
            }
        });

        myViewModel.statusObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                myViewModel.displayToast(getBaseContext(), getResources().getString(R.string.no_internet));
            }
        });
    }

    private void backClick() {
        myViewModel.goBack.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                finish();
            }
        });
    }


    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsAdapter = new DetailsAdapter(this, children);
        recyclerView.setAdapter(detailsAdapter);
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

}