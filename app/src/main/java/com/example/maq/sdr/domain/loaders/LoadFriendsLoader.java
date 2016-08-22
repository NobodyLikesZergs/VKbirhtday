package com.example.maq.sdr.domain.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public class LoadFriendsLoader extends AsyncTaskLoader<List<Friend>>{

    private DataSource mRepository;

    public LoadFriendsLoader(Context context, DataSource dataRepository) {
        super(context);
        mRepository = dataRepository;
    }

    @Override
    public List<Friend> loadInBackground() {
        Log.i("LoadFriendsLoader", "loadInBackground");
        return mRepository.loadFriends();
    }

    @Override
    public void deliverResult(List<Friend> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
