package com.example.fond.adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.R;
import com.example.fond.models.ParseRecipe;
import com.example.fond.models.RecipeFavorites;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class RecipeSavedAdapter extends RecyclerView.Adapter<RecipeSavedAdapter.ViewHolder> {

    private String TAG = "RecipeSavedAdapter";
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
        public CheckBox chkBookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvRecipeSummary = itemView.findViewById(R.id.tvRecipeSummary);
            chkBookmark = itemView.findViewById(R.id.chkBookmark);

            chkBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        Log.i(TAG, "I am at" + getAdapterPosition());
                    }else{

                        removeRecipeFavoriteParse(parseRecipes.get(getAdapterPosition()));
                        parseRecipes.remove(getAdapterPosition());
                        notifyDataSetChanged();

                    }
                }
            });

        }

        public void bind(ParseRecipe parseRecipe) {
            tvRecipeSummary.setText(parseRecipe.getSummary());
            tvRecipeTitle.setText(parseRecipe.getTitle());
            chkBookmark.setChecked(true);
            Glide
                    .with(context)
                    .load(parseRecipe.getImageUrl())
                    .into(ivRecipeImage);
        }
    }

    private void removeRecipeFavoriteParse(ParseRecipe parseRecipe) {
        ParseQuery<RecipeFavorites> query = ParseQuery.getQuery("Recipe_Favorites");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.whereEqualTo("recipeId", parseRecipe);
        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
        query.getFirstInBackground(new GetCallback<RecipeFavorites>() {
            @Override
            public void done(RecipeFavorites object, ParseException e) {
                if (object != null) {
                    Log.i(TAG, "Deleting "+object.toString());
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
                    Log.e(TAG, "Error: "+ e);
                }
            }
        });

    }
}
