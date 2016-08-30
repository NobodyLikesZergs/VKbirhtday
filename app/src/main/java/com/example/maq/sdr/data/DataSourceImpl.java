package com.example.maq.sdr.data;

import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.friends.FriendsUpdateEvent;

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
        refreshFriends();
        return mLocalDataSource.getFriends();
    }

    @Override
    public void saveFriend(Friend friend) {

    }

    @Override
    public void saveFriends(List<Friend> friends) {
        mLocalDataSource.saveFriends(friends);
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
    public void setVkToken(String vkToken) {
        mRemoteDataSource.setVkToken(vkToken);
    }

    private void refreshFriends() {
        MainApplication.getEventBus().post(new FriendsUpdateEvent(true));
    }

}
