package com.example.interview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interview.R;
import com.example.interview.models.Children;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
    private Context context;
    private List<Children> childrenList;
    private int lastPosition = -1;
    private Random random;

    public DetailsAdapter(Context context, List<Children> childrenList) {
        this.context = context;
        this.childrenList = childrenList;
        random = new Random(System.currentTimeMillis());
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_list_cell, parent, false);

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
            Children children = childrenList.get(position);
            holder.author.setText(children.getAuthor());
            holder.comment.setText(HtmlCompat.fromHtml(children.getText(), 0));
        }

    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView author, comment;
        private CardView cardView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            comment = itemView.findViewById(R.id.comment);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    /**
     * This method is responsible to update list.
     * It will call every time when user hit the search.
     *
     * @param newChildrenList this is the new updated result from server.
     */

    public void updateList(List<Children> newChildrenList) {
        if (childrenList != null) {
            childrenList.clear();
            childrenList = newChildrenList;
            notifyDataSetChanged();
        }
    }
}
