package org.kannegiesser.twitterclient.clients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

public class TwitterApi extends OAuthBaseClient {

	private static final String TAG = TwitterApi.class.getName();

	public static final Class<? extends Api> REST_API_CLASS = org.scribe.builder.api.TwitterApi.class;

	public TwitterApi(Context context) {
		super(context,
                REST_API_CLASS,
                TwitterApiConfig.getBaseUrl(),
                TwitterApiConfig.getConsumerKey(),
                TwitterApiConfig.getConsumerSecret(),
                TwitterApiConfig.getCallbackUrl());
	}

	public void getHomeTimeline(String maxId, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
        if (maxId != null) {
            params.put("max_id", maxId);
        }
		client.get(url, params, handler);
	}

	public void getMentionsTimeline(String maxId, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (maxId != null) {
			params.put("max_id", maxId);
		}
		client.get(url, params, handler);
	}

	public void getUserTimeline(String maxId, String screenName, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (maxId != null) {
			params.put("max_id", maxId);
            params.put("screen_name", screenName);
		}
		client.get(url, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();
        params.put("skip_status", "true");
		client.get(url, params, handler);
	}

	public void postTweet(String text, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", text);
		client.post(url, params, handler);
	}
}