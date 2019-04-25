package com.example.hp.mycampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.activity.NewsShowActivity;
import com.example.hp.mycampus.model.News;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> {

    // 数据
    private List<News> list;
    // 上下文环境
    private Context context;
    MyItemClickListener listener = null;

    // 构造函数
    public NewsRecycleAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // 这个方法返回viewholder，创建一个viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 关联相关样式
        View v = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        ViewHolder heroViewHolder = new ViewHolder(v);
        return heroViewHolder;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/"+ list.get(position).getPhotoId())));
        holder.name.setText(list.get(position).getName());
        holder.descrip.setText(list.get(position).getDes());
        // 点击事件也可以写在这里
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chosenPosition = (Integer) view.getTag();
                Intent intent = new Intent(context, NewsShowActivity.class);
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putInt("chosenPosition",chosenPosition );//  放入data值为int型
                intent.putExtras(bundle);// 将Bundle对象放入到Intent上
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // RecycleView的特点之一，必须是自定义viewholder
    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView; //这个是为了之后可以添加点击事件
        ImageView imageView;
        TextView name;
        TextView descrip;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_item);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            descrip = (TextView) itemView.findViewById(R.id.tv_des);
        }
    }

}