package com.example.fond.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.fond.R;
import com.example.fond.models.ParseRecipe;

import java.util.List;

public class RecipeInstructionAdapter extends BaseAdapter {
    public String TAG = "RecipeInstructionAdapter";
    private List<String> instructions;
    Context context;
    LayoutInflater inflater;
    String value;

    public RecipeInstructionAdapter(Context context, List<String> instructions){
        this.context = context;
        this.instructions = instructions;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return instructions.size();
    }

    @Override
    public Object getItem(int i) {
        return instructions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_list, null);
        final CheckedTextView tvListItem = (CheckedTextView) view.findViewById(R.id.tvListItem);
        tvListItem.setText(instructions.get(i));
        Log.i(TAG, "step i: "+instructions.get(i));
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
