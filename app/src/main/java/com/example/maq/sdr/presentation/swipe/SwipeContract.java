package com.example.maq.sdr.presentation.swipe;

import android.content.Context;

import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public interface SwipeContract {

    interface View {

        Context getCurrentContext();

        void showFriends(List<Friend> friendsList);
    }

    interface Presenter {

        void getFriends();

        void onActivityRestart();

        void onActivityStop();

        void saveMessage();
    }
}
