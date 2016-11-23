package com.example.maq.sdr.domain.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;

public class DeleteMessagesByFriend extends AsyncTaskLoader<Void> {

    private DataSource mRepository;
    private Friend mFriend;

    public DeleteMessagesByFriend(Context context, DataSource dataRepository, Friend friend) {
        super(context);
        mRepository = dataRepository;
        mFriend = friend;
    }

    @Override
    public Void loadInBackground() {
        mRepository.deleteMessagesByFriend(mFriend);
        return null;
    }
}
