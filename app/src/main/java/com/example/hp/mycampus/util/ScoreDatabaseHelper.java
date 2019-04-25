package com.example.hp.mycampus.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabaseHelper extends SQLiteOpenHelper {

    public ScoreDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //第一次创建数据库的时候会用到该方法，所以用的就是create table 这样的语法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores (id integer primary key autoincrement, " +
                "year integer, " +
                "semester varchar(32), " +
                "name varchar(32), " +
                "credit varchar(32)," +
                "score integer)");
    }

    //数据库版本升级的时候会用到该方法，暂时用不着的话先不用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}