package com.example.maq.sdr.domain.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.maq.sdr.data.DataSource;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.VkAccount;
import com.example.maq.sdr.presentation.MainApplication;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public class SendMessagesAsyncTask extends AsyncTask<Void, Void, Void> {

    private DataSource mDataSource;

    public SendMessagesAsyncTask(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<Message> messages = mDataSource.getTodayMessages();

        Account mock =       new VkAccount("208798080", true, "a", "12.12", "name name");
        messages = new LinkedList<>();
        messages.add(new Message("test",
                mock,
                new DateTime(),
                new Friend(mock)));

        if (messages == null) {
            return null;
        }
        for (Message message: messages) {
            mDataSource.sendMessage(message);
        }
        Log.i(MainApplication.LOG_TAG, "sendMessagesAsyncTask");
        return null;
    }
}
