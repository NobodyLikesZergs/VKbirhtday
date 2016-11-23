package com.example.maq.sdr.presentation.friends;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.loaders.DeleteMessagesByFriend;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.ArrayList;

public class DeleteMessagesCallback implements LoaderManager.LoaderCallbacks<Void> {

    private Friend mFriend;
    private FriendsContract.View mFriendsView;
    private DataSource mDataSource;

    public DeleteMessagesCallback(FriendsContract.View friendsView, DataSource dataSource, Friend friend) {
        mFriend = friend;
        mDataSource = dataSource;
        mFriendsView = friendsView;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new DeleteMessagesByFriend(mFriendsView.getCurrentContext(), mDataSource, mFriend);
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        Log.i(MainApplication.LOG_TAG, "DeleteMessageLoader: loadFinished");
        for (Account account: mFriend.getAccountList()) {
            account.setMessageList(new ArrayList<Message>());
        }
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
