package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainApp = (MainApplication) getApplication();

        setContentView(R.layout.friends);
        mRecyclerView = (RecyclerView) findViewById(R.id.friends_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new FriendsAdapter(new ArrayList<Friend>(), this);
        mRecyclerView.setAdapter(mFriendsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        createPresenter();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFriends();
            }
        });
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
    public void showAuthorizationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы не авторизованы!")
                .setMessage("Пожалуйста, авторизуйтесь!")
                .setCancelable(false)
                .setNegativeButton("Войти через ВК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(FriendsActivity.this,
                                        AuthorizationActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showConnectionErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Соединение отсутствует!")
                .setMessage("Пожалуйста, проверьте соединение и повторите попытку.")
                .setCancelable(false)
                .setNegativeButton("Повторить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.getFriends();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }
}
