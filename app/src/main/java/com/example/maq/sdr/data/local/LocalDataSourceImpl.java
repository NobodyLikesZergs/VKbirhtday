package com.example.maq.sdr.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.Template;

import java.util.ArrayList;
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
                FriendEntry.IMG_URL_COLUMN,
                FriendEntry.LAST_INTERACTION_COLUMN
        };
        Cursor c = mDb.query(FriendEntry.TABLE_NAME, columns, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(FriendEntry.ID_COLUMN));
                String name = c.getString(c.getColumnIndex(FriendEntry.NAME_COLUMN));
                String birthDate = String.valueOf(
                        c.getInt(c.getColumnIndex(FriendEntry.BIRTH_DATE_COLUMN)));
                String imgUrl = c.getString(c.getColumnIndex(FriendEntry.IMG_URL_COLUMN));
//                Date lastInteraction =
//                        new Date(c.getInt(c.getColumnIndex(FriendEntry.LAST_INTERACTION_COLUMN)));
                Friend friend = new Friend(id, name, birthDate, imgUrl);
                friends.add(friend);
            }
        }
        if (c != null)
            c.close();
        return friends;
    }

    @Override
    public List<Friend> getUnresolvedFriends() {
        return null;
    }

    @Override
    public Friend getFriend(int id) {
        return null;
    }

    @Override
    public void saveFriend(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(FriendEntry.ID_COLUMN, friend.getId());
        values.put(FriendEntry.BIRTH_DATE_COLUMN, friend.getBirthDate());
        values.put(FriendEntry.IMG_URL_COLUMN, friend.getImgUrl());
//        values.put(FriendEntry.LAST_INTERACTION_COLUMN, friend.getLastInteraction().getTime());
        values.put(FriendEntry.NAME_COLUMN, friend.getName());
        mDb.insert(FriendEntry.TABLE_NAME, null, values);
    }

    private void deleteAllFriends() {
        Log.i("LocalDataSource", "deleteAllFriends");
        mDb.execSQL("DELETE FROM " + FriendEntry.TABLE_NAME + " WHERE 1=1");
    }

    @Override
    public void saveFriends(List<Friend> friends) {
        deleteAllFriends();
        Log.i("LocalDataSource", "saveFriends");
        for(Friend friend: friends) {
            saveFriend(friend);
        }
    }

    @Override
    public List<Account> getAccountsByFriend(Friend friend) {
        return null;
    }

    @Override
    public List<Message> getMessages() {
        return null;
    }

    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public List<Template> getTemplates() {
        return null;
    }

    @Override
    public void saveTemplate(Template template) {

    }

}
