package com.example.fond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.models.UserPost;
import com.parse.ParseFile;

import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ViewHolder>{
    private Context context;
    private List<UserPost> posts;

    public UserPostAdapter(Context context, List<UserPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPost post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUserProfile;
        public TextView tvUsername;
        public ImageView ivImage;
        public TextView tvCaption;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivUserProfile = itemView.findViewById(R.id.ivUserProfile);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCaption = itemView.findViewById(R.id.tvCaption);


        }

        public void bind(UserPost post) {
            // Fill the views with actual data
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getDescription());

            // Using glide to load the user profile
            ParseFile profile = post.getUserProfile();
            if (profile != null) {
                Glide.with(context).load(profile.getUrl()).into(ivUserProfile);
            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(profile.getUrl()).into(ivImage);
            }
        }
    }
}
