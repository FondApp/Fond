package com.example.fond.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fond.R;

public class UserFeedFragment extends Fragment {
    Button btnCreatePost;
    private OnPostButtonSelectedListener listener;
    private Context context;
    private static final String TAG = "UserFeedFragment";

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

        btnCreatePost = view.findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // TODO: Launch Compose Fragment
                 Log.i(TAG, "Setting the listener");
                 listener.onPostButtonClick(getContext());
             }}
        );


    }

}