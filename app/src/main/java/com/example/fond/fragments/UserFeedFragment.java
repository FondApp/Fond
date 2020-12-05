package com.example.fond.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fond.R;
import com.example.fond.adapters.UserPostAdapter;
import com.example.fond.models.UserPost;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserFeedFragment extends Fragment {

    private RecyclerView rvUserPosts;
    private String TAG = "UserFeedFragment";
    private UserPostAdapter adapter;
    private List<UserPost> allPosts;
    private OnPostButtonSelectedListener listener;
    protected Button btnCreatePost;

    public UserFeedFragment() {
        // Required empty public constructor
    }

    public interface OnPostButtonSelectedListener {
        public void onPostButtonClick(Context context);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if the activity actually implements the interface
        if (context instanceof OnPostButtonSelectedListener) {
            // Assign the context to the listener
            listener = (OnPostButtonSelectedListener) context;
        }
        // If we forgot to implement the listener, throw an error with a reminder
        else {
            throw new ClassCastException(context.toString()
                    + " must implement UserFeedFragment.OnPostButtonSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //        onCreate() is called to do initial creation of the fragment (Data initialization)
        setHasOptionsMenu(true);  // Need this so that onCreateOptionsMenu is called in the fragment
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //        onCreateView() is called by Android once the Fragment should inflate a view
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //     called after onCreateView() and ensures that the fragment's root view is non-null.
        //     Any view setup should happen here. E.g., view lookups, attaching listeners
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // Attaching the button to the view
        btnCreatePost = view.findViewById(R.id.btnPost);

        // Setting the OnPostButtonSelectedListener to the Post button
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Setting the listener");
                listener.onPostButtonClick(getContext());
            }}
        );
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);

        //     called after onCreateView() and ensures that the fragment's root view is non-null.
        //     Any view setup should happen here. E.g., view lookups, attaching listeners

        // Creating a list of posts
        allPosts = new ArrayList<>();

        // Setting the recycler view
        rvUserPosts = getView().findViewById(R.id.rvUserPosts);

        // Creating an adapter
        adapter = new UserPostAdapter(getContext(), allPosts);
        rvUserPosts.setAdapter(adapter);
        rvUserPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();


        // Create a RecyclerView.Adapter and ViewHolder to render the item
        // Bind the adapter to the data source to populate the RecyclerView

    }

    protected void queryPosts() {
        // Specifying the class we want to query
        ParseQuery<UserPost> query = ParseQuery.getQuery(UserPost.class);
        query.addDescendingOrder(UserPost.KEY_CREATED);

        // Perform query in the background
        query.findInBackground(new FindCallback<UserPost>() {
            @Override
            public void done(List<UserPost> posts, ParseException e) {
                // If there is an error, handle
                if (e != null) {
                    Log.e(TAG, "Issue querying posts");
                }

                for (UserPost post : posts) {
                    try {
                        Log.i(TAG, "Post:" + post.getDescription() + "\nFrom: " + post.getUsername());
                    } catch (Exception error) {
                        Log.e(TAG, "Error with grabbing username");
                    }

                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    protected void findUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error querying users");

                }

                for (ParseUser user : users) {

                    Log.i(TAG, user + "\n" + user.getUsername());
                }
            }
        });

    }
}