package org.kannegiesser.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;
import org.kannegiesser.twitterclient.R;
import org.kannegiesser.twitterclient.TwitterApplication;
import org.kannegiesser.twitterclient.clients.TwitterApi;

public class ComposeTweetDialog extends DialogFragment {

    public interface ComposeTweetDialogListener {
        void onTweetPosted();
    }

    private static final String TAG = ComposeTweetDialog.class.getName();

    private EditText etTweet;

    public ComposeTweetDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View ivCancel = view.findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        View btnTweet = view.findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterApi twitterApi = TwitterApplication.getTwitterApi();
                String tweetText = etTweet.getText().toString();
                Log.d(TAG, "Posting tweet: " + tweetText);
                twitterApi.postTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //Toast.makeText(getContext(), "successfully posted tweet", Toast.LENGTH_SHORT).show();
                        ComposeTweetDialogListener listener = (ComposeTweetDialogListener) getActivity();
                        listener.onTweetPosted();
                        dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        Log.e(TAG, String.format("Failed to post tweet. status: %d, response: %s", statusCode, response), throwable);
                        Toast.makeText(getContext(), "Sorry, we were unable to post your tweet.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }));

        etTweet = (EditText) view.findViewById(R.id.etTweet);
        // Show soft keyboard automatically and request focus to field
        etTweet.requestFocus();

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
