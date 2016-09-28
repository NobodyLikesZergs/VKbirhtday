package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;
import android.os.AsyncTask;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class SwipePresenter implements SwipeContract.Presenter {

    private static final int GET_FRIENDS_LOADER_ID = 2;

    private DataSource mDataSource;

    private LoaderManager mLoaderManager;

    private SwipeContract.View mSwipeView;

    private GetUntunedFriendsCallback mGetFriendsCallback;

    private EventBus mEventBus;

    public SwipePresenter(DataSource dataSource, LoaderManager loaderManager,
                          SwipeContract.View view, EventBus eventBus) {
        mDataSource = dataSource;
        mLoaderManager = loaderManager;
        mSwipeView = view;
        mGetFriendsCallback = new GetUntunedFriendsCallback(mSwipeView, dataSource);
        mEventBus = eventBus;
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

    public void onActivityRestart() {
        mEventBus.register(this);
    }

    public void onActivityStop() {
        mEventBus.unregister(this);
    }

    @Override
    public void getFriends() {
        mLoaderManager.restartLoader(GET_FRIENDS_LOADER_ID, null, mGetFriendsCallback).forceLoad();
    }

    @Subscribe
    public void task(FriendsUpdateEvent e) {
        if (e.getResult() == FriendsUpdateEvent.Result.OK) {
            getFriends();
        } else if (e.getResult() == FriendsUpdateEvent.Result.NOT_NEED) {
        } else if (e.getResult() == FriendsUpdateEvent.Result.CONNECTION_ERROR) {
        };
    }

}
