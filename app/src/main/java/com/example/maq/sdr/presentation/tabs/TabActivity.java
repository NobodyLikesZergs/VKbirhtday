package com.example.maq.sdr.presentation.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.maq.sdr.R;
import com.example.maq.sdr.presentation.friends.FriendsFragment;
import com.example.maq.sdr.presentation.swipe.SwipeFragment;

public class TabActivity extends FragmentActivity {

    static final int PAGE_COUNT = 2;

    NonSwipeableViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

//        pager.setOnPageChangeListener(new NonSwipeableViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.d(TAG, "onPageSelected, position = " + position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
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
