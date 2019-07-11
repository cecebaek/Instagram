package com.example.instagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PostDetailActivity extends AppCompatActivity {

    Post mPost;
    String imageUrl;

    TextView mUsername;
    TextView mCreatedAt;
    ImageView mImagePost;
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // resolve the view object
        mUsername = findViewById(R.id.tvUsername);
        mCreatedAt = findViewById(R.id.tvCreatedAt);
        mImagePost = findViewById(R.id.ivImagePost);
        mDescription = findViewById(R.id.tvDescription);

        // unwrap the post passed in via intent, using its simple name as a key
        mPost = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
        // set variables with content from post
        mUsername.setText(mPost.getUser().getUsername());
        mCreatedAt.setText(mPost.getCreatedAt().toString());
        mDescription.setText(mPost.getDescription());
        imageUrl = mPost.getImage().getUrl();

        // load image using glide
        Glide.with(this)
                .load(imageUrl)
                .into(mImagePost);
    }
}
