package com.example.maq.sdr.presentation.friends;

import android.util.Log;

import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.Subscribe;

public class FriendsEventListener {

    private FriendsContract.Presenter mPresenter;
    private FriendsContract.View mView;

    public FriendsEventListener(FriendsContract.Presenter presenter,
                                FriendsContract.View view) {
        mPresenter = presenter;
        mView = view;
    }

    @Subscribe
    public void task(FriendsUpdateEvent e) {
        if (e.getResult() == FriendsUpdateEvent.Result.OK) {
            mPresenter.getFriends();
            mView.hideConnectionErrorIcon();
        } else if (e.getResult() == FriendsUpdateEvent.Result.NOT_NEED) {
            mView.hideProgressBar();
        } else if (e.getResult() == FriendsUpdateEvent.Result.CONNECTION_ERROR) {
            mView.showConnectionErrorIcon();
        }
        mView.hideProgressBar();
        Log.i(MainApplication.LOG_TAG, "FriendsUpdateEvent");
    }
}
