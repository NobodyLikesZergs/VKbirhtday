package com.example.maq.sdr.data;

import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.Template;

import java.util.List;

public class DataSourceImpl implements DataSource{

    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private static DataSourceImpl INSTANCE;

    private DataSourceImpl(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static DataSourceImpl getInstance(LocalDataSource localDataSource,
                                      RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new DataSourceImpl(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        return mLocalDataSource.getFriends();
    }

    @Override
    public List<Friend> loadFriends() {
        List<Friend> result = mRemoteDataSource.loadFriends();
        saveFriends(result);
        return result;
    }

    @Override
    public Friend loadFriend(int id) {
        return null;
    }

    @Override
    public List<Friend> getUnresolvedFriends() {
        return null;
    }

    @Override
    public Friend getFriend(int id) {
        return null;
    }

    @Override
    public void saveFriend(Friend friend) {

    }

    @Override
    public void saveFriends(List<Friend> friends) {
        mLocalDataSource.saveFriends(friends);
    }

    @Override
    public List<Account> getAccountsByFriend(Friend friend) {
        return null;
    }

    @Override
    public List<Message> getMessages() {
        return null;
    }

    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public List<Template> getTemplates() {
        return null;
    }

    @Override
    public void saveTemplate(Template template) {

    }
}
