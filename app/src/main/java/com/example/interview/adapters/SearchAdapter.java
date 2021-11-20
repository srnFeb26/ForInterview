package com.example.interview.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interview.models.Hit;
import com.example.interview.R;
import com.example.interview.views.DetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context context;
    private List<Hit> hitList;
    private int lastPosition = -1;
    private Random random;

    public SearchAdapter(Context context, List<Hit> hitList) {
        this.context = context;
        this.hitList = hitList;
        random = new Random(System.currentTimeMillis());
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_animation);
            holder.itemView.startAnimation(animation);

            int r = 155 + random.nextInt(101);
            int g = 155 + random.nextInt(101);
            int b = 155 + random.nextInt(101);
            int color = Color.rgb(r, g, b);
            holder.cardView.setBackgroundColor(color);
            if (hitList.get(position).title != null && hitList.get(position).title.length() > 0) {
                holder.title.setText("Title : " + hitList.get(position).title);
            } else {
                holder.title.setText("Title : " + "No Title");
            }

            holder.author.setText("Author : " + hitList.get(position).author);
            holder.points.setText("Points : " + hitList.get(position).getPoints());
            if (hitList.get(position).story_text != null) {
                holder.story_text.setText("" + hitList.get(position).story_text);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("object_id", hitList.get(position).getObjectID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hitList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, author, points, story_text;
        private CardView cardView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            points = itemView.findViewById(R.id.points);
            story_text = itemView.findViewById(R.id.story_text);
            cardView = itemView.findViewById(R.id.card_view);


        }
    }

    /**
     * This method is responsible to update list.
     * It will call every time when user hit the search.
     *
     * @param newHitList this is the new updated search result from server.
     */

    public void updateList(List<Hit> newHitList) {
        if (hitList != null) {
            hitList.clear();
            hitList = newHitList;
            notifyDataSetChanged();
        }
    }

}
