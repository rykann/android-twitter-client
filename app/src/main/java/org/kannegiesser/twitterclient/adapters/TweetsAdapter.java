package org.kannegiesser.twitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.models.Tweet;

import java.util.List;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    //TODO: implement view holder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        // if a recycled view wasn't given, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.user.profileImageUrl).noFade().into(ivProfileImage);

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(tweet.user.name);

        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        tvScreenName.setText("@" + tweet.user.screenName);

        TextView tvTweetText = (TextView) convertView.findViewById(R.id.tvTweetText);
        tvTweetText.setText(tweet.text);

        return convertView;
    }
}
