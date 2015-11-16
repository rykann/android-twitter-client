package org.kannegiesser.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String id;
    public String name;
    public String screenName;
    public String profileImageUrl;
    public String profileBannerUrl;
    public String description;
    public int followingCount;
    public int followersCount;

    public static User fromJson(JSONObject json) throws JSONException {
        User user = new User();
        user.id = json.getString("id_str");
        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.profileBannerUrl = json.optString("profile_banner_url");
        user.description = json.getString("description");
        user.followingCount = json.getInt("friends_count");
        user.followersCount = json.getInt("followers_count");
        return user;
    }
}
