package com.codepath.apps.restclienttemplate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;
    Long infimum = null;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // For each row, inflate layout for tweet
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with the view holder
        holder.bind(tweet);
    }

    // Bind values based on position of the element
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clears all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Adds the list of items to the recycler
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        ImageView ivMedia;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimeAgo;
        TextView tvRetweets;
        TextView tvFavorites;
        RelativeLayout rlTweet;
        Button btnReply;

        // itemView represents a tweet
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            rlTweet = itemView.findViewById(R.id.rlTweet);
            btnReply = itemView.findViewById(R.id.btnReply);
            tvRetweets = itemView.findViewById(R.id.tvRetweets);
            tvFavorites = itemView.findViewById(R.id.tvFavorites);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTimeAgo.setText(tweet.timeAgo);
            tvRetweets.setText(tweet.retweets + " retweets");
            tvFavorites.setText(tweet.favorites + " favorites");

            // Set the on click listener to go to details
            rlTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TweetDetailsActivity.class);
                    intent.putExtra("ScreenName", "@" + tweet.user.screenName);
                    intent.putExtra("TimeAgo", tweet.timeAgo);
                    intent.putExtra("Body", tweet.body);
                    intent.putExtra("MediaUrl", tweet.url);
                    intent.putExtra("ProfileUrl", tweet.user.profileImageUrl);
                    context.startActivity(intent);
                }
            });

            // Set the on click listener to reply
            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra("handle", "@" + tweet.user.screenName);
                    context.startActivity(intent);
                }
            });

                    Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            // Check if there was attached media
            if (tweet.media) {
                Glide.with(context).load(tweet.url).into(ivMedia);
                // Make sure body doesn't write over the image ivMedia
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvBody.getLayoutParams();
                params.setMargins(5, 2, 0, 0); //substitute parameters for left, top, right, bottom
                tvBody.setLayoutParams(params);
            } else {
                // Make sure no image is loaded in
                Glide.with(context).clear(ivMedia);
                // Adjust params to stretch out body
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvBody.getLayoutParams();
                params.setMargins(5, 2, -50, 0); //substitute parameters for left, top, right, bottom
                tvBody.setLayoutParams(params);
            }
        }
    }
}
