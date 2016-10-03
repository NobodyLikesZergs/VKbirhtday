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
import com.example.maq.sdr.presentation.authorization.AuthorizationActivity;
import com.example.maq.sdr.presentation.swipe.SwipeActivity;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View{

    private FriendsContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private FriendsAdapter mFriendsAdapter;

    private MainApplication mainApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication) getApplication();

        setContentView(R.layout.friends);
        mRecyclerView = (RecyclerView) findViewById(R.id.friends_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(new ArrayList<Friend>(), this);
        mRecyclerView.setAdapter(mFriendsAdapter);

        if (mainApp.getAuthManager().isEmpty()) {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
        }
        createPresenter();
    }

    @Override
    protected void onRestart() {
        Log.i(MainApplication.LOG_TAG, "Activity onRestart");
        super.onRestart();
        mPresenter.onActivityRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getFriends();
    }

    @Override
    protected void onStop() {
        Log.i(MainApplication.LOG_TAG, "Activity onStop");
        mPresenter.onActivityStop();
        super.onStop();
    }

    private void createPresenter() {
        DataSource dataSource = mainApp.getDataSource();
        EventBus eventBus = mainApp.getEventBus();
        mPresenter = new FriendsPresenter(getLoaderManager(), dataSource, this, eventBus);
        mPresenter.onActivityRestart();
        mPresenter.getFriends();
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFriends(List<Friend> friends) {
        mFriendsAdapter.replaceData(friends);
    }

    @Override
    public void showProgressBar() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showConnectionErrorIcon() {
        final ImageView imageView = (ImageView) findViewById(R.id.connection_error_icon);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideConnectionErrorIcon() {
        final ImageView imageView = (ImageView) findViewById(R.id.connection_error_icon);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }
}
