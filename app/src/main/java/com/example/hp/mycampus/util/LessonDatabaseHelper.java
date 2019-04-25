package com.example.hp.mycampus.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LessonDatabaseHelper extends SQLiteOpenHelper {

    public LessonDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //第一次创建数据库的时候会用到该方法，所以用的就是create table 这样的语法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lessons(" +
                "id integer primary key autoincrement," +
                "lesson_name text," +
                "teacher_name text," +
                "class_room text," +
                "day integer," +
                "class_start integer," +
                "class_end integer)");
    }
    //数据库版本升级的时候会用到该方法，暂时用不着的话先不用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
