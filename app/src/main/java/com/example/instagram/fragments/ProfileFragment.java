package com.example.instagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.instagram.LoginActivity;
import com.example.instagram.R;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    ParseUser mParseUser;
    String imageUrl;

    private Button mLogoutButton;
    private ImageView mProfileImage;
    private TextView mUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mParseUser = ParseUser.getCurrentUser();

        mLogoutButton = view.findViewById(R.id.mLogoutButton);
        mUsername = view.findViewById(R.id.tvUsername);
        mProfileImage = view.findViewById(R.id.ivProfilePic);

        mUsername.setText(mParseUser.getUsername());

//        imageUrl = mParseUser.getParseFile("profilePic").getUrl();
        // load image using glide
//        Glide.with(this)
//                .load(ParseUser.getCurrentUser().getParseFile("profilePic").getUrl())
//                .into(mProfileImage);

        // set onClick Listener for Logout button
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
