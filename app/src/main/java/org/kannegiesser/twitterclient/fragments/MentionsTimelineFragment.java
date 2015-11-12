package org.kannegiesser.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.TwitterApplication;
import org.kannegiesser.twitterclient.models.Tweet;

public class MentionsTimelineFragment extends TweetsListFragment {

    private static final String TAG = MentionsTimelineFragment.class.getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchTweets();
    }

    @Override
    public void fetchTweets() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), R.string.network_not_available, Toast.LENGTH_LONG).show();
            return;
        }

        TwitterApplication.getTwitterApi().getMentionsTimeline(oldestFetchedTweetId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    addAll(Tweet.fromJsonArray(response));
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing Twitter response", e);
                    Toast.makeText(getContext(), "Sorry, unable to fetch tweets at this time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                String errorResponse = response != null ? response.toString() : "null";
                Log.e(TAG, "Failed to fetch tweets. Response: " + errorResponse);
            }
        });
    }
}
