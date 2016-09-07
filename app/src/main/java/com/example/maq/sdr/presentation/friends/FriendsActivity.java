package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View{

    private FriendsPresenter mFriendsPresenter;

    private RecyclerView mRecyclerView;

    private FriendsAdapter mFriendsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friends);
        mRecyclerView = (RecyclerView) findViewById(R.id.friends_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(new ArrayList<Friend>(), this);
        mRecyclerView.setAdapter(mFriendsAdapter);

        if (VKAccessToken.currentToken() == null) {
            VKSdk.login(this, VKScope.FRIENDS, VKScope.MESSAGES);
        } else {
            ((MainApplication)getApplication())
                    .setVkToken(VKAccessToken.currentToken().accessToken);
            createPresenter(VKAccessToken.currentToken().accessToken);
            ((MainApplication)getApplication()).startService();
        }
    }

    @Override
    public void showFriends(List<Friend> friends) {
        mFriendsAdapter.replaceData(friends);
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                createPresenter(res.accessToken);
                ((MainApplication)getApplication()).startService();
                Log.i("Vk onResult token:", res.accessToken);
            }
            @Override
            public void onError(VKError error) {
                Log.e("Vk error", "onError");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        mFriendsPresenter.onActivityDestroy();
        super.onDestroy();
    }

    private void createPresenter(String vkToken) {
        DataSource dataSource = ((MainApplication)getApplication()).getDataSource();
        mFriendsPresenter = new FriendsPresenter(getLoaderManager(), dataSource, this);
        mFriendsPresenter.getFriends();
    }
}
