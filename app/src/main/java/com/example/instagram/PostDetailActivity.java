package com.example.instagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PostDetailActivity extends AppCompatActivity {

    Post post;
    String imageUrl;

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

        // unwrap the post passed in via intent, using its simple name as a key
        post = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
        // set variables with content from post
        tvUsername.setText(post.getUser().getUsername());
        tvCreatedAt.setText(post.getCreatedAt().toString());
        tvDescription.setText(post.getDescription());
        imageUrl = post.getImage().getUrl();

        // load image using glide
        Glide.with(this)
                .load(imageUrl)
                .into(ivImagePost);
    }
}
