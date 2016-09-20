package com.example.maq.sdr.presentation.swipe;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
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
        createPresenter();
        mSwipeDeck = (SwipeDeck) findViewById(R.id.swipe_deck);
        mAdapter = new SwipeDeckAdapter(new ArrayList<Friend>(), this);
        mSwipeDeck.setAdapter(mAdapter);
        mSwipeDeck.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long positionInAdapter) {
                Log.i("MainActivity", "card was swiped left");
                Friend friend = (Friend) mAdapter.getItem((int) positionInAdapter);
                if (friend.getBirthDate() == null)
                    return;
                for (Account account: friend.getAccountList()) {
                    mPresenter.saveMessage(friend, account,
                            new Message(account.getId(), null, friend.getBirthDate()));
                }
            }

            @Override
            public void cardSwipedRight(long positionInAdapter) {
                Log.i("MainActivity", "card was swiped right");
                Friend friend = (Friend) mAdapter.getItem((int) positionInAdapter);
                if (friend.getBirthDate() == null)
                    return;
                EditText editText = (EditText) findViewById(R.id.message_text);
                for (Account account: friend.getAccountList()) {
                    mPresenter.saveMessage(friend, account,
                            new Message(account.getId(), editText.getText().toString(),
                                    friend.getBirthDate()));
                }
            }
        });
        Button swipeLeftButton = (Button) findViewById(R.id.swipe_left_button);
        swipeLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   
                mSwipeDeck.swipeTopCardLeft(500);
            }
        });
        Button swipeRigthButton = (Button) findViewById(R.id.swipe_rigth_button);
        swipeRigthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeDeck.swipeTopCardRight(200);
            }
        });
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