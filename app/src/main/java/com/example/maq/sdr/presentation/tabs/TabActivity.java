package com.example.maq.sdr.presentation.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
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

    private NonSwipeableViewPager pager;

    private MyFragmentPagerAdapter pagerAdapter;

    private TabContract.Presenter mPresenter;

    private MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        connectionErrorIcon = (ImageView) findViewById(R.id.connection_error_image);
        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(MainApplication.LOG_TAG, "page selected: " + position);
                if (position == 1) {
                    SwipeFragment fragment = ((SwipeFragment) pagerAdapter
                            .getRegisteredFragment(position));
                    if (fragment != null) {
                        fragment.refreshFriends();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<>();

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

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
