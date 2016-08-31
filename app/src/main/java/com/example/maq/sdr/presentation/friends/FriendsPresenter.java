package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Friend>> {

    private final static int GET_FRIENDS_LOADER_ID = 1;

    private LoaderManager mLoaderManager;

    private DataSource mDataSource;

    private FriendsContract.View mFriendsView;

    private FriendsUpdateEventListener mEventListener;

    public FriendsPresenter(LoaderManager loaderManager, DataSource dataSource,
                            FriendsContract.View view) {
        mLoaderManager = loaderManager;
        mDataSource = dataSource;
        mFriendsView = view;
        mEventListener = new FriendsUpdateEventListener(this);
        MainApplication.getEventBus().register(mEventListener);
    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, this)
                .forceLoad();
    }

    public void showFriends(List<Friend> friends) {
        mFriendsView.showFriends(friends);
    }

    public void onActivityDestroy() {
        MainApplication.getEventBus().unregister(mEventListener);
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new GetFriendsLoader(mFriendsView.getCurrentContext(), mDataSource);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        Log.i(MainApplication.LOG_TAG, "GetFriendsLoader: load finished");
        this.showFriends(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {

    }
}
