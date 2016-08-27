package com.example.maq.sdr.data.remote;

import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public interface RemoteDataSource {

    List<Friend> loadFriends();

    Friend loadFriend(int id);

    void setVkToken(String vkToken);

}
