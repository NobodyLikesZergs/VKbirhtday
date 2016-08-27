package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.LoadFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.List;

public class LoadFriendsLoaderCallback implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private LoadFriendsLoader mLoader;
    private FriendsPresenter mPresenter;

    public LoadFriendsLoaderCallback (LoadFriendsLoader loadFriendsLoader,
                                      FriendsPresenter friendsPresenter) {
        mLoader = loadFriendsLoader;
        mPresenter = friendsPresenter;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        Log.i(MainApplication.LOG_TAG, "LoadFriendsLoader: load finished");
        mPresenter.showFriends(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
