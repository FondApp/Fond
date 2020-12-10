package com.example.fond.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.fond.R;

import java.util.List;

public class RecipeIngredientsAdapter extends BaseAdapter {
    public String TAG = "RecipeIngredientsAdapter";
    private List<String> ingredients;
    Context context;
    LayoutInflater inflater;
    String value;

    public RecipeIngredientsAdapter(Context context, List<String> ingredients){
        this.context = context;
        this.ingredients = ingredients;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_list, null);
        final CheckedTextView tvListItem = (CheckedTextView) view.findViewById(R.id.tvListItem);
        tvListItem.setText(ingredients.get(i));
        Log.i(TAG, ingredients.get(i));
        tvListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvListItem.isChecked()){
                    value = "un-Checked";
                    tvListItem.setChecked(false);
                } else{
                    value = "Checked";
                    tvListItem.setChecked(true);
                }
            }
        });
        return view;
    }
}
