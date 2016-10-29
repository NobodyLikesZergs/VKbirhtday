package com.example.maq.sdr.presentation.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;

import com.example.maq.sdr.R;
import com.example.maq.sdr.presentation.MainApplication;
import com.example.maq.sdr.presentation.authorization.AuthorizationActivity;
import com.example.maq.sdr.presentation.friends.FriendsFragment;
import com.example.maq.sdr.presentation.swipe.SwipeFragment;
import com.google.common.eventbus.EventBus;

public class TabActivity extends FragmentActivity implements TabContract.View {

    static final int PAGE_COUNT = 2;

    private int connectionErrorDialogsCount;
    private int authErrorDialogsCount;

    NonSwipeableViewPager pager;

    PagerAdapter pagerAdapter;

    TabContract.Presenter mPresenter;

    MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        mainApplication = (MainApplication) getApplication();
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

    }

    @Override
    public void hideConnectionErrorIcon() {

    }

    @Override
    public void showAuthorizationDialog() {
        if (authErrorDialogsCount > 0)
            return;
        authErrorDialogsCount++;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.auth_error_title))
                .setMessage(getString(R.string.auth_error_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.auth_error_positive),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                authErrorDialogsCount--;
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
        if (connectionErrorDialogsCount > 0)
            return;
        connectionErrorDialogsCount++;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.no_connection_title))
                .setMessage(getString(R.string.no_connection_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.no_connection_positive),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                connectionErrorDialogsCount--;
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
            if (position==0)
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
