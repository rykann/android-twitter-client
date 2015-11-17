package org.kannegiesser.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.adapters.TweetsAdapter;
import org.kannegiesser.twitterclient.fragments.ComposeTweetDialog;
import org.kannegiesser.twitterclient.fragments.HomeTimelineFragment;
import org.kannegiesser.twitterclient.fragments.MentionsTimelineFragment;
import org.kannegiesser.twitterclient.models.User;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener, TweetsAdapter.TweetsAdapterListener {

    private static final String TAG = "TimelineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
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
        if (id == R.id.miProfile) {
            launchProfileActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showComposeTweetDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialog dialog = new ComposeTweetDialog();
        dialog.show(fm, "fragment_compose_tweet");
    }

    private void launchProfileActivity() {
        launchProfileActivity(null);
    }

    private void launchProfileActivity(String screenName) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("screenName", screenName);
        startActivity(intent);
    }

    @Override
    public void onTweetPosted() {
        //TODO: restore this functionality (time permitting, do it in a less brute force way)
        //homeTimelineFragment.clear();
        //homeTimelineFragment.fetchTweets();
    }

    @Override
    public void onProfileImageClicked(User user) {
        launchProfileActivity(user.screenName);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = { "Home", "Mentions" };

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
