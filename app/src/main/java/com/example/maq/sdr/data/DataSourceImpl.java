package com.example.maq.sdr.data;

import android.os.AsyncTask;
import android.util.Log;

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
    public List<Friend> getFriends(boolean refreshFriends) {
        List<Friend> result = getFriends();
        if (refreshFriends) {
            refreshFriends();
        }
        return result;
    }

    @Override
    public List<Friend> getFriends() {
        return mLocalDataSource.getFriends();
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
    public void setVkToken(String vkToken) {
        mRemoteDataSource.setVkToken(vkToken);
    }

    private void refreshFriends() {
        Log.i(MainApplication.LOG_TAG, "refreshFriends");
        new RefreshFriendsTask().execute();
    }

    class RefreshFriendsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mLocalDataSource.saveFriends(mRemoteDataSource.getFriends());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainApplication.getEventBus().post(new FriendsUpdateEvent(true));
        }
    }
}
