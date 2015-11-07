package org.kannegiesser.twitterclient.clients;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

public class TwitterApiConfig {

    private static final String TAG = TwitterApiConfig.class.getName();
    private static Properties properties;

    public static void load(Context context) {
        try {
            properties = new Properties();
            AssetManager assetManager = context.getAssets();
            properties.load(assetManager.open("twitter-api.properties"));
        } catch (IOException e) {
            Log.e(TAG, "Unable to load Twitter api configuration", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getCallbackUrl() {
        return properties.getProperty("callback.url");
    }

    public static String getConsumerKey() {
        return properties.getProperty("consumer.key");
    }

    public static String getConsumerSecret() {
        return properties.getProperty("consumer.secret");
    }
}