package com.example.maq.sdr.domain.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.presentation.MainApplication;

public class MessageManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DataSource dataSource =
                ((MainApplication)context.getApplicationContext()).getDataSource();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 45000, pendingIntent);
        sendMessages(dataSource);
    }

    public void sendMessages(DataSource dataSource) {
        Log.i(MainApplication.LOG_TAG, "MessageManager: sendMessages");
        new SendMessagesAsyncTask(dataSource).execute();
    }
}
