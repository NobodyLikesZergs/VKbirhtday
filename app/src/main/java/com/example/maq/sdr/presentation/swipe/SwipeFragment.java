package com.example.maq.sdr.presentation.swipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.presentation.MainApplication;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SwipeFragment extends Fragment implements
        SwipeContract.View {

    private SwipeDeck mSwipeDeck;
    private SwipeDeckAdapter mAdapter;
    private SwipeContract.Presenter mPresenter;
    private MainApplication mApplication;

    public static SwipeFragment newInstance(int page) {
        SwipeFragment swipeFragment = new SwipeFragment();
        return swipeFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MainApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.swipe_layout, null);
        mSwipeDeck = (SwipeDeck) view.findViewById(R.id.swipe_deck);
        mAdapter = new SwipeDeckAdapter(new ArrayList<Friend>(), mApplication);
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
                EditText editText = (EditText) view.findViewById(R.id.message_text);
                for (Account account: friend.getAccountList()) {
                    mPresenter.saveMessage(friend, account,
                            new Message(account.getId(), editText.getText().toString(),
                                    friend.getBirthDate()));
                }
            }
        });
        Button swipeLeftButton = (Button) view.findViewById(R.id.swipe_left_button);
        swipeLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeDeck.swipeTopCardLeft(500);
            }
        });
        Button swipeRigthButton = (Button) view.findViewById(R.id.swipe_rigth_button);
        swipeRigthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeDeck.swipeTopCardRight(200);
            }
        });
        createPresenter();
        return view;
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
        mPresenter.getFriends();
    }

    @Override
    public Context getCurrentContext() {
        return getActivity();
    }

    @Override
    public void showFriends(List<Friend> friendsList) {
        mAdapter.setData(friendsList);
    }

    private void createPresenter() {
        DataSource dataSource = mApplication.getDataSource();
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        EventBus eventBus = mApplication.getEventBus();
        mPresenter = new SwipePresenter(dataSource, loaderManager, this, eventBus);
    }
}