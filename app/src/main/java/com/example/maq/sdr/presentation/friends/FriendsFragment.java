package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maq.sdr.R;
import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment implements FriendsContract.View{

    private FriendsContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private FriendsAdapter mFriendsAdapter;

    private MainApplication mainApp;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static FriendsFragment newInstance(int page) {
        FriendsFragment friendsFragment = new FriendsFragment();
        return friendsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.friends_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        createPresenter();
        mFriendsAdapter = new FriendsAdapter(new ArrayList<Friend>(), getActivity(), mPresenter);
        mRecyclerView.setAdapter(mFriendsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.divider));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFriends();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        Log.i(MainApplication.LOG_TAG, "FriendsFragment onStart");
        super.onStart();
        mPresenter.onStart();
        mPresenter.getFriends();
    }

    @Override
    public void onStop() {
        Log.i(MainApplication.LOG_TAG, "FriendsFragment onStop");
        mPresenter.onStop();
        super.onStop();
    }

    private void createPresenter() {
        DataSource dataSource = mainApp.getDataSource();
        EventBus eventBus = mainApp.getEventBus();
        mPresenter = new FriendsPresenter(getActivity().getSupportLoaderManager()
                , dataSource, this, eventBus);
    }

    @Override
    public void showFriends(List<Friend> friends) {
        mFriendsAdapter.replaceData(friends);
    }

    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public Context getCurrentContext() {
        return getActivity();
    }
}
