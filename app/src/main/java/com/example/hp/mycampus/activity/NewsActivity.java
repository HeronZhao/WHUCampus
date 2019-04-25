package com.example.hp.mycampus.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.adapter.NewsRecycleAdapter;
import com.example.hp.mycampus.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends Activity {

    //声明一个对象、一个适配器和一个数据结构
    RecyclerView recyclerView;
    NewsRecycleAdapter adapter;
    List<News> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //获取对象
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设定布局管理
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        //初始化数据
        list = new ArrayList<>();
        initData();
        //实例化一个adapter,并建立关联
        adapter = new NewsRecycleAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        News news0=new News((String) this.getResources().getText(R.string.news_name_1),(String) this.getResources().getText(R.string.news_des_1),"school.jpg");
        News news1=new News((String) this.getResources().getText(R.string.news_name_2),(String) this.getResources().getText(R.string.news_des_2),"news1.jpg");
        News news2=new News((String) this.getResources().getText(R.string.news_name_3),(String) this.getResources().getText(R.string.news_des_3),"news2.jpg");
        News news3=new News((String) this.getResources().getText(R.string.news_name_4),(String) this.getResources().getText(R.string.news_des_4),"news3.jpg");
        list.add(news0);
        list.add(news1);
        list.add(news2);
        list.add(news3);
    }
}

