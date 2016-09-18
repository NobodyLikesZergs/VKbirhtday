package com.example.maq.sdr.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.maq.sdr.data.local.entries.AccountEntry;
import com.example.maq.sdr.data.local.entries.FriendEntry;
import com.example.maq.sdr.data.local.entries.MessageEntry;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.VkAccount;
import com.example.maq.sdr.presentation.MainApplication;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource{

    private static LocalDataSourceImpl  INSTANCE;

    private DbHelper mDbHelper;

    private SQLiteDatabase mDb;

    private LocalDataSourceImpl(Context context) {
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static LocalDataSourceImpl getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSourceImpl (context);
        return INSTANCE;
    }

    private List<Friend> getFriendByCondition(String selection, String[] selectionArgs) {
        List<Friend> friends = new ArrayList<>();
        String[] columns = {
                FriendEntry.ID_COLUMN,
                FriendEntry.NAME_COLUMN,
                FriendEntry.BIRTH_DATE_COLUMN,
                FriendEntry.IMG_URL_COLUMN,
        };
        Cursor c = mDb.query(FriendEntry.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(FriendEntry.ID_COLUMN));
                String name = c.getString(c.getColumnIndex(FriendEntry.NAME_COLUMN));
                String birthDate = c.getString(c.getColumnIndex(FriendEntry.BIRTH_DATE_COLUMN));
                String imgUrl = c.getString(c.getColumnIndex(FriendEntry.IMG_URL_COLUMN));
                Friend friend;
                if (birthDate == null) {
                    friend = new Friend(id, name, null, imgUrl, getAccountsByFriendId(id));
                } else {
                    friend = new Friend(id, name, DateTime.parse(birthDate),
                            imgUrl, getAccountsByFriendId(id));
                }
                friends.add(friend);
            }
        }
        if (c != null)
            c.close();
        Log.i(MainApplication.LOG_TAG, "local get friends");
        return friends;
    }

    @Override
    public List<Friend> getFriends() {
        return getFriendByCondition(null, null);
    }

    @Override
    public void saveFriend(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(FriendEntry.ID_COLUMN, friend.getId());
        values.put(FriendEntry.IMG_URL_COLUMN, friend.getImgUrl());
        values.put(FriendEntry.NAME_COLUMN, friend.getName());
        if (friend.getBirthDate() != null) {
            values.put(FriendEntry.BIRTH_DATE_COLUMN, friend.getBirthDate().toString());
        }
        mDb.insert(FriendEntry.TABLE_NAME, null, values);
        saveAccounts(friend);
    }

    @Override
    public void saveFriends(List<Friend> friends) {
        deleteAllFriends();
        Log.i(MainApplication.LOG_TAG, "local data: saveFriends");
        for(Friend friend: friends) {
            saveFriend(friend);
        }
    }

    private void deleteAllFriends() {
        deleteAllAccounts();
        Log.i(MainApplication.LOG_TAG, "local data: deleteFriends");
        mDb.execSQL("DELETE FROM " + FriendEntry.TABLE_NAME + " WHERE 1=1");
    }

    private List<Account> getAccountsByCondition(String selection, String[] selectionArgs) {
        List<Account> accounts = new LinkedList<>();
        String[] columns = {
                AccountEntry.ID_COLUMN,
                AccountEntry.NAME_COLUMN,
                AccountEntry.BIRTH_DATE_COLUMN,
                AccountEntry.IMG_URL_COLUMN,
        };
        Cursor c = mDb.query(AccountEntry.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(AccountEntry.ID_COLUMN));
                String name = c.getString(c.getColumnIndex(AccountEntry.NAME_COLUMN));
                String birthDate = c.getString(c.getColumnIndex(AccountEntry.BIRTH_DATE_COLUMN));
                String imgUrl = c.getString(c.getColumnIndex(AccountEntry.IMG_URL_COLUMN));
                Account account;
                if (birthDate == null) {
                    account = new VkAccount(id, imgUrl, null, getMessagesByAccountId(id), name);
                } else {
                    account = new VkAccount(id, imgUrl, DateTime.parse(birthDate),
                            getMessagesByAccountId(id), name);
                }
                accounts.add(account);
            }
        }
        if (c != null)
            c.close();
        return accounts;
    }

    private List<Account> getAccountsByFriendId(String friendId) {
        String args[] = {friendId};
        return getAccountsByCondition(AccountEntry.FRIEND_ID_COLUMN + "=?", args);
    }

    private void saveAccounts(Friend friend) {
        for (Account account: friend.getAccountList()) {
            saveAccount(account, friend);
        }
    }

    private void saveAccount(Account account, Friend friend) {
        ContentValues values = new ContentValues();
        values.put(AccountEntry.ID_COLUMN, account.getId());
        values.put(AccountEntry.NAME_COLUMN, account.getName());
        values.put(AccountEntry.IMG_URL_COLUMN, account.getImgUrl());
        values.put(AccountEntry.FRIEND_ID_COLUMN, friend.getId());
        if (account.getBirthDate() != null) {
            values.put(AccountEntry.BIRTH_DATE_COLUMN, account.getBirthDate().toString());
        }
        mDb.insert(AccountEntry.TABLE_NAME, null, values);
        saveMessages(friend, account);
    }

    private void deleteAllAccounts() {
        Log.i(MainApplication.LOG_TAG, "local data: deleteAccounts");
        mDb.execSQL("DELETE FROM " + AccountEntry.TABLE_NAME + " WHERE 1=1");
    }

    private List<Message> getMessagesByCondition(String selection, String[] selectionArgs) {
        List<Message> messages = new LinkedList<>();
        String[] columns = {
                MessageEntry.ID_COLUMN,
                MessageEntry.TEXT_COLUMN,
                MessageEntry.DATE_COLUMN,
                MessageEntry.FRIEND_ID_COLUMN,
                MessageEntry.ACCOUNT_ID_COLUMN,
        };
        Cursor c = mDb.query(MessageEntry.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String text = c.getString(c.getColumnIndex(MessageEntry.TEXT_COLUMN));
                String id = c.getString(c.getColumnIndex(MessageEntry.ID_COLUMN));
                String date = c.getString(c.getColumnIndex(MessageEntry.DATE_COLUMN));
                Message message = new Message(id, text, DateTime.parse(date));
                messages.add(message);
            }
        }
        if (c != null)
            c.close();
        return messages;
    }

    private List<Message> getMessagesByAccountId(String accountId) {
        String args[] = {accountId};
        return getMessagesByCondition(MessageEntry.ACCOUNT_ID_COLUMN + "=?", args);
    }

    private void saveMessages(Friend friend, Account account) {
        for (Message message: account.getMessageList()) {
            saveMessage(friend, account, message);
        }
    }

    @Override
    public void saveMessage(Friend friend, Account account, Message message) {
        deleteMessage(message.getId());
        ContentValues values = new ContentValues();
        values.put(MessageEntry.ID_COLUMN, message.getId());
        values.put(MessageEntry.DATE_COLUMN, message.getDate().toString());
        values.put(MessageEntry.TEXT_COLUMN, message.getText());
        values.put(MessageEntry.FRIEND_ID_COLUMN, friend.getId());
        values.put(MessageEntry.ACCOUNT_ID_COLUMN, account.getId());
        mDb.insert(MessageEntry.TABLE_NAME, null, values);
    }

    private void deleteMessage(String messageId) {
        mDb.execSQL("DELETE FROM " + MessageEntry.TABLE_NAME +
                " WHERE " + MessageEntry.ID_COLUMN + "="+ messageId);
    }
}