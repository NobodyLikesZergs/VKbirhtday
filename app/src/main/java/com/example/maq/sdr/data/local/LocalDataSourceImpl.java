package com.example.maq.sdr.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.maq.sdr.data.local.entries.AccountEntry;
import com.example.maq.sdr.data.local.entries.FriendEntry;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.VkAccount;
import com.example.maq.sdr.presentation.MainApplication;

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

    public static LocalDataSourceImpl  getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSourceImpl (context);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        String[] columns = {
                FriendEntry.ID_COLUMN,
                FriendEntry.NAME_COLUMN,
                FriendEntry.BIRTH_DATE_COLUMN,
                FriendEntry.IS_ACTIVE,
                FriendEntry.IMG_URL_COLUMN,
        };
        Cursor c = mDb.query(FriendEntry.TABLE_NAME, columns, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(FriendEntry.ID_COLUMN));
                String name = c.getString(c.getColumnIndex(FriendEntry.NAME_COLUMN));
                String birthDate = c.getString(c.getColumnIndex(FriendEntry.BIRTH_DATE_COLUMN));
                Boolean isActive = new Boolean(
                        c.getString(c.getColumnIndex(FriendEntry.IS_ACTIVE)));
                String imgUrl = c.getString(c.getColumnIndex(FriendEntry.IMG_URL_COLUMN));
                Friend friend = new Friend(id, name, birthDate,
                        imgUrl, isActive, getAccountsByFriendId(id));
                friends.add(friend);
            }
        }
        if (c != null)
            c.close();
        return friends;
    }

    @Override
    public void saveFriend(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(FriendEntry.ID_COLUMN, friend.getId());
        values.put(FriendEntry.BIRTH_DATE_COLUMN, friend.getBirthDate());
        values.put(FriendEntry.IMG_URL_COLUMN, friend.getImgUrl());
        values.put(FriendEntry.NAME_COLUMN, friend.getName());
        values.put(FriendEntry.IS_ACTIVE, String.valueOf(friend.isActive()));
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

    private List<Account> getAccountsByFriendId(String friendId) {
        List<Account> accounts = new LinkedList<>();
        String[] columns = {
                AccountEntry.ID_COLUMN,
                AccountEntry.NAME_COLUMN,
                AccountEntry.BIRTH_DATE_COLUMN,
                AccountEntry.IS_ACTIVE,
                AccountEntry.IMG_URL_COLUMN,
        };
        Cursor c = mDb.query(AccountEntry.TABLE_NAME, columns, AccountEntry.FRIEND_ID_COLUMN +
                " = " + friendId, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(AccountEntry.ID_COLUMN));
                String name = c.getString(c.getColumnIndex(AccountEntry.NAME_COLUMN));
                String birthDate = c.getString(c.getColumnIndex(FriendEntry.BIRTH_DATE_COLUMN));
                Boolean isActive = new Boolean(
                        c.getString(c.getColumnIndex(FriendEntry.IS_ACTIVE)));
                String imgUrl = c.getString(c.getColumnIndex(FriendEntry.IMG_URL_COLUMN));
                Account account = new VkAccount(id, isActive, imgUrl, birthDate, name);
                accounts.add(account);
            }
        }
        if (c != null)
            c.close();
        return accounts;
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
        values.put(AccountEntry.BIRTH_DATE_COLUMN, account.getBirthDate());
        values.put(AccountEntry.IMG_URL_COLUMN, account.getImgUrl());
        values.put(AccountEntry.IS_ACTIVE, String.valueOf(account.isActive()));
        values.put(AccountEntry.FRIEND_ID_COLUMN, friend.getId());
        mDb.insert(AccountEntry.TABLE_NAME, null, values);

    }

    private void deleteAllAccounts() {
        Log.i(MainApplication.LOG_TAG, "local data: deleteAccounts");
        mDb.execSQL("DELETE FROM " + AccountEntry.TABLE_NAME + " WHERE 1=1");
    }
}
