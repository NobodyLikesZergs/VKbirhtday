package com.example.maq.sdr.presentation.swipe;

import android.content.Context;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

import java.util.List;

public interface SwipeContract {

    interface View {

        Context getCurrentContext();

        void showFriends(List<Friend> friendsList);
    }

    interface Presenter {

        void getFriends();

        void onStart();

        void onStop();

        void saveMessage(Friend friend, Account account, Message message);
    }
}
