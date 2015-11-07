package org.kannegiesser.twitterclient.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Tweet {

    private static final String TAG = Tweet.class.getName();
    private static final SimpleDateFormat TWITTER_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);

    public String id; //id_str
    public Date createdAt; //created_at
    public String text; //text
    public User user;

    public static Tweet fromJson(JSONObject json) throws JSONException, ParseException {
        Tweet tweet = new Tweet();
        tweet.id = json.getString("id_str");
        tweet.createdAt = TWITTER_DATE_FORMAT.parse(json.getString("created_at"));
        tweet.text = json.getString("text");
        tweet.user = User.fromJson(json.getJSONObject("user"));
        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            try {
                tweets.add(Tweet.fromJson(json.getJSONObject(i)));
            } catch (Exception e) {
                Log.d(TAG, "Error parsing tweet data; skipping tweet", e);
            }
        }
        return tweets;
    }

    public String toString() {
        return String.format("@%s: %s", user.screenName, text);
    }
}
