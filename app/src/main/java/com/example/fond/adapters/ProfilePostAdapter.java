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
import com.example.fond.models.UserPost;
import com.parse.ParseFile;

import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {
    private String TAG = "ProfilePostAdapter";
    private Context context;
    private List<UserPost> posts;

    public ProfilePostAdapter(Context context, List<UserPost> posts){
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ProfilePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostAdapter.ViewHolder holder, int position) {
        UserPost post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPost;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
        }

        public void bind(UserPost post) {
            ParseFile postImage = post.getImage();
            if (postImage != null) {
                Glide.with(context).load(postImage.getUrl()).into(ivPost);
            }
        }
    }
}
