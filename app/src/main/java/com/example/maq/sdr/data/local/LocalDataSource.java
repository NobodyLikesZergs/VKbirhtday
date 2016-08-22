package com.example.maq.sdr.data.local;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.Template;

import java.util.List;

public interface LocalDataSource {

    List<Friend> getFriends();

    List<Friend> getUnresolvedFriends();

    Friend getFriend(int id);

    void saveFriend(Friend friend);

    void saveFriends(List<Friend> friends);

    List<Account> getAccountsByFriend(Friend friend);

    List<Message> getMessages();

    void saveMessage(Message message);

    List<Template> getTemplates();

    void saveTemplate(Template template);
}
