package org.kannegiesser.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.fragments.ComposeTweetDialog;
import org.kannegiesser.twitterclient.fragments.HomeTimelineFragment;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener {

    private static final String TAG = "TimelineActivity";

    HomeTimelineFragment homeTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        if (savedInstanceState == null) {
            homeTimelineFragment = (HomeTimelineFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.timeline_fragment);
        }
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

    private void showComposeTweetDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialog dialog = new ComposeTweetDialog();
        dialog.show(fm, "fragment_compose_tweet");
    }

    @Override
    public void onTweetPosted() {
        //TODO: latest tweets should be added in a less brute force way
        homeTimelineFragment.clear();
        homeTimelineFragment.fetchTweets();
    }
}
