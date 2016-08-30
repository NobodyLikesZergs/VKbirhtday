package com.example.maq.sdr.data;

import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public interface DataSource extends LocalDataSource, RemoteDataSource{

    List<Friend> getFriends(boolean refreshFriends);

}
