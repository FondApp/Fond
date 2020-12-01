package com.example.fond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.data.model.Recipe;

import java.util.List;

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.ViewHolder> {
    private List<Recipe> recipes;
    private Context context;
    private RecipeItemListener recipeItemListener;

    public RecipeSearchAdapter(Context context, List<Recipe> recipes, RecipeItemListener itemListener){
        this.recipes = recipes;
        this.context = context;
        this.recipeItemListener=itemListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RelativeLayout container;
        private TextView tvRecipeTitle;
        private TextView tvRecipeSummary;
        private ImageView ivRecipeImage;
        RecipeItemListener recipeItemListener;

        public ViewHolder(View recipeView, RecipeItemListener recipeItemListener){
            super(recipeView);
            container = recipeView.findViewById(R.id.container);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvRecipeSummary = itemView.findViewById(R.id.tvRecipeSummary);
            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            this.recipeItemListener = recipeItemListener;
            recipeView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Recipe recipe = getRecipe(getAdapterPosition());
            this.recipeItemListener.onRecipeClick(recipe.getId());

            notifyDataSetChanged();
        }

        public void bind(Recipe recipe) {
            tvRecipeTitle.setText(recipe.getTitle());
            tvRecipeSummary.setText(recipe.getSummary());
            Glide
                    .with(context)
                    .load(recipe.getImage())
                    .into(ivRecipeImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        ViewHolder viewHolder = new ViewHolder(recipeView, this.recipeItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void clear(){
        recipes.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Recipe> recipeList){
        recipes.addAll(recipeList);
        notifyDataSetChanged();
    }

    public void updateRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipe(int adapterPostition){
        return recipes.get(adapterPostition);
    }

    public interface RecipeItemListener {
        void onRecipeClick(long id);
    }


}
