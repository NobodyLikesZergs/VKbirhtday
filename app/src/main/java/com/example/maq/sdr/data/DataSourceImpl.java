package com.example.maq.sdr.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.data.remote.WrongTokenException;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.events.FriendsUpdateEvent;
import com.google.common.eventbus.EventBus;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class DataSourceImpl implements DataSource{

    private LocalDataSource mLocalDataSource;

    private RemoteDataSource mRemoteDataSource;

    private static DataSourceImpl INSTANCE;

    private EventBus mEventBus;

    private DateTime mLastFriendsUpdate;

    private DataSourceImpl(LocalDataSource localDataSource, RemoteDataSource remoteDataSource,
                           EventBus eventBus) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mLastFriendsUpdate = new DateTime().minusHours(1);
        mEventBus = eventBus;
    }

    public static DataSourceImpl getInstance(LocalDataSource localDataSource,
                                             RemoteDataSource remoteDataSource,
                                             EventBus eventBus) {
        if (INSTANCE == null)
            INSTANCE = new DataSourceImpl(localDataSource, remoteDataSource, eventBus);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        List<Friend> result = mLocalDataSource.getFriends();
        refreshFriends();
        return result;
    }

    @Override
    public void sendMessage(Account account, Message message) {
        mRemoteDataSource.sendMessage(account, message);
    }

    @Override
    public void saveFriends(List<Friend> friends) {
        mLocalDataSource.saveFriends(friends);
    }

    @Override
    public void saveMessage(Friend friend, Account account, Message message) {
        mLocalDataSource.saveMessage(friend, account, message);
    }

    private void refreshFriends() {
        DateTime currentTime = new DateTime();
        if (currentTime.minusMinutes(1).getMillis() > mLastFriendsUpdate.getMillis()) {
            Log.i(MainApplication.LOG_TAG, "refreshFriends");
            new RefreshFriendsTask().execute();
        } else {
            mEventBus.post(new FriendsUpdateEvent(FriendsUpdateEvent.Result.NOT_NEED));
        }
    }

    class RefreshFriendsTask extends AsyncTask<Void, Void, Void> {

        FriendsUpdateEvent result;

        @Override
        protected Void doInBackground(Void... params) {
            DateTime currentTime = new DateTime();
            try {
                mLocalDataSource.saveFriends(mRemoteDataSource.getFriends());
                result = new FriendsUpdateEvent(FriendsUpdateEvent.Result.OK);
                mLastFriendsUpdate = currentTime;
            } catch (WrongTokenException e) {
                result = new FriendsUpdateEvent(FriendsUpdateEvent.Result.WRONG_TOKEN);
            } catch (IOException e) {
                result = new FriendsUpdateEvent(FriendsUpdateEvent.Result.CONNECTION_ERROR);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mEventBus.post(result);
        }
    }
}
