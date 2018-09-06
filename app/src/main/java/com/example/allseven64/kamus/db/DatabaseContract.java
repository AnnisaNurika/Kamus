package com.example.allseven64.kamus.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_KAMUS_INDO = "indonesia";
    static String TABLE_KAMUS_ING = "inggris";

    static final class KamusColumns implements BaseColumns{
        //Kamus kata
        static String KATA_ASAL = "kata";
        //kamus arti
        static String ARTI = "arti";
    }
}
