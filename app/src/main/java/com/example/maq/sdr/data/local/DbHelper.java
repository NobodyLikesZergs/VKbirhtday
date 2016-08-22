package com.example.maq.sdr.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Friends.db";

    private final String TEXT_TYPE = " TEXT";

    private final String INTEGER_TYPE = " INTEGER";

    private final String SQL_CREATE = "CREATE TABLE " + FriendEntry.TABLE_NAME + " (" +
            FriendEntry.ID_COLUMN + TEXT_TYPE + " PRIMARY KEY," +
            FriendEntry.NAME_COLUMN + TEXT_TYPE + "," +
            FriendEntry.BIRTH_DATE_COLUMN + TEXT_TYPE + "," +
            FriendEntry.IMG_URL_COLUMN + TEXT_TYPE + "," +
            FriendEntry.LAST_INTERACTION_COLUMN + INTEGER_TYPE +
            " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
