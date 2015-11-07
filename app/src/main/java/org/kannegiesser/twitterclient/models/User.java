package org.kannegiesser.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String id;
    public String name;
    public String screenName;
    public String profileImageUrl;

    public static User fromJson(JSONObject json) throws JSONException {
        User user = new User();
        user.id = json.getString("id_str");
        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        return user;
    }
}
