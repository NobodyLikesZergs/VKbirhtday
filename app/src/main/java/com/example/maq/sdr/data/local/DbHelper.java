package com.example.maq.sdr.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.maq.sdr.data.local.entries.AccountEntry;
import com.example.maq.sdr.data.local.entries.FriendEntry;
import com.example.maq.sdr.data.local.entries.MessageEntry;

public class DbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Friends.db";

    private final String TEXT_TYPE = " TEXT";

    private final String INTEGER_TYPE = " INTEGER";

    private final String SQL_CREATE_FRIEND_TABLE = "CREATE TABLE " +
            FriendEntry.TABLE_NAME + " (" +
            FriendEntry.ID_COLUMN + TEXT_TYPE + " PRIMARY KEY," +
            FriendEntry.NAME_COLUMN + TEXT_TYPE + "," +
            FriendEntry.BIRTH_DATE_COLUMN + TEXT_TYPE + "," +
            FriendEntry.IMG_URL_COLUMN + TEXT_TYPE +
            " )";

    private final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE " +
            AccountEntry.TABLE_NAME + " (" +
            AccountEntry.ID_COLUMN + TEXT_TYPE + " PRIMARY KEY," +
            AccountEntry.FRIEND_ID_COLUMN + TEXT_TYPE + "," +
            AccountEntry.NAME_COLUMN + TEXT_TYPE + "," +
            AccountEntry.BIRTH_DATE_COLUMN + TEXT_TYPE + "," +
            AccountEntry.IMG_URL_COLUMN + TEXT_TYPE +
            " )";

    private final String SQL_CREATE_MESSAGE_TABLE = "CREATE TABLE " +
            MessageEntry.TABLE_NAME + " (" +
            MessageEntry.ID_COLUMN + TEXT_TYPE + " PRIMARY KEY," +
            MessageEntry.FRIEND_ID_COLUMN + TEXT_TYPE + "," +
            MessageEntry.ACCOUNT_ID_COLUMN + TEXT_TYPE + "," +
            MessageEntry.TEXT_COLUMN + TEXT_TYPE + "," +
            MessageEntry.DATE_COLUMN + TEXT_TYPE +
            " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FRIEND_TABLE);
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
