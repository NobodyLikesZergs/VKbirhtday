package com.example.maq.sdr.presentation.friends;

import android.util.Log;

import com.example.maq.sdr.presentation.MainApplication;
import com.google.common.eventbus.Subscribe;

public class FriendsUpdateEventListener {

    private FriendsPresenter mPresenter;

    public FriendsUpdateEventListener(FriendsPresenter presenter) {
        mPresenter = presenter;
    }

    @Subscribe
    public void task(FriendsUpdateEvent e) {
        mPresenter.getFriends(false);
        Log.i(MainApplication.LOG_TAG, "FriendsUpdateEvent");
    }
}
