package com.example.fond.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fond.R;
import com.example.fond.models.Fond;
import com.example.fond.models.UserPost;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.ViewHolder>{
    private String TAG = "UserPostAdapter";
    private Context context;
    private List<UserPost> posts;
    private List<String> fondedPosts;

    public UserPostAdapter(Context context, List<UserPost> posts, List<String> fondedPosts) {
        this.context = context;
        this.posts = posts;
        this.fondedPosts = fondedPosts;
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
        public CheckBox chkFond;


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
            chkFond = itemView.findViewById(R.id.chkFond);

            chkFond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        addFond(posts.get(getAdapterPosition()));
                        fondedPosts.add(posts.get(getAdapterPosition()).getObjectId());
                        Log.i(TAG, "Fonded posts" + fondedPosts.toString());
                    } else {
                        removeFond(posts.get(getAdapterPosition()));
                        //remove from list
                        fondedPosts.remove(posts.get(getAdapterPosition()).getObjectId());
                    }
                }
            });

        }

        private void removeFond(UserPost userPost) {
            ParseQuery<Fond> query = ParseQuery.getQuery("Fond");
            query.whereEqualTo("postId", userPost);
            query.whereEqualTo("userId", ParseUser.getCurrentUser());
            query.getFirstInBackground(new GetCallback<Fond>() {
                @Override
                public void done(Fond object, ParseException e) {
                    if (object != null){
                        object.deleteInBackground();
                    } else if (e != null) {
                        Log.e(TAG, "Error removeFond: " + e);
                    }
                }
            });
        }

        private void addFond(UserPost userPost) {
            Fond fond = new Fond();

            Log.i(TAG, "ParseUser" + ParseUser.getCurrentUser().toString());
            Log.i(TAG, "UserPost" + userPost.toString());
            fond.put("userId", ParseUser.getCurrentUser());
            fond.put("postId", userPost);
            fond.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null){
                        Log.i(TAG, "Added Fond");
                    } else {

                        Log.e(TAG, "Error addFond: " + e);
                    }
                }
            });
        }

        public void bind(UserPost post) {
            boolean isFonded = false;
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
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            if(fondedPosts.contains(post.getObjectId())) {isFonded = true;}
            chkFond.setChecked(isFonded);
        }
    }


}
