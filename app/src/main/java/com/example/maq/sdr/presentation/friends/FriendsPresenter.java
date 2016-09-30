package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Friend>> {

    private final static int GET_FRIENDS_LOADER_ID = 1;

    private LoaderManager mLoaderManager;

    private DataSource mDataSource;

    private FriendsContract.View mFriendsView;

    private EventBus mEventBus;

    public FriendsPresenter(LoaderManager loaderManager, DataSource dataSource,
                            FriendsContract.View view, EventBus eventBus) {
        mLoaderManager = loaderManager;
        mDataSource = dataSource;
        mFriendsView = view;
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
        mEventBus.register(this);
    }

    public void onActivityStop() {
        mEventBus.unregister(this);
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

    @Subscribe
    public void task(FriendsUpdateEvent e) {
        if (e.getResult() == FriendsUpdateEvent.Result.OK) {
            getFriends();
            mFriendsView.hideConnectionErrorIcon();
        } else if (e.getResult() == FriendsUpdateEvent.Result.CONNECTION_ERROR) {
            mFriendsView.showConnectionErrorIcon();
        } else if (e.getResult() == FriendsUpdateEvent.Result.WRONG_TOKEN) {
            Log.i(MainApplication.LOG_TAG, "FriendsPresenter: WRONG_TOKEN");
        }
        mFriendsView.hideProgressBar();
        Log.i(MainApplication.LOG_TAG, "FriendsUpdateEvent");
    }
}
