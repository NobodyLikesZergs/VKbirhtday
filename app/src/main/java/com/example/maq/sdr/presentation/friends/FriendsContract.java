package com.example.maq.sdr.presentation.friends;

import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public interface FriendsContract {

    interface View {

        void showFriends(List<Friend> friends);

    }

    interface Presenter {

        void getFriends();

        void loadFriends();
    }
}
