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

    private static class ViewHolder {
        ImageView profileImage;
        TextView userName;
        TextView screenName;
        TextView tweetText;

        static ViewHolder build(View view) {
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.profileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
            viewHolder.userName = (TextView) view.findViewById(R.id.tvUserName);
            viewHolder.screenName = (TextView) view.findViewById(R.id.tvScreenName);
            viewHolder.tweetText = (TextView) view.findViewById(R.id.tvTweetText);
            return viewHolder;
        }
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // if a recycled view wasn't given, inflate a new one and cache its views
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = ViewHolder.build(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        populateViews(viewHolder, getItem(position));
        return convertView;
    }

    private void populateViews(ViewHolder viewHolder, Tweet tweet) {
        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.user.profileImageUrl).noFade().into(viewHolder.profileImage);
        viewHolder.userName.setText(tweet.user.name);
        viewHolder.screenName.setText("@" + tweet.user.screenName);
        viewHolder.tweetText.setText(tweet.text);
    }
}
