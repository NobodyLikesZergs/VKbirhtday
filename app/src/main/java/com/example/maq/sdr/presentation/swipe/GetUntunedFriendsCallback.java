package com.example.maq.sdr.presentation.swipe;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetUntunedFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.List;

public class GetUntunedFriendsCallback implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private SwipeContract.View mSwipeView;
    private DataSource mDataSource;

    public GetUntunedFriendsCallback(SwipeContract.View view, DataSource dataSource) {
        mSwipeView = view;
        mDataSource = dataSource;
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new GetUntunedFriendsLoader(mSwipeView.getCurrentContext(), mDataSource);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        Log.i(MainApplication.LOG_TAG, "getUntunedFriendsLoader: load finished");
        mSwipeView.showFriends(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {

    }
}
