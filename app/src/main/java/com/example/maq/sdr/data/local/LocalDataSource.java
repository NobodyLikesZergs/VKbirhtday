package com.example.maq.sdr.data.local;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

import java.util.List;

public interface LocalDataSource {

    List<Friend> getFriends();

    void saveFriend(Friend friend);

    void saveFriends(List<Friend> friends);

    void saveMessage(Friend friend, Account account, Message message);
}
