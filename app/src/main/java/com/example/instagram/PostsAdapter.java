package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvCreatedAt;
        private ImageButton mLikeButton;
        private TextView mLikeCount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.handle_textview);
            ivImage = itemView.findViewById(R.id.image_imageview);
            tvDescription = itemView.findViewById(R.id.description_edittext);
            tvCreatedAt = itemView.findViewById(R.id.createdat_textview);
            mLikeButton = itemView.findViewById(R.id.like_button);
            mLikeCount = itemView.findViewById(R.id.like_count_textview);

            itemView.setOnClickListener(this);
        }


        // binds the view elements to the post
        public void bind(final Post post) {
            tvHandle.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            tvCreatedAt.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
            mLikeCount.setText(String.valueOf(post.getNumLikes()));
            if (post.isLiked()) {
                mLikeButton.setImageResource(R.drawable.ufi_heart_active);
            }
            else {
                mLikeButton.setImageResource(R.drawable.ufi_heart);
            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            mLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!post.isLiked()) {
                        // like
                        post.likePost();
                        mLikeButton.setImageResource(R.drawable.ufi_heart_active);
                    }
                    else {
                        // unlike
                        post.unlikePost();
                        mLikeButton.setImageResource(R.drawable.ufi_heart);
                    }
                    post.saveInBackground();
                    mLikeCount.setText(String.valueOf(post.getNumLikes()));
                }
            });
        }


        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Post.class.getSimpleName(), post);
                // show the activity
                context.startActivity(intent);
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
