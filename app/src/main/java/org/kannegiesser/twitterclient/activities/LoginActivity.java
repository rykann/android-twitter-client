package org.kannegiesser.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;

import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.clients.TwitterApi;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterApi> {

    private static final String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate");
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	@Override
	public void onLoginSuccess() {
        Log.i(TAG, "onLoginSuccess");
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to log in.
	public void loginToRest(View view) {
        Log.i(TAG, "loginToRest");
		getClient().connect();
	}
}