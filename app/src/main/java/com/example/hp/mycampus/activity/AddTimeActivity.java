package com.example.hp.mycampus.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Time;

public class AddTimeActivity extends Activity
{
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        DatePicker datePicker = (DatePicker)
                findViewById(R.id.datePicker);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new OnDateChangedListener()
        {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day)
            {
                AddTimeActivity.this.year = year;
                AddTimeActivity.this.month = month;
                AddTimeActivity.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day);
            }
        });

        Button butten = (Button) findViewById(R.id.button);
        butten.setOnClickListener(new View.OnClickListener() {
            //设置登入事件
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.name);
                EditText show = (EditText) findViewById(R.id.show);
                String name = text.getText().toString().trim();
                String date = show.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Date date1 = null;
                try {
                    date1 = format.parse(nowTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = null;
                try {
                    date2 = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int days= differentDaysByMillisecond(date1,date2);
                String countDown=String.valueOf(days);
                Toast.makeText(AddTimeActivity.this,"还剩"+days+"天", Toast.LENGTH_LONG).show();

                if (name.equals("") || date.equals("") ) {
                    Toast.makeText(AddTimeActivity.this, "基本信息未填写", Toast.LENGTH_SHORT).show();
                }
                else {
                    Time time = new Time(name, date,countDown);
                    Intent intent = new Intent(AddTimeActivity.this, TimeActivity.class);
                    //传递实例
                    intent.putExtra("time",time);
                    setResult(0, intent);
                    finish();
                }

            }
        });
    }

    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month
            , int day)
    {
        EditText show = (EditText) findViewById(R.id.show);
        show.setText( year + "-"
                + (month + 1) + "-" + day);
    }

    //获取系统当前日期
    public String nowTime(){
        Date date = new Date();
        String year = String.format("%tY", date);
        String month = String.format("%tB", date);
        String day = String.format("%te", date);

        switch (month) {
            case "January":
                month = "01";
                break;
            case "February":
                month = "02";
                break;
            case "March":
                month = "03";
                break;
            case "April":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "June":
                month = "06";
                break;
            case "July":
                month = "07";
                break;
            case "August":
                month = "08";
                break;
            case "September":
                month = "09";
                break;
            case "October":
                month = "10";
                break;
            case "November":
                month = "11";
                break;
            case "December":
                month = "12";
                break;
        }
        return year + "-" + month + "-" + day;
    }


    //计算相隔天数
    public static int differentDaysByMillisecond(Date date1,Date date2){
        /*
         * 通过getTime方法可以将一个日期类型转换为long类型的毫秒值
         */
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
}

