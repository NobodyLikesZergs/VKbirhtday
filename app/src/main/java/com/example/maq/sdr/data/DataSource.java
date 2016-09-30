package com.example.maq.sdr.data;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

import java.util.List;

public interface DataSource {

    List<Friend> getFriends();

    void sendMessage(Account account, Message message);

    void saveFriend(Friend friend);

    void saveFriends(List<Friend> friends);

    void saveMessage(Friend friend, Account account, Message message);

    void setVkToken(String vkToken);
}
