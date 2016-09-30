package com.example.maq.sdr.data.remote;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;

import java.io.IOException;
import java.util.List;

public interface RemoteDataSource {

    List<Friend> getFriends() throws IOException, WrongTokenException;

    void sendMessage(Account account, Message message);

}
