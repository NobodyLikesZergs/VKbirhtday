package com.example.maq.sdr.domain.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.maq.sdr.presentation.MainApplication;

public class MessageService extends IntentService {

    public MessageService() {
        super("MessageService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(MainApplication.LOG_TAG, "MessageService onStartCommand");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(MainApplication.LOG_TAG, "MessageService onHandleIntent");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(MainApplication.LOG_TAG, "MessageService onDestroy");
    }
}
