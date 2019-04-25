package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.hp.mycampus.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LostActivity extends Activity {
    private String[] Title={"丢失手机","捡到钱包","丢失校园卡","捡到校园卡","妈呀","这啥呀"};
    private String[] name={"张三","李四","王五","赵六","卫七","陈八"};
    private String[] Content={"丢失红色Iphone手机","捡到小熊钱包","丢失16级本科生校园卡","捡到17级本科生校园卡","丢失一个钥匙扣上面两把钥匙","这啥呀"};
    private String[] phoneNumber={"15112345678","15322225555","15901234567","13124576898","15901234567","15132165478"};
    private int[] Images={R.drawable.lost_1,R.drawable.lost2,R.drawable.lost_5,R.drawable.lost_4,R.drawable.lost_3,R.drawable.lost_5};
    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        for(int i=0;i<Title.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("title",Title[i]);
            map.put("content",Content[i]);
            map.put("phonenumber",phoneNumber[i]);
            map.put("name",name[i]);
            map.put("img",Images[i]);
            list.add(map);
        }
        ListView listview=(ListView) this.findViewById(R.id.listView);
        listview.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            final ViewHolder mHolder;
            if(convertView==null){
                view= LayoutInflater.from(LostActivity.this).inflate(R.layout.lost_item,null);
                mHolder=new ViewHolder();
                mHolder.card_title=(TextView)view.findViewById(R.id.cardTitle);
                mHolder.name=(TextView)view.findViewById(R.id.name);
                mHolder.content=(TextView)view.findViewById(R.id.content);
                mHolder.phoneNumber=(TextView)view.findViewById(R.id.phoneNumber);
                mHolder.img=(ImageView)view.findViewById(R.id.lost_img);

                view.setTag(mHolder);  //将ViewHolder存储在View中
            }else {
                view=convertView;
                mHolder=(ViewHolder)view.getTag();  //重新获得ViewHolder
            }
            mHolder.card_title.setText(list.get(position).get("title").toString());
            mHolder.name.setText(list.get(position).get("name").toString());
            mHolder.content.setText(list.get(position).get("content").toString());
            mHolder.phoneNumber.setText(list.get(position).get("phonenumber").toString());
            mHolder.img.setImageResource((int)list.get(position).get("img"));
            mHolder.img.setTag(position);
            mHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int choosePostion=(Integer)view.getTag();
                    showExitDialog1(choosePostion);
                }
            });
            return view;
        }
    }

    class ViewHolder{
        TextView card_title;
        TextView name;
        TextView phoneNumber;
        TextView content;
        ImageView img;
    }
    private void showExitDialog1(int choosePostion){
        ImageView img = new ImageView(this);
        img.setImageResource((int)list.get(choosePostion).get("img"));
        new AlertDialog.Builder(this)
                .setTitle("")
                .setView(img)
                .setPositiveButton("确定", null)
                .show();
    }
}

