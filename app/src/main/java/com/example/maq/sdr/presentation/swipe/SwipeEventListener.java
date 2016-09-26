package com.example.maq.sdr.presentation.swipe;

import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.Subscribe;

public class SwipeEventListener {

    private SwipePresenter mPresenter;

    public SwipeEventListener(SwipePresenter presenter) {
        mPresenter = presenter;
    }

    @Subscribe
    public void task(FriendsUpdateEvent e) {
        if (e.getResult() == FriendsUpdateEvent.Result.OK) {
            mPresenter.getFriends();
        } else if (e.getResult() == FriendsUpdateEvent.Result.NOT_NEED) {
        } else if (e.getResult() == FriendsUpdateEvent.Result.CONNECTION_ERROR) {
        };
    }
}
