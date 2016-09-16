package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetFriendsLoader;

import java.util.List;

public class SwipePresenter implements SwipeContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Friend>> {

    private static final int GET_FRIENDS_LOADER_ID = 2;
    private DataSource mDataSource;
    private LoaderManager mLoaderManager;
    private SwipeContract.View mSwipeView;

    public SwipePresenter(DataSource dataSource, LoaderManager loaderManager,
                          SwipeContract.View view) {
        mDataSource = dataSource;
        mLoaderManager = loaderManager;
        mSwipeView = view;
    }

    @Override
    public void onActivityStop() {

    }

    @Override
    public void onActivityRestart() {

    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, this).forceLoad();
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new GetFriendsLoader(mSwipeView.getCurrentContext(), mDataSource);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        mSwipeView.showFriends(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {

    }
}
