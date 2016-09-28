package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.swipe.SwipeActivity;
import com.google.common.eventbus.EventBus;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View{

    private FriendsContract.Presenter mPresenter;

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
        }
        createPresenter();
    }

    @Override
    public void showFriends(List<Friend> friends) {
        mFriendsAdapter.replaceData(friends);
    }

    @Override
    public void showProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showConnectionErrorIcon() {
        ImageView imageView = (ImageView) findViewById(R.id.connection_error_icon);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectionErrorIcon() {
        ImageView imageView = (ImageView) findViewById(R.id.connection_error_icon);
        imageView.setVisibility(View.INVISIBLE);
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
                ((MainApplication) getApplication()).updateVkToken();
                mPresenter.getFriends();
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
    protected void onRestart() {
        Log.i(MainApplication.LOG_TAG, "Activity onRestart");
        super.onRestart();
        mPresenter.onActivityRestart();
    }

    @Override
    protected void onStop() {
        Log.i(MainApplication.LOG_TAG, "Activity onStop");
        mPresenter.onActivityStop();
        super.onStop();
    }

    private void createPresenter() {
        MainApplication application = (MainApplication) getApplication();
        DataSource dataSource = application.getDataSource();
        EventBus eventBus = application.getEventBus();
        mPresenter = new FriendsPresenter(getLoaderManager(), dataSource, this, eventBus);
        mPresenter.onActivityRestart();
        mPresenter.getFriends();
    }



    public void onButtonClick(View view) {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }
}
