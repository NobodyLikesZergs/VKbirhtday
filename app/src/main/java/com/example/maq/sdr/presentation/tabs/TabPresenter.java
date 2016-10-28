package com.example.maq.sdr.presentation.tabs;

import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TabPresenter implements TabContract.Presenter {

    private TabContract.View mTabView;

    private EventBus mEventBus;

    public TabPresenter(TabContract.View view, EventBus eventBus) {
        mTabView = view;
        mEventBus = eventBus;
    }

    @Override
    public void onActivityStart() {
        mEventBus.register(this);
    }

    @Override
    public void onActivityStop() {
        mEventBus.unregister(this);
    }


    @Subscribe
    public void task(FriendsUpdateEvent e) {
        if (e.getResult() == FriendsUpdateEvent.Result.CONNECTION_ERROR) {
            mTabView.showConnectionErrorIcon();
        } else {
            mTabView.hideConnectionErrorIcon();
        }
    }
}
