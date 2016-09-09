package com.example.maq.sdr.data.remote;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

import java.util.List;

public interface RemoteDataSource {

    List<Friend> getFriends();

    Friend getFriend(int id);

    void sendMessage(Account account, Message message);

    void setVkToken(String vkToken);
}
