package com.example.fond.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.R;
import com.example.fond.models.ParseRecipe;

import java.util.List;

public class RecipeSavedAdapter extends RecyclerView.Adapter<RecipeSavedAdapter.ViewHolder> {

    private Context context;
    private List<ParseRecipe> parseRecipes;

    public RecipeSavedAdapter(Context context, List<ParseRecipe> recipes ){
        this.context = context;
        this.parseRecipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        ViewHolder viewHolder = new ViewHolder(recipeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseRecipe parseRecipe = parseRecipes.get(position);
        holder.bind(parseRecipe);
    }

    @Override
    public int getItemCount() {
        return parseRecipes.size();
    }

    public void clear(){
        parseRecipes.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ParseRecipe> parseRecipeList){
        parseRecipes.addAll(parseRecipeList);
        notifyDataSetChanged();
    }

    public void updateAll(List<ParseRecipe> parseRecipeList){
        this.parseRecipes = parseRecipeList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivRecipeImage;
        public TextView tvRecipeTitle;
        public TextView tvRecipeSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvRecipeSummary = itemView.findViewById(R.id.tvRecipeSummary);

        }

        public void bind(ParseRecipe parseRecipe) {
            tvRecipeSummary.setText(parseRecipe.getSummary());
            tvRecipeTitle.setText(parseRecipe.getTitle());
            Glide
                    .with(context)
                    .load(parseRecipe.getImageUrl())
                    .into(ivRecipeImage);
        }
    }
}
