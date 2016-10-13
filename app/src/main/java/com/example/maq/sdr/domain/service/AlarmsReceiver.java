package com.example.maq.sdr.domain.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.maq.sdr.presentation.MainApplication;

public class AlarmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120_000, pendingIntent);
        context.startService(new Intent(context, MessageService.class));
        Log.i(MainApplication.LOG_TAG, "AlarmsReceiver onReceive");
    }
}
