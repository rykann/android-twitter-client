package org.kannegiesser.twitterclient.clients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterApi extends OAuthBaseClient {
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
}