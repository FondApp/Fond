package com.example.fond.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.R;
import com.example.fond.data.model.Recipe;
import com.example.fond.models.ParseRecipe;
import com.example.fond.models.RecipeFavorites;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.ViewHolder> {
    private List<Recipe> recipes;
    private Context context;
    private RecipeItemListener recipeItemListener;
    private String TAG = "RecipeSearchAdapter";
    private List<Integer> savedRecipes;

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
        public CheckBox chkBookmark;

        public ViewHolder(View recipeView, RecipeItemListener recipeItemListener){
            super(recipeView);
            container = recipeView.findViewById(R.id.container);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvRecipeSummary = itemView.findViewById(R.id.tvRecipeSummary);
            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            this.recipeItemListener = recipeItemListener;
            recipeView.setOnClickListener(this);
            chkBookmark = itemView.findViewById(R.id.chkBookmark);

            chkBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        checkRecipeFavoriteParse(recipes.get(getAdapterPosition()));
                    }else{
                        removeRecipeFavoriteParse(recipes.get(getAdapterPosition()));
                        savedRecipes.remove(recipes.get(getAdapterPosition()).getId());
                    }
                }
            });
        }

        private void removeRecipeFavoriteParse(Recipe recipe) {
            ParseQuery<ParseRecipe> query = ParseQuery.getQuery("ParseRecipe");
            query.whereEqualTo("recipeId", recipe.getId());
            query.getFirstInBackground(new GetCallback<ParseRecipe>() {

                @Override
                public void done(ParseRecipe recipe, ParseException e) {
                    if (recipe != null) {
                        ParseQuery<RecipeFavorites> query = ParseQuery.getQuery("Recipe_Favorites");
                        query.whereEqualTo("userId", ParseUser.getCurrentUser());
                        query.whereEqualTo("recipeId", recipe);
                        // The query will search for a ParseObject, given its objectId.
                        // When the query finishes running, it will invoke the GetCallback
                        // with either the object, or the exception thrown
                        query.getFirstInBackground(new GetCallback<RecipeFavorites>() {
                            @Override
                            public void done(RecipeFavorites object, ParseException e) {
                                if (object != null) {
                                    Log.i(TAG, "Deleting " + object.toString());
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe_Favorites");
                                    query.getInBackground(object.getObjectId(), new GetCallback<ParseObject>() {
                                        public void done(ParseObject entity, ParseException e) {
                                            if (e == null) {
                                                // Otherwise, you can delete the entire ParseObject from the database
                                                entity.deleteInBackground();
                                            }
                                        }
                                    });


                                } else if (e != null) {
                                    Log.e(TAG, "Error: " + e);
                                }
                            }
                        });

                    } else if (e != null) {
                        Log.e(TAG, "Error: " + e);
                    }
                }
            });
        }



        @Override
        public void onClick(View view) {
            Recipe recipe = getRecipe(getAdapterPosition());
            this.recipeItemListener.onRecipeClick(recipe.getId());

            notifyDataSetChanged();
        }

        public void bind(Recipe recipe) {
            boolean isSaved = false;

            tvRecipeTitle.setText(recipe.getTitle());
            tvRecipeSummary.setText(recipe.getSummary());
            Glide
                    .with(context)
                    .load(recipe.getImage())
                    .into(ivRecipeImage);
            if (savedRecipes.contains(recipe.getId())) {isSaved = true;}
            chkBookmark.setChecked(isSaved);

        }
    }

    private List<Integer> getSavedRecipes() {
        ParseQuery<RecipeFavorites> query = ParseQuery.getQuery("Recipe_Favorites");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.include("recipeId");

        final List<Integer> queriedRecipes = new ArrayList<>();
        query.findInBackground(new FindCallback<RecipeFavorites>() {
            @Override
            public void done(List<RecipeFavorites> recipeFavorites, ParseException e) {
                if (recipeFavorites != null){
                    for (RecipeFavorites recipeFavorite : recipeFavorites ) {
                        Log.i(TAG, "Result: " + recipeFavorite.toString());
                        ParseRecipe parseRecipe = (ParseRecipe) recipeFavorite.getParseObject("recipeId");
                        queriedRecipes.add(parseRecipe.getRecipeId());
                    }
                }

            }
        });
        return queriedRecipes;
    }

    private void checkRecipeFavoriteParse(final Recipe recipe) {
        //Check if recipe added to parse
        ParseQuery<ParseRecipe> query = ParseQuery.getQuery("ParseRecipe");
        query.whereEqualTo("recipeId", recipe.getId());
        query.getFirstInBackground(new GetCallback<ParseRecipe>() {
            @Override
            public void done(ParseRecipe object, ParseException e) {
                if (object != null){
                    Log.i(TAG, "Recipe Exists");
                    RecipeFavorites newFavorite = new RecipeFavorites();
                    newFavorite.put("userId", ParseUser.getCurrentUser());
                    newFavorite.put("recipeId", object);

                    newFavorite.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.e(TAG, "Error: " + e );
                        }
                    });
                }
                else {
                    Log.i(TAG, "Need to add recipe");
                    final ParseRecipe newRecipe = new ParseRecipe();
                    newRecipe.put("title", recipe.getTitle());
                    newRecipe.put("imageUrl", recipe.getImage());
                    newRecipe.put("summary", recipe.getSummary());
                    newRecipe.put("recipeId", recipe.getId());

                    newRecipe.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(TAG, "Error: " + e );
                            } else {
                                RecipeFavorites newFavorite = new RecipeFavorites();
                                newFavorite.put("userId", ParseUser.getCurrentUser());
                                newFavorite.put("recipeId", newRecipe);

                                newFavorite.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Log.e(TAG, "Error: " + e );
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.item_recipe, parent, false);

        ViewHolder viewHolder = new ViewHolder(recipeView, this.recipeItemListener);

        savedRecipes = getSavedRecipes();
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

    public Recipe getRecipe(int adapterPosition){
        return recipes.get(adapterPosition);
    }

    public interface RecipeItemListener {
        void onRecipeClick(long id);
    }


}
