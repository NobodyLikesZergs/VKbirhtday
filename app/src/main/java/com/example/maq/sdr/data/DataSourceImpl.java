package com.example.maq.sdr.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.friends.FriendsUpdateEvent;

import org.joda.time.DateTime;

import java.util.List;

public class DataSourceImpl implements DataSource{

    private LocalDataSource mLocalDataSource;

    private RemoteDataSource mRemoteDataSource;

    private static DataSourceImpl INSTANCE;

    private DateTime mLastFriendsUpdate;

    private DataSourceImpl(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mLastFriendsUpdate = new DateTime().minusHours(1);
    }

    public static DataSourceImpl getInstance(LocalDataSource localDataSource,
                                             RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new DataSourceImpl(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        List<Friend> result = mLocalDataSource.getFriends();
        refreshFriends();
        return result;
    }

    @Override
    public Friend getFriend(int id) {
        return null;
    }

    @Override
    public void sendMessage(Message message) {
        mRemoteDataSource.sendMessage(message);
    }

    @Override
    public void saveFriend(Friend friend) {

    }

    @Override
    public void saveFriends(List<Friend> friends) {
        mLocalDataSource.saveFriends(friends);
    }

    @Override
    public List<Message> getMessages() {
        return mLocalDataSource.getMessages();
    }

    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public void setVkToken(String vkToken) {
        mRemoteDataSource.setVkToken(vkToken);
    }

    private void refreshFriends() {
        DateTime currentTime = new DateTime();
        if (currentTime.minusMinutes(15).getMillis() > mLastFriendsUpdate.getMillis()) {
            Log.i(MainApplication.LOG_TAG, "refreshFriends");
            new RefreshFriendsTask().execute();
            mLastFriendsUpdate = currentTime;
        }
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
