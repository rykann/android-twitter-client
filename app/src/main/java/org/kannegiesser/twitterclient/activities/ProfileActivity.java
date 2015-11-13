package org.kannegiesser.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;
import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.TwitterApplication;
import org.kannegiesser.twitterclient.fragments.UserTimelineFragment;
import org.kannegiesser.twitterclient.models.User;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fetchUserInfo();

        // The first time the activity is created, dynamically add the user timeline fragment
        // to the container defined in the layout
        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.userTimelineContainer, userTimelineFragment);
            ft.commit();
        }
    }

    private void fetchUserInfo() {
        TwitterApplication.getTwitterApi().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG, response.toString());
                    user = User.fromJson(response);
                    getSupportActionBar().setTitle("@" + user.screenName);
                    populateHeader();
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing Twitter response", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                String errorResponse = response != null ? response.toString() : "null";
                Log.e(TAG, "Failed to fetch user profile. Response: " + errorResponse);
            }
        });
    }

    private void populateHeader() {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        Picasso.with(this).load(user.profileImageUrl).noFade().into(ivProfileImage);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user.name);

        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvScreenName.setText("@" + user.screenName);

        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(user.description);

        TextView tvFollowingCount = (TextView) findViewById(R.id.tvFollowingCount);
        tvFollowingCount.setText(String.format("%d", user.followingCount));

        TextView tvFollowersCount = (TextView) findViewById(R.id.tvFollowersCount);
        tvFollowersCount.setText(String.format("%d", user.followersCount));
    }
}
