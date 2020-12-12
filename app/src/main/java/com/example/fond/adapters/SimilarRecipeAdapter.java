package com.example.fond.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.R;
import com.example.fond.data.model.Recipe;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeAdapter.ViewHolder> {
    private String TAG = "SimilarRecipeAdapter";
    private Context context;
    private List<Recipe> recipes;
    private SimilarRecipeItemListener similarRecipeItemListener;



    public SimilarRecipeAdapter(Context context, List<Recipe> recipes, SimilarRecipeItemListener similarRecipeItemListener){
        this.context = context;
        this.recipes = recipes;
        this.similarRecipeItemListener = similarRecipeItemListener;
    }

    @NonNull
    @Override
    public SimilarRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_profile_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this.similarRecipeItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface SimilarRecipeItemListener {
        void onSimilarRecipeClick(long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivPost;
        SimilarRecipeItemListener similarRecipeItemListener;

        public ViewHolder(View view, SimilarRecipeItemListener similarRecipeItemListener) {
            super(view);
            ivPost = itemView.findViewById(R.id.ivPost);
            this.similarRecipeItemListener = similarRecipeItemListener;
            view.setOnClickListener(this);

        }

        public void bind(Recipe recipe) {
            Glide
                    .with(context)
                    .load(recipe.getImage())
                    .into(ivPost);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());
            this.similarRecipeItemListener.onSimilarRecipeClick(recipe.getId());
            notifyDataSetChanged();
        }
    }
}
