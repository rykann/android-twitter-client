package org.kannegiesser.twitterclient.listeners;

import android.util.Log;
import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private static String TAG = EndlessScrollListener.class.getName();

    private int visibleThreshold = 5;
    private int previousTotal = 0;
    private boolean loading = true;

    public EndlessScrollListener() {}

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d(TAG, String.format("[onScroll]: firstVisibleItem: %d, visibleItemCount: %d, totalItemCount: %d", firstVisibleItem, visibleItemCount, totalItemCount));

        if (loading) {
            if (totalItemCount > previousTotal) {
                // total item count grew, so we must be done loading
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && firstVisibleItem + visibleItemCount > totalItemCount - visibleThreshold) {
            onLoadMore();
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d(TAG, "[onScrollStateChanged] scrollState: " + scrollState);
    }

    public abstract void onLoadMore();
}
