package com.example.lenovo.zhihu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2018/12/11.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
   private Context mcontext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }
    private static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "password text)";
    private static final String CREATE_ID="create table Id("
            +"id integer primary key autoincrement,"
            +"news_id text,"
            +"column_id text)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_ID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
              db.execSQL("drop table if exists User ");
        db.execSQL("drop table if exists Id ");
        onCreate(db);
    }
}
