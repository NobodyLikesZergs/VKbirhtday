package com.example.maq.sdr.presentation.friends;

import android.app.LoaderManager;

import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.loaders.GetFriendsLoader;
import com.example.maq.sdr.domain.loaders.LoadFriendsLoader;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.List;

public class FriendsPresenter implements FriendsContract.Presenter{

    private final static int LOAD_FRIENDS_LOADER_ID = 1;

    private final static int GET_FRIENDS_LOADER_ID = 2;

    private LoaderManager mLoaderManager;

    private LoadFriendsLoader mLoadFriendsLoader;

    private GetFriendsLoader mGetFriendsLoader;

    private FriendsContract.View mFriendsView;

    private FriendsUpdateEventListener eventListener;

    public FriendsPresenter(LoaderManager loaderManager, LoadFriendsLoader loadFriendsLoader,
                            GetFriendsLoader getFriendsLoader, FriendsContract.View view) {
        mLoaderManager = loaderManager;
        mLoadFriendsLoader = loadFriendsLoader;
        mGetFriendsLoader = getFriendsLoader;
        mFriendsView = view;
        eventListener = new FriendsUpdateEventListener(this);
        MainApplication.getEventBus().register(eventListener);
    }

    @Override
    public void getFriends() {
        mLoaderManager.initLoader(GET_FRIENDS_LOADER_ID, null,
                new GetFriendsLoaderCallback(this, mGetFriendsLoader))
                .forceLoad();
    }

    @Override
    public void loadFriends() {
        mLoaderManager.initLoader(LOAD_FRIENDS_LOADER_ID, null,
                new LoadFriendsLoaderCallback(mLoadFriendsLoader, this)).forceLoad();
    }

    public void showFriends(List<Friend> friends) {
        mFriendsView.showFriends(friends);
    }

    public void onActivityDestroy() {
        MainApplication.getEventBus().unregister(eventListener);
    }
}
