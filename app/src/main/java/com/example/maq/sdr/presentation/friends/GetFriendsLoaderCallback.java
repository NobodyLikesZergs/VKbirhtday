package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public class GetFriendsLoaderCallback implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private FriendsPresenter mPresenter;

    private Loader mLoader;

    public GetFriendsLoaderCallback(FriendsPresenter presenter, Loader loader) {
        mPresenter = presenter;
        mLoader = loader;
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        Log.i("Loader", String.valueOf(data==null));
        mPresenter.showFriends(data);
        mPresenter.loadFriends();
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {

    }
}
