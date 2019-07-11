package com.example.instagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instagram.EndlessRecyclerViewScrollListener;
import com.example.instagram.Post;
import com.example.instagram.PostsAdapter;
import com.example.instagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";

    private SwipeRefreshLayout mSwipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView mPostsRecyclerView;
    private PostsAdapter mPostsAdapter;
    private List<Post> mPosts;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_posts, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Lookup the swipe container view
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // refresh the list
                fetchTimelineAsync(0);
            }
        });

        mPostsRecyclerView = view.findViewById(R.id.rvPosts);
        // create the adapter
        mPosts = new ArrayList<Post>();
        // create the data source
        mPostsAdapter = new PostsAdapter(getContext(), mPosts);
        // set the adapter on the recycler view
        mPostsRecyclerView.setAdapter(mPostsAdapter);
        // set the layout manager on the recycler view
        linearLayoutManager = new LinearLayoutManager(getContext());
        mPostsRecyclerView.setLayoutManager(linearLayoutManager);
        // load posts
        loadTopPosts(0);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadTopPosts(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        mPostsRecyclerView.addOnScrollListener(scrollListener);
    }

    private void loadTopPosts(int page) {
        final Post.Query postQuery = new Post.Query();
        postQuery.setLimit(20);
        postQuery.setSkip(20*page);
        postQuery.getTop().withUser().orderByDescending("createdAt");
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    mPosts.addAll(posts);
                    mPostsAdapter.notifyDataSetChanged();
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        mPostsAdapter.clear();
        loadTopPosts(page);
        mSwipeContainer.setRefreshing(false);
    }
}
