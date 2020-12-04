package com.example.fond.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fond.R;
import com.example.fond.models.UserPost;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import static android.app.Activity.RESULT_OK;
import java.io.File;

/*
    TODO:
     - Attach ComposeFragment to MainActivity. You should begin a transaction at a button click.
     - Add Submit click listener, end fragment and go back to home (begin transaction)
 */

public class ComposeFragment extends Fragment {
    private ImageView ivPostImage;
    private TextView etDescription;
    private Button btnSubmit;
    private File photoFile;
    private String photoFileName = "photo.jpg";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public static final String TAG = "ComposeFragment";
    private OnSubmitListener listener;

    public ComposeFragment() {
        // Required empty public constructor
    }

    public interface OnSubmitListener {
        // Action depends on whether the exception is null or not
        public void onDataSubmit(ParseException e);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if activity implements the listener
        if (context instanceof OnSubmitListener) {
            listener = (OnSubmitListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeFragment.OnSubmitListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPostImage = view.findViewById(R.id.ivPostImage);
        etDescription = view.findViewById(R.id.etDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Grab caption
                String caption = etDescription.getText().toString();

                // Check if caption and image are available
                if (caption.isEmpty()) {
                    Toast.makeText(getContext(), "Post must have a caption", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "Post must have a photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(currentUser, caption, photoFile);


            }
        });

        launchCamera();
    }

    private void savePost(ParseUser currentUser, String caption, File photoFile) {
        UserPost post = new UserPost();

        post.setUser(currentUser);
        post.setDescription(caption);
        post.setImage(new ParseFile(photoFile));

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving");
                    Toast.makeText(getContext(), "Error - photo not saved", Toast.LENGTH_SHORT).show();
                }

                Log.i(TAG, "Photo saved successfully");
                listener.onDataSubmit(e);
            }
        });
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fond.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture Not Captured", Toast.LENGTH_SHORT).show();
            }
        }
    }
}