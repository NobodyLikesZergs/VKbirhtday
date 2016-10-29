package com.example.maq.sdr.presentation.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.maq.sdr.R;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.authorization.AuthorizationActivity;
import com.example.maq.sdr.presentation.friends.FriendsFragment;
import com.example.maq.sdr.presentation.swipe.SwipeFragment;
import com.google.common.eventbus.EventBus;

public class TabActivity extends AppCompatActivity implements TabContract.View {

    static final int PAGE_COUNT = 2;

    private boolean connectionErrorDialogVisible;
    private boolean authErrorDialogVisible;

    private ImageView connectionErrorIcon;

    NonSwipeableViewPager pager;

    PagerAdapter pagerAdapter;

    TabContract.Presenter mPresenter;

    MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        connectionErrorIcon = (ImageView) findViewById(R.id.connection_error_image);
        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        mainApplication = (MainApplication) getApplication();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createPresenter();
    }

    private void createPresenter() {
        EventBus eventBus = mainApplication.getEventBus();
        mPresenter = new TabPresenter(this, eventBus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onActivityStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onActivityStop();
        super.onStop();
    }

    @Override
    public void showConnectionErrorIcon() {
        connectionErrorIcon.post(new Runnable() {
            @Override
            public void run() {
                connectionErrorIcon.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideConnectionErrorIcon() {
        connectionErrorIcon.post(new Runnable() {
            @Override
            public void run() {
                connectionErrorIcon.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showAuthorizationDialog() {
        if (authErrorDialogVisible)
            return;
        authErrorDialogVisible = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.auth_error_title))
                .setMessage(getString(R.string.auth_error_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.auth_error_positive),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                authErrorDialogVisible = false;
                                Intent intent = new Intent(TabActivity.this,
                                        AuthorizationActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showConnectionErrorDialog() {
        if (connectionErrorDialogVisible)
            return;
        connectionErrorDialogVisible=true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.no_connection_title))
                .setMessage(getString(R.string.no_connection_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.no_connection_positive),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                connectionErrorDialogVisible=false;
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return FriendsFragment.newInstance(position);
            else
                return SwipeFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return getString(R.string.list_tab);
            else
                return getString(R.string.swipe_tab);
        }
    }
}
