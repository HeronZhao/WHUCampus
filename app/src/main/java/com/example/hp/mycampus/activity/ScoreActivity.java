package com.example.hp.mycampus.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Score;
import com.example.hp.mycampus.util.InfoUtil;

import java.io.Serializable;
import java.util.ArrayList;


public class ScoreActivity extends Activity {
    private String s_xq = null;
    private String s_xn = null;
    private Spinner sel_xn = null;
    private Spinner sel_xq = null;
    private Button select_score_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        sel_xn = (Spinner) findViewById(R.id.sel_xn);
        sel_xq = (Spinner) findViewById(R.id.sel_xq);

        //设置学年Spinner的监听事件 设置选择的值
        sel_xn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_xn = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                s_xn = "2015";
            }
        });

        //设置学期Spinner的监听事件 设置选择的值
        sel_xq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_xq = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                s_xq = "上";
            }
        });


        
        //设置成绩查询按钮的监听
        select_score_show=(Button) findViewById(R.id.query);


        select_score_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //筛选符合条件的成绩
                ArrayList<Score> scores = InfoUtil.getScores();

                ArrayList<Score> newScores = new ArrayList<>();
                for(Score score : scores) {
                    if(score.getYear().equals(s_xn)) {
                        String judge = (s_xq.equals("0"))?"上":"下";
                        if(score.getSemester().equals(judge)) {
                            newScores.add(score);
                        }
                    }
                }

                //跳转
                Intent intent = new Intent(ScoreActivity.this, ScoreShowActivity.class);
                //传递实例
                intent.putExtra("scores",(Serializable)newScores);
                startActivity(intent);
            }
        });
    }
}
