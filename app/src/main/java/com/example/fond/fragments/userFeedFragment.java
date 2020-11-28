package com.example.fond.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fond.R;

public class userFeedFragment extends Fragment {
    Button btnCreatePost;

    public userFeedFragment() {
        // Required empty public constructor
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
                 launchComposeFragment();
             }}
        );


    }

    protected void launchComposeFragment() {
        Fragment composeFragment = new ComposeFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_compose, composeFragment).commit();
    }

}