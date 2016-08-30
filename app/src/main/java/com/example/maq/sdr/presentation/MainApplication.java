package com.example.maq.sdr.presentation;

import android.app.Application;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.data.DataSourceImpl;
import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.local.LocalDataSourceImpl;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSourceImpl;
import com.google.common.eventbus.EventBus;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class MainApplication extends Application {

    public static final String LOG_TAG = "log tag";

    private static EventBus eventBus;

    private DataSource mDataSource;

    private String vkToken;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Log.i("Vk token", "is invalid");
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance(this);
        RemoteDataSource remoteDataSource = RemoteDataSourceImpl.getInstance(vkToken);
        mDataSource = DataSourceImpl.getInstance(localDataSource, remoteDataSource);

    }

    public String getVkToken() {
        return vkToken;
    }

    public void setVkToken(String vkToken) {
        this.vkToken = vkToken;
        mDataSource.setVkToken(vkToken);
    }

    public static EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }

    public DataSource getDataSource() {
        return mDataSource;
    }
}