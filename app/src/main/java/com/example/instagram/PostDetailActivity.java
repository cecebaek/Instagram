package com.example.instagram;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

public class PostDetailActivity extends AppCompatActivity {

    Post mPost;
    ParseUser mUser;
    String imageUrl;

    TextView mUsername;
    TextView mCreatedAt;
    ImageView mImagePost;
    TextView mDescription;
    ImageButton mLikeButton;
    TextView mLikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // resolve the view object
        mUsername = findViewById(R.id.username_textview);
        mCreatedAt = findViewById(R.id.createdat_textview);
        mImagePost = findViewById(R.id.image_post_imageview);
        mDescription = findViewById(R.id.description_edittext);
        mLikeButton = findViewById(R.id.like_button);
        mLikeCount = findViewById(R.id.like_count_textview);

        // unwrap the post passed in via intent, using its simple name as a key
        mPost = (Post) getIntent().getParcelableExtra(Post.class.getSimpleName());
        // set variables with content from post
        mUsername.setText(mPost.getUser().getUsername());
        mCreatedAt.setText(mPost.getCreatedAt().toString());
        mDescription.setText(mPost.getDescription());
        imageUrl = mPost.getImage().getUrl();
        mLikeCount.setText(String.valueOf(mPost.getNumLikes()));
        if (mPost.isLiked()) {
            mLikeButton.setImageResource(R.drawable.ufi_heart_active);
        }
        else {
            mLikeButton.setImageResource(R.drawable.ufi_heart);
        }

        // load image using glide
        Glide.with(this)
                .load(imageUrl)
                .into(mImagePost);

        mUser = ParseUser.getCurrentUser();

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPost.isLiked()) {
                    // like
                    mPost.likePost();
                    mLikeButton.setImageResource(R.drawable.ufi_heart_active);
                }
                else {
                    // unlike
                    mPost.unlikePost();
                    mLikeButton.setImageResource(R.drawable.ufi_heart);
                }
                mPost.saveInBackground();
                mLikeCount.setText(String.valueOf(mPost.getNumLikes()));
            }
        });
    }
}
