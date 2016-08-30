package com.example.maq.sdr.domain.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;

import java.util.List;

public class GetFriendsLoader extends AsyncTaskLoader<List<Friend>>{

    private DataSource mRepository;

    private boolean refreshList;

    public GetFriendsLoader(Context context, DataSource dataRepository, boolean refreshList) {
        super(context);
        mRepository = dataRepository;
        this.refreshList = refreshList;
    }

    @Override
    public List<Friend> loadInBackground() {
        return mRepository.getFriends(refreshList);
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
