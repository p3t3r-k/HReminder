package com.example.hreminder.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelp extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "HReminder.db";
    public static final String TABLE_HReminder = "HReminder_table";
    public static final String COL_ID = "ID";
    public static final String COL_USERNAME = "name";
    public static final String COL_PW = "password";

    private final Context context;

    public DatabaseHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_HReminder + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT(15) NOT NULL, password INTEGER(4) NOT NULL, fingerprint INTEGER(1) NOT NULL, gender INTEGER(1) NOT NULL, " +
                "bday )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HReminder);
        onCreate(db);
    }
/*
    public boolean insertData(String name, String passwort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, passwort);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            Toast.makeText(context, "info saved", Toast.LENGTH_LONG).show();
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String name, String passwort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, passwort);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
            }
    */


}
