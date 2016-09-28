package com.example.maq.sdr.presentation;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.data.DataSourceImpl;
import com.example.maq.sdr.data.local.LocalDataSource;
import com.example.maq.sdr.data.local.LocalDataSourceImpl;
import com.example.maq.sdr.data.remote.RemoteDataSource;
import com.example.maq.sdr.data.remote.RemoteDataSourceImpl;
import com.example.maq.sdr.domain.service.AlarmsReceiver;
import com.google.common.eventbus.EventBus;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class MainApplication extends Application {

    public static final String LOG_TAG = "sdrr";

    private EventBus mEventBus;

    private DataSource mDataSource;

    private String vkToken;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Log.i(LOG_TAG, "TokenTracker: new Token is null");
            }
            updateVkToken();
            Log.i(MainApplication.LOG_TAG, "onVkAccessTokenChanged");
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance(this);
        RemoteDataSource remoteDataSource = RemoteDataSourceImpl.getInstance(vkToken);
        mEventBus = getEventBus();
        mDataSource = DataSourceImpl.getInstance(localDataSource, remoteDataSource, mEventBus);
        updateVkToken();
        startService();
    }

    public void updateVkToken() {
        if (VKAccessToken.currentToken() != null) {
            vkToken = VKAccessToken.currentToken().accessToken;
            mDataSource.setVkToken(vkToken);
        }
    }

    public EventBus getEventBus() {
        if (mEventBus == null) {
            mEventBus = new EventBus();
        }
        return mEventBus;
    }

    public DataSource getDataSource() {
        return mDataSource;
    }

    public void startService() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmsReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
//        if (pendingIntent == null) {
//            pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, pendingIntent);
        Log.i(MainApplication.LOG_TAG, "mainApp: service started");
//        }
    }
}
