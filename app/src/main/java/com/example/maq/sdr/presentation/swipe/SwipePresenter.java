package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;

import com.example.maq.sdr.data.DataSource;

public class SwipePresenter implements SwipeContract.Presenter {

    private static final int GET_FRIENDS_LOADER_ID = 2;

    private DataSource mDataSource;

    private LoaderManager mLoaderManager;

    private SwipeContract.View mSwipeView;

    private GetUntunedFriendsCallback mGetFriendsCallback;

    public SwipePresenter(DataSource dataSource, LoaderManager loaderManager,
                          SwipeContract.View view) {
        mDataSource = dataSource;
        mLoaderManager = loaderManager;
        mSwipeView = view;
        mGetFriendsCallback = new GetUntunedFriendsCallback(view, dataSource);
    }

    @Override
    public void onActivityStop() {

    }

    @Override
    public void saveMessage() {

    }

    @Override
    public void onActivityRestart() {

    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, mGetFriendsCallback).forceLoad();
    }


}
