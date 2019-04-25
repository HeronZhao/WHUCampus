package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Lesson;
import com.example.hp.mycampus.util.LessonDatabaseHelper;
import com.example.hp.mycampus.util.InfoUtil;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    private RelativeLayout day;
    private LessonDatabaseHelper databaseHelper = new LessonDatabaseHelper(this, "database.db", null, 1);
    private int course_height;
    private int currentcoursesNumber = 0;
    private int maxcoursesNumber = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("课程表");
        setSupportActionBar(toolbar);

        createLeftView();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                LinearLayout leftViewLayout = (LinearLayout) findViewById(R.id.left_view_layout);
                course_height=leftViewLayout.getHeight()/13;
                loadData();
            }
        }, 100);    //延时0.1s执行，解决获取不到高度的问题
    }

    //从数据库加载数据
    /**
    * @Description: 从数据库加载数据
    * @Param:
    * @return:
    * @Author: SPG
    * @Date: 2018/7/17 
    */
    private void loadData() {
        //数组保存所有的客户课程信息
        ArrayList<Lesson> lessonsList = new ArrayList<>();
        //获取数据库，并进行查询操作
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from lessons", null);
        if (cursor.moveToFirst()) {
            do {
                lessonsList.add(new Lesson(
                        cursor.getString(cursor.getColumnIndex("lesson_name")),
                        cursor.getString(cursor.getColumnIndex("teacher_name")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getString(cursor.getColumnIndex("day")),
                        cursor.getString(cursor.getColumnIndex("class_start")),
                        cursor.getString(cursor.getColumnIndex("class_end"))));
            } while(cursor.moveToNext());
        }
        cursor.close();
        //使用从数据库读取出来的课程信息来加载课程表视图
        for (Lesson lesson : lessonsList) {
            createcourseView(lesson);//课程视图
        }
    }

    //保存数据到数据库  1.打开数据库2.执行SQL语句
    /** 
    * @Description: 保存课程数据到数据库：1.打开数据库2.执行insert语句将对象属性存入
    * @Param:  lesson
    * @return:  
    * @Author: SPG
    * @Date: 2018/7/17  
    */ 
    private void saveData(Lesson lesson) {
        //当数据库不可写入时，getReadableDatabase()以只读的方式打开数据库，而getWritableDatabase()会出现异常
        SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
        //执行SQL语句
        sqLiteDatabase.execSQL
                ("insert into lessons(lesson_name, teacher_name, class_room, day, class_start, class_end) " + "values(?, ?, ?, ?, ?, ?)",
                        new String[] {lesson.getLessonName(),
                                lesson.getTeacherName(),
                                lesson.getClassRoom(),
                                lesson.getDay()+"",
                                lesson.getBeginTime()+"",
                                lesson.getEndTime()+""}
                );
    }

    /** 
    * @Description: 创建课程节数的视图（每一个小块课程）
    * @Param:  
    * @return:  
    * @Author: SPG
    * @Date: 2018/7/17  
    */
    private void createLeftView() {
        for (int i = 0;i<maxcoursesNumber;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.left_view,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,0,1);
            view.setLayoutParams(params);
            TextView textView = view.findViewById(R.id.class_number_text);
            textView.setText(String.valueOf(++currentcoursesNumber));
            LinearLayout leftViewLayout = (LinearLayout) findViewById(R.id.left_view_layout);
            leftViewLayout.addView(view);
        }
    }

    /**
    * @description: 创建课表视图
    * @param:  lesson
    * @return:
    * @author: SPG
    * @date: 2018/7/17  15:52
    */
    private void createcourseView(final Lesson lesson) {
        //获取课程是星期几
        int nowDay = Integer.valueOf(lesson.getDay());
        //获取课程开始的节数
        int class_start = Integer.valueOf(lesson.getBeginTime())-1;
        //获取课程结束的节数
        int class_end= Integer.valueOf(lesson.getEndTime())-1;
        if ((nowDay < 1 || nowDay > 7) || class_start > class_end)
            //如果输入错误，给出消息提示
            Toast.makeText(this, "星期几没写对,或课程结束时间比开始时间还早~~", Toast.LENGTH_LONG).show();
        else {
            switch (nowDay) {
                case 1: day = findViewById(R.id.monday); break;
                case 2: day = findViewById(R.id.tuesday); break;
                case 3: day = findViewById(R.id.wednesday); break;
                case 4: day = findViewById(R.id.thursday); break;
                case 5: day = findViewById(R.id.friday); break;
                case 6: day = findViewById(R.id.saturday); break;
                case 7: day = findViewById(R.id.sunday); break;
            }
            //每一个课程都是一个course_card
            final View v = LayoutInflater.from(this).inflate(R.layout.course_card, null); //加载单个课程布局
            v.setY(course_height * class_start-1); //设置开始高度,即第几节课开始,比如第一节课就从0开始
            //给课程布局设置参数，宽
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    //宽适应原布局单元的大小，布局高度为（占的课时*每个课时占的高度）
                    (ViewGroup.LayoutParams.MATCH_PARENT,(class_end-class_start+1)*course_height - 8,1); //设置布局高度,即跨多少节课
            v.setLayoutParams(params);//属性绑定
            TextView text = v.findViewById(R.id.text_view);
            text.setText(lesson.getLessonName() + "\n" + lesson.getTeacherName() + "\n" +"@"+ lesson.getClassRoom()); //显示课程名
            day.addView(v);
            //长按删除课程
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.setVisibility(View.GONE);//先隐藏
                    day.removeView(v);//再移除课程视图
                    SQLiteDatabase sqLiteDatabase =  databaseHelper.getWritableDatabase();
                    sqLiteDatabase.execSQL("delete from lessons where lesson_name = ?", new String[] {lesson.getLessonName()});
                    return true;
                }
            });
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(CourseActivity.this,"按久一点就可以删了",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
    * @description: 获取创建课表界面中的lesson,并添加进课表界面中去
    * @param: requestCode resultCode data
    * @return:
    * @author: SPG
    * @date: 2018/7/17  15:54
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == 0 && data != null){
            Lesson course_single = (Lesson) data.getSerializableExtra("lessons");
            createcourseView(course_single);
            saveData(course_single);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lesson_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_lessons:
                Intent intent = new Intent(CourseActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.lesson_import:
                //登陆后获取爬取的课程信息
                delete();
                this.recreate();
                ArrayList<Lesson> lessons =InfoUtil.getLessons();
                for(Lesson lesson : lessons){
                    //创建课程表视图
                    createcourseView(lesson);
                    //存储数据到数据库
                    saveData(lesson);}
                break;
            case R.id.delete_all:
                delete();
                Toast.makeText(this,"Delete !!!",Toast.LENGTH_SHORT).show();
                this.recreate();
                break;
        }
        return true;
    }
    
    /** 
    * @description: 从数据库中将当前所有的课程信息全部删除
    * @param:  
    * @return:
    * @author: SPG
    * @date: 2018/7/17 16:00
    */ 
    private void delete(){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from lessons");
    }

}
