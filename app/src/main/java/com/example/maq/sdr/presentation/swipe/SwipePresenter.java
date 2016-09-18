package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;
import android.os.AsyncTask;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

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
        mGetFriendsCallback = new GetUntunedFriendsCallback(mSwipeView, dataSource);
    }

    @Override
    public void onActivityStop() {

    }

    @Override
    public void saveMessage(final Friend friend, final Account account, final Message message) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mDataSource.saveMessage(friend, account, message);
                return null;
            }
        }.execute();
    }

    @Override
    public void onActivityRestart() {

    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, mGetFriendsCallback).forceLoad();
    }


}
