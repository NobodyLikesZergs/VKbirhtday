package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements
        SwipeContract.View {

    private SwipeDeck mSwipeDeck;
    private SwipeDeckAdapter mAdapter;
    private SwipeContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_layout);
        mSwipeDeck = (SwipeDeck) findViewById(R.id.swipe_deck);
        mAdapter = new SwipeDeckAdapter(new ArrayList<Friend>(), this);
        mSwipeDeck.setAdapter(mAdapter);
        mSwipeDeck.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long positionInAdapter) {
                Log.i("MainActivity", "card was swiped left");
            }

            @Override
            public void cardSwipedRight(long positionInAdapter) {
                Log.i("MainActivity", "card was swiped right");
            }
        });
        createPresenter();
    }

    @Override
    protected void onStop() {
        mPresenter.onActivityStop();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onActivityRestart();
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    public void showFriends(List<Friend> friendsList) {
        mAdapter.setData(friendsList);
    }

    private void createPresenter() {
        DataSource dataSource = ((MainApplication)getApplication()).getDataSource();
        LoaderManager loaderManager = getLoaderManager();
        mPresenter = new SwipePresenter(dataSource, loaderManager, this);
        mPresenter.getFriends();
    }
}