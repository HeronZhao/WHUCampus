package com.example.hp.mycampus.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import com.example.hp.mycampus.adapter.ScoreAdapter;
import com.example.hp.mycampus.model.Score;
import com.example.hp.mycampus.util.ScoreDatabaseHelper;
import com.example.hp.mycampus.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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
        final ArrayList<Score> scoresList = new ArrayList<>(); //成绩列表
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
                        cursor.getString(cursor.getColumnIndex("score")),
                        cursor.getString(cursor.getColumnIndex("lessontype"))
                        ));
            } while(cursor.moveToNext());
        }
        cursor.close();

        ScoreAdapter adapter = new ScoreAdapter(this,R.layout.score_layout,scoresList);
        listView1.setAdapter(adapter);

        //删除数据
        deleteTable(sqLiteDatabase);

        //跳转按钮
        Button btnChart = findViewById(R.id.button);
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScoreShowActivity.this, ChartShowActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("scoresList",scoresList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
                ("insert into scores(year, semester, name, credit, score, lessontype) " + "values(?, ?, ?, ?, ?, ?)",
                        new String[] {score.getYear(),
                                score.getSemester(),
                                score.getName(),
                                score.getCredit(),
                                score.getScore()+"",
                                score.getType()
                        }
                );
    }

}