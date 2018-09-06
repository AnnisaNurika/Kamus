package com.example.allseven64.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.allseven64.kamus.KamusModel;
import com.example.allseven64.kamus.R;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.allseven64.kamus.db.DatabaseContract.KamusColumns.ARTI;
import static com.example.allseven64.kamus.db.DatabaseContract.KamusColumns.KATA_ASAL;
import static com.example.allseven64.kamus.db.DatabaseContract.TABLE_KAMUS_INDO;
import static com.example.allseven64.kamus.db.DatabaseContract.TABLE_KAMUS_ING;

public class KamusHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByName(String query, String lang_select){
        Cursor cursor;
        if (lang_select == "Eng"){
            cursor = database.query(TABLE_KAMUS_ING, null, KATA_ASAL+" LIKE?", new String[]{query.trim()+"%"}, null, null, _ID + " ASC", null);

        }else{
            cursor = database.query(TABLE_KAMUS_INDO, null, KATA_ASAL+" LIKE?", new String[]{query.trim()+"%"}, null, null, _ID + " ASC", null);
        }
        cursor.moveToFirst();

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata_asal(cursor.getString(cursor.getColumnIndexOrThrow(KATA_ASAL)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(kamusModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(String lang_select){
        Cursor cursor;

        if (lang_select == "Eng"){
            cursor = database.query(TABLE_KAMUS_ING, null, null,null,null, null, _ID+ " ASC", null);
         }else{
            cursor = database.query(TABLE_KAMUS_INDO, null, null,null,null, null, _ID+ " ASC", null);
        }
        cursor.moveToFirst();

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata_asal(cursor.getString(cursor.getColumnIndexOrThrow(KATA_ASAL)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(kamusModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(KamusModel kamusModel, String lang_select){

        String table = null;
        if (lang_select == "Eng"){
            table = TABLE_KAMUS_ING;
        }else{
            table = TABLE_KAMUS_INDO;
        }

        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA_ASAL, kamusModel.getKata_asal());
        initialValues.put(ARTI, kamusModel.getArti());
        return database.insert(table, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccessful(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusModel, String lang_select){
        String table = null;
        if (lang_select == "Eng"){
            table = TABLE_KAMUS_ING;
        }else{
            table = TABLE_KAMUS_INDO;
        }
        String sql = "INSERT INTO "+table+" ("+KATA_ASAL+", "+ARTI+") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata_asal());
        stmt.bindString(2, kamusModel.getArti());
        stmt.execute();
        stmt.clearBindings();
    }


    public  int update (KamusModel kamusModel, String lang_select){
        String table = null;
        if (lang_select == "Eng"){
            table = TABLE_KAMUS_ING;
        }else{
            table = TABLE_KAMUS_INDO;
        }
        ContentValues args = new ContentValues();
        args.put(KATA_ASAL, kamusModel.getKata_asal());
        args.put(ARTI, kamusModel.getArti());
        return database.update(table, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    public int delete (int id, String lang_select){
        String table = null;
        if (lang_select == "Eng"){
            table = TABLE_KAMUS_ING;
        }else{
            table = TABLE_KAMUS_INDO;
        }

        return database.delete(table, _ID + " = '"+id+"'", null);
    }
}
