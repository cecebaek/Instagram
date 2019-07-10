package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    Post post;

    TextView tvUsername;
    TextView tvCreatedAt;
    ImageView ivImagePost;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // resolve the view object
        tvUsername = findViewById(R.id.tvUsername);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        ivImagePost = findViewById(R.id.ivImagePost);
        tvDescription = findViewById(R.id.tvDescription);

//        // unwrap the post passed in via intent, using its simple name as a key
//        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
//        // set variables with content from post
//        tvUsername.setText(post.getUser().toString());
//        tvCreatedAt.setText(post.getCreatedAt().toString());
//        tvDescription.setText(post.getDescription());

        // unwrap the post passed in via intent, using its simple name as a key
        Intent movieDetails = getIntent();
        // set variables with content from post
        tvUsername.setText(movieDetails.getStringExtra("username"));
        tvCreatedAt.setText(movieDetails.getStringExtra("createdAt"));
        tvDescription.setText(movieDetails.getStringExtra("description"));
    }
}
