package com.example.maq.sdr.presentation.friends;

import android.content.Context;

import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public interface FriendsContract {

    interface View {

        void showFriends(List<Friend> friends);

        Context getCurrentContext();
    }

    interface Presenter {

        void getFriends(boolean refreshList);

    }
}
