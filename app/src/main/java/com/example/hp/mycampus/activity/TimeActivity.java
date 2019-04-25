package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.adapter.TimeRecycleAdapter;
import com.example.hp.mycampus.adapter.myItemTouchHelperCallBack;
import com.example.hp.mycampus.model.Lesson;
import com.example.hp.mycampus.model.Time;
import com.example.hp.mycampus.util.TimeDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TimeActivity extends AppCompatActivity {

    //声明一个对象，一个适配器 和 一个数据结构
    RecyclerView recyclerView;
    TimeRecycleAdapter adapter;
    List<Time> list;

    //SQLite Helper类
    private TimeDatabaseHelper timeDatabaseHelper = new TimeDatabaseHelper
            (this, "timedatabase.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        //工具条
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
        //设置监听器
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TimeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //获取对象
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设定布局管理
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        //初始化数据
        list = new ArrayList<>();
        loadData();
        //实例化一个adapter,并建立关联
        adapter = new TimeRecycleAdapter(list, this,timeDatabaseHelper);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    //从数据库加载数据
    private void loadData() {
        SQLiteDatabase sqLiteDatabase =  timeDatabaseHelper.getWritableDatabase();
        //游标，表示每一行的集合
        Cursor cursor = sqLiteDatabase.rawQuery("select * from time", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Time(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("countdown"))
                       ));
            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    //获取创建课表中的time实例
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0 && data != null) {
            Time time_single = (Time) data.getSerializableExtra("time");
            SQLiteDatabase sqLiteDatabase =  timeDatabaseHelper.getWritableDatabase();
            //执行SQL语句
            String name=time_single.getName();
            String date=time_single.getDate();
            String countDown=time_single.getCountDown();
            sqLiteDatabase.execSQL("insert into time(name,date,countDown) " + "values(?, ?,?)",
                    new String[] {name, date,countDown});
            loadData();
            refresh();
        }
    }

    public void refresh() {
        Intent intent = new Intent(TimeActivity.this, TimeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.time_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu:
                Intent intent = new Intent(TimeActivity.this, AddTimeActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
        return true;
    }
}