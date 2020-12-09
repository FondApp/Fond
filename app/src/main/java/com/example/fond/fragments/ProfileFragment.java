package com.example.fond.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.example.fond.R;
import com.example.fond.activities.LoginActivity;
import com.example.fond.adapters.ProfilePostAdapter;
import com.example.fond.models.UserPost;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView rvProfilePosts;
    private String TAG = "ProfileFeedFragment";
    private List<UserPost> allPosts;
    private OnLogoutButtonSelectedListener listener;
    protected Button btnLogout;
    private TextView tvUserNameProfile;
    private ProfilePostAdapter adapter;
    private ImageView ivProfilePicture;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public interface OnLogoutButtonSelectedListener {
        public void onLogoutButtonClick(Context context);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if the activity actually implements the interface
        if (context instanceof OnLogoutButtonSelectedListener) {
            // Assign the context to the listener
            listener = (OnLogoutButtonSelectedListener) context;
        }
        // If we forgot to implement the listener, throw an error with a reminder
        else {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileFragment.OnLogoutButtonSelectedListener");
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//     called after onCreateView() and ensures that the fragment's root view is non-null.
//     Any view setup should happen here. E.g., view lookups, attaching listeners
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tvUserNameProfile = view.findViewById(R.id.tvUserNameProfile);
        tvUserNameProfile.setText(ParseUser.getCurrentUser().getUsername());

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        ParseFile image = ParseUser.getCurrentUser().getParseFile("user_profile");
        if (image != null) {
            Glide
                    .with(getContext())
                    .load(image.getUrl())
                    .circleCrop()
                    .into(ivProfilePicture);
        }

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logging out", Toast.LENGTH_SHORT ).show();
                listener.onLogoutButtonClick(getContext());
            }
        });

        rvProfilePosts = getView().findViewById(R.id.rvProfilePosts);

        allPosts = new ArrayList<>();


        adapter = new ProfilePostAdapter(getContext(), allPosts);

        rvProfilePosts.setLayoutManager(new GridLayoutManager(getContext(), 3 , LinearLayoutManager.VERTICAL, false ));
        rvProfilePosts.setAdapter(adapter);

        queryPosts();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void queryPosts() {
        ParseQuery<UserPost> query = ParseQuery.getQuery(UserPost.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder(UserPost.KEY_CREATED);
        Log.i(TAG, "get Posts " );
        query.findInBackground(new FindCallback<UserPost>() {
            @Override
            public void done(List<UserPost> posts, ParseException e) {
                if (e!= null){
                    Log.e(TAG, "ERROR: " + e);
                } else {
                    Log.i(TAG, "Queried: " + posts.toString());
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}