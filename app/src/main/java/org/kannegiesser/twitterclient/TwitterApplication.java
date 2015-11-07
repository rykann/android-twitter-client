package org.kannegiesser.twitterclient;

import android.content.Context;

import org.kannegiesser.twitterclient.models.TwitterApiConfig;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the twitter api.
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
		TwitterApiConfig.load(this);
	}

	public static TwitterApi getTwitterApi() {
		return (TwitterApi) TwitterApi.getInstance(TwitterApi.class, TwitterApplication.context);
	}
}