package com.example.maq.sdr.domain.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.presentation.MainApplication;

import org.joda.time.DateTime;

import java.util.List;

public class MessageService extends IntentService {

    public MessageService() {
        super("MessageService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataSource dataSource = ((MainApplication)getApplication()).getDataSource();
        List<Friend> friendList = dataSource.getFriends();

        for (Friend friend: friendList) {
            for (Account account: friend.getAccountList()) {
                for (Message message: account.getMessageList()) {
                    if (verifyMessage(friend, account, message)) {
                        dataSource.sendMessage(account, message);
                    }
                }
            }
        }
        Log.i(MainApplication.LOG_TAG, "MessageService onHandleIntent");
    }

    private boolean verifyMessage(Friend friend, Account account, Message message) {
//        TODO: check lastInteractionDate;
        DateTime currentDate = new DateTime();
        DateTime messageDate = message.getDate();
        DateTime friendDate = friend.getBirthDate();
        if ((currentDate.getMonthOfYear() != messageDate.getMonthOfYear()) ||
                currentDate.getDayOfMonth() != messageDate.getDayOfMonth())
            return false;
        if ((friendDate.getMonthOfYear() != messageDate.getMonthOfYear()) ||
                friendDate.getDayOfMonth() != messageDate.getDayOfMonth())
            return false;
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(MainApplication.LOG_TAG, "MessageService onDestroy");
    }
}
