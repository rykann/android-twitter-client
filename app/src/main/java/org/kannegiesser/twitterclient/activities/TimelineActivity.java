package org.kannegiesser.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.TwitterApplication;
import org.kannegiesser.twitterclient.adapters.TweetsAdapter;
import org.kannegiesser.twitterclient.fragments.ComposeTweetDialog;
import org.kannegiesser.twitterclient.listeners.EndlessScrollListener;
import org.kannegiesser.twitterclient.models.Tweet;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener {

    private static final String TAG = "TimelineActivity";

    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> tweetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsAdapter(this, tweets);
        ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetsAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                fetchTweets();
            }
        });

        //TODO: check internet access
        fetchTweets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.miComposeTweet) {
            showComposeTweetDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTweets() {
        TwitterApplication.getTwitterApi().getHomeTimeline(oldestFetchedTweetId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    tweetsAdapter.addAll(Tweet.fromJsonArray(response));
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing Twitter response", e);
                    Toast.makeText(TimelineActivity.this, "Sorry, unable to fetch tweets at this time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                String errorResponse = response != null ? response.toString() : "null";
                Log.e(TAG, "Failed to fetch tweets. Response: " + errorResponse);
            }
        });
    }

    private String oldestFetchedTweetId() {
        if (tweets.size() > 0) {
            Tweet oldestFetchedTweet = tweets.get(tweets.size() - 1);
            return oldestFetchedTweet.id;
        } else {
            return null;
        }
    }

    private void showComposeTweetDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialog dialog = new ComposeTweetDialog();
        dialog.show(fm, "fragment_compose_tweet");
    }

    @Override
    public void onTweetPosted() {
        tweetsAdapter.clear();
        fetchTweets();
    }
}
