package com.example.maq.sdr.domain.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;

import java.util.LinkedList;
import java.util.List;

public class GetUntunedFriendsLoader extends AsyncTaskLoader<List<Friend>> {

    private DataSource mDataSource;

    public GetUntunedFriendsLoader(Context context, DataSource dataSource) {
        super(context);
        mDataSource = dataSource;
    }

    @Override
    public List<Friend> loadInBackground() {
        List<Friend> friendList = mDataSource.getFriends();
        List<Friend> result = new LinkedList<>();
        for (Friend friend: friendList) {
            if (isUntuned(friend)) {
                result.add(friend);
            }
        }
        return result;
    }

    private boolean isUntuned(Friend friend) {
        for (Account account: friend.getAccountList()) {
            if (!account.getMessageList().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
