package com.example.fond.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fond.R;
import com.example.fond.models.UserPost;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    public String TAG = "ListItemAdapter";
    private List<String> items;
    Context context;
    String value;

    public ListItemAdapter(Context context, List<String> items){
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView tvListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListItem = itemView.findViewById(R.id.tvListItem);
        }

        public void bind(String item) {
            tvListItem.setText(item);
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
        }
    }
}
