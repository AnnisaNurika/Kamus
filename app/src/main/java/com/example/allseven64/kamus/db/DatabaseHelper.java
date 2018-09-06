package com.example.allseven64.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.allseven64.kamus.db.DatabaseContract.KamusColumns.ARTI;
import static com.example.allseven64.kamus.db.DatabaseContract.KamusColumns.KATA_ASAL;
import static com.example.allseven64.kamus.db.DatabaseContract.TABLE_KAMUS_INDO;
import static com.example.allseven64.kamus.db.DatabaseContract.TABLE_KAMUS_ING;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_KAMUS_INDO = "create table "+TABLE_KAMUS_INDO+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA_ASAL+" text not null, " +
            ARTI+" text not null);";

    public static String CREATE_TABLE_KAMUS_ING = "create table "+TABLE_KAMUS_ING+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA_ASAL+" text not null, " +
            ARTI+" text not null);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KAMUS_INDO);
        db.execSQL(CREATE_TABLE_KAMUS_ING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KAMUS_INDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KAMUS_ING);
        onCreate(db);
    }
}
