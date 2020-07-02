package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TweetDetailsActivity extends AppCompatActivity {

    // Declare local variables
    ImageView ivProfile;
    ImageView ivMedia;
    TextView tvScreenName;
    TextView tvTimeAgo;
    TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        // Link views
        ivProfile = findViewById(R.id.ivProfile);
        ivMedia = findViewById(R.id.ivMedia2);
        tvScreenName = findViewById(R.id.tvScreenName2);
        tvTimeAgo = findViewById(R.id.tvTimeAgo2);
        tvBody = findViewById(R.id.tvBody2);

        // Retrieve intent data
        Intent intent = getIntent();

        // Get text data from intent
        tvScreenName.setText(intent.getStringExtra("ScreenName"));
        tvTimeAgo.setText(intent.getStringExtra("TimeAgo"));
        tvBody.setText(intent.getStringExtra("Body"));

        // Get urls from intent
        String profileUrl = intent.getStringExtra("ProfileUrl");
        String mediaUrl = intent.getStringExtra("MediaUrl");            // This could be null

        // Use glide to insert images
        Glide.with(this).load(profileUrl).into(ivProfile);
        if (mediaUrl != null) {
            Glide.with(this).load(mediaUrl).into(ivMedia);
        }
    }
}