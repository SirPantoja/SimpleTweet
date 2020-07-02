package com.codepath.apps.restclienttemplate;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public String timeAgo;
    public User user;
    public String url;
    public int retweets;
    public Integer favorites;
    public boolean media;
    public long id;

    // For the Parceler library
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.timeAgo = TimelineActivity.getRelativeTimeAgo(tweet.createdAt);
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweets = jsonObject.getInt("retweet_count");
        tweet.favorites = jsonObject.getInt("favorite_count");
        tweet.id = jsonObject.getLong("id");
        JSONObject entity = jsonObject.getJSONObject("entities");
        // Check if entity has an image
        if (entity.has("media") && entity.getJSONArray("media").length() != 0)
        {
            tweet.media = true;
            JSONObject newMedia = entity.getJSONArray("media").getJSONObject(0);
            tweet.url = newMedia.getString("media_url");
            Log.i("Tweet", tweet.url);
        } else {
            tweet.media = false;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
