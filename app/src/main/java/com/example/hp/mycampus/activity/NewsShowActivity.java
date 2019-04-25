package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mycampus.R;

public class NewsShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        Intent intent = getIntent();
        //获取参数:chosenPosition(点击的cardview的序号)
        int chosenPosition= (int)intent.getExtras().get("chosenPosition");
        //工具条
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //找到cardview中的控件
        TextView textView1 = (TextView) findViewById(R.id.title);
        TextView textView2 = (TextView) findViewById(R.id.text);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        //根据参数判断显示的控件资源
        switch (chosenPosition) {
            case 0:
                textView1.setText((String) this.getResources().getText(R.string.title0));
                textView2.setText((String) this.getResources().getText(R.string.text0));
                break;
            case 1:
                textView1.setText((String) this.getResources().getText(R.string.title1));
                textView2.setText((String) this.getResources().getText(R.string.text1));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.news1));
                textView1.setGravity(Gravity.CENTER);
                textView2.setGravity(Gravity.CENTER);
                break;
            case 2:
                textView1.setText((String) this.getResources().getText(R.string.title2));
                textView2.setText((String) this.getResources().getText(R.string.text2));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.news2));
                break;
            case 3:
                textView1.setText((String) this.getResources().getText(R.string.title3));
                textView2.setText((String) this.getResources().getText(R.string.text3));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.news3));
                textView1.setGravity(Gravity.CENTER);
                textView2.setGravity(Gravity.CENTER);
                break;
        }
    }

    //创建菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_toolbar, menu);
        return true;
    }

    //创建菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like_menu:
                Toast.makeText(NewsShowActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
