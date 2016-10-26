package com.example.maq.sdr.presentation.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.maq.sdr.R;
import com.example.maq.sdr.presentation.friends.FriendsActivity;
import com.example.maq.sdr.presentation.swipe.SwipeActivity;

public class TabActivity extends android.app.TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator(getString(R.string.list_tab));
        tabSpec.setContent(new Intent(this, FriendsActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator(getString(R.string.swipe_tab));
        tabSpec.setContent(new Intent(this, SwipeActivity.class));
        tabHost.addTab(tabSpec);
    }
}
