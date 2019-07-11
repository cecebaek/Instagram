package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public JSONArray getListOfLikes() {
        JSONArray a = getJSONArray(KEY_LIKES);
        if (a == null) {
            return new JSONArray();
        }
        else {
            return a;
        }
    }

    public void likePost() {
        ParseUser user = ParseUser.getCurrentUser();
        add(KEY_LIKES, user);
    }

    public void unlikePost() {
        ParseUser user = ParseUser.getCurrentUser();
        ArrayList<ParseUser> users = new ArrayList<ParseUser>();
        users.add(user);
        removeAll(KEY_LIKES, users);
    }

    public int getNumLikes() {
        return getListOfLikes().length();
    }

    public boolean isLiked() {
        JSONArray a = getListOfLikes();
        for (int i = 0; i < a.length(); i++) {
            try {
                if (a.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    // Class to query post models
    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }
}
