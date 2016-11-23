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
import android.widget.LinearLayout;

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
    private EditViewPager editViewPager;

    public static SwipeFragment newInstance(int page) {
        return new SwipeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MainApplication) getActivity().getApplication();
        createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.swipe_layout, null);
        editViewPager = (EditViewPager) view.findViewById(R.id.edit_view_pager);
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
                editViewPager.saveCurrentContent();
                for (Account account: friend.getAccountList()) {
                    mPresenter.saveMessage(friend, account,
                            new Message(account.getId(), editViewPager.getText().toString(),
                                    friend.getBirthDate()));
                }
            }
        });
        LinearLayout leftButton = (LinearLayout) view.findViewById(R.id.swipe_button_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeDeck.swipeTopCardLeft(350);
            }
        });
        LinearLayout rightButton = (LinearLayout) view.findViewById(R.id.swipe_button_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeDeck.swipeTopCardRight(350);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(MainApplication.LOG_TAG, "SwipeFragment: onResume");
    }

    public void refreshFriends() {
        editViewPager.refreshContent();
        mPresenter.getFriends();
    }

    @Override
    public void onStop() {
        Log.i(MainApplication.LOG_TAG, "SwipeFragment onStop");
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFriends();
        mPresenter.onStart();
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public void showFriends(List<Friend> friendsList) {
        mAdapter.setData(friendsList);
        mAdapter.notifyDataSetChanged();
        Log.i(MainApplication.LOG_TAG, "SwipeFragment: showFriends" +" " + ((Friend)mAdapter.getItem(0)).getName());
    }

    private void createPresenter() {
        DataSource dataSource = mApplication.getDataSource();
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        EventBus eventBus = mApplication.getEventBus();
        mPresenter = new SwipePresenter(dataSource, loaderManager, this, eventBus);
    }
}