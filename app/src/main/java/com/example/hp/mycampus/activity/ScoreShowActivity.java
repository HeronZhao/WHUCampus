package com.example.hp.mycampus.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import com.example.hp.mycampus.adapter.ScoreAdapter;
import com.example.hp.mycampus.model.Score;
import com.example.hp.mycampus.util.ScoreDatabaseHelper;
import com.example.hp.mycampus.R;

import androidx.appcompat.app.AppCompatActivity;


public class ScoreShowActivity extends AppCompatActivity {

    //SQLite Helper类
    private ScoreDatabaseHelper scoreDatabaseHelper = new ScoreDatabaseHelper
            (this, "database02.db", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_show);
        ListView listView1 = (ListView) findViewById(R.id.listView1);

        getResult();

        //从数据库读取数据
        ArrayList<Score> scoresList = new ArrayList<>(); //成绩列表
        SQLiteDatabase sqLiteDatabase =  scoreDatabaseHelper.getWritableDatabase();//从helper中获得数据库
        //游标，表示每一行的集合
        Cursor cursor = sqLiteDatabase.rawQuery("select * from scores", null);
        if (cursor.moveToFirst()) {
            do {
                scoresList.add(new Score(
                        cursor.getString(cursor.getColumnIndex("year")),
                        cursor.getString(cursor.getColumnIndex("semester")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("credit")),
                        cursor.getString(cursor.getColumnIndex("score"))));
            } while(cursor.moveToNext());
        }
        cursor.close();

        ScoreAdapter adapter = new ScoreAdapter(this,R.layout.score_layout,scoresList);
        listView1.setAdapter(adapter);

        //删除数据
        deleteTable(sqLiteDatabase);
    }

    //清空scores表
    public void deleteTable(SQLiteDatabase db){
        db.execSQL("delete from scores");
    }

    private void getResult(){
        Intent intent = getIntent();
        ArrayList<Score> scores =  (ArrayList<Score>) intent.getSerializableExtra("scores");
        for (Score score:scores){
            saveData(score);
        }
    }

    //保存数据到数据库  1.打开数据库2.执行SQL语句
    private void saveData(Score score) {
        //当数据库不可写入时，getReadableDatabase()以只读的方式打开数据库，而getWritableDatabase()会出现异常
        SQLiteDatabase sqLiteDatabase =  scoreDatabaseHelper.getWritableDatabase();
        //执行SQL语句
        sqLiteDatabase.execSQL
                ("insert into scores(year, semester, name, credit, score) " + "values(?, ?, ?, ?, ?)",
                        new String[] {score.getYear(),
                                score.getSemester(),
                                score.getName(),
                                score.getCredit(),
                                score.getScore()+""}
                );
    }

}