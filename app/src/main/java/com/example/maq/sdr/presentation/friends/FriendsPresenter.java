package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;
import com.google.common.eventbus.EventBus;

import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Friend>> {

    private final static int GET_FRIENDS_LOADER_ID = 1;

    private LoaderManager mLoaderManager;

    private DataSource mDataSource;

    private FriendsContract.View mFriendsView;

    private FriendsEventListener mEventListener;

    private EventBus mEventBus;

    public FriendsPresenter(LoaderManager loaderManager, DataSource dataSource,
                            FriendsContract.View view, EventBus eventBus) {
        mLoaderManager = loaderManager;
        mDataSource = dataSource;
        mFriendsView = view;
        mEventListener = new FriendsEventListener(this, view);
        mEventBus = eventBus;
    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, this)
                .forceLoad();
        mFriendsView.showProgressBar();
    }

    public void showFriends(List<Friend> friends) {
        mFriendsView.showFriends(friends);
    }

    public void onActivityRestart() {
        mEventBus.register(mEventListener);
    }

    public void onActivityStop() {
        mEventBus.unregister(mEventListener);
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
