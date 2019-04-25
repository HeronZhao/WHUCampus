package com.example.hp.mycampus.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.mycampus.R;

import com.example.hp.mycampus.model.Time;
import com.example.hp.mycampus.util.TimeDatabaseHelper;

import java.util.Collections;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TimeRecycleAdapter extends RecyclerView.Adapter<TimeRecycleAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    // 数据
    private List<Time> list;
    // 上下文环境
    private Context context;

    //SQLite Helper类
    private TimeDatabaseHelper timeDatabaseHelper;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(list,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);

    }

    @Override
    public void onItemDelete(int position) {
        String name=list.get(position).getName();
        String sql="delete from time where name="+"'"+name+"'";
        System.out.println(sql);
        SQLiteDatabase sqLiteDatabase =  timeDatabaseHelper.getWritableDatabase();
        //执行SQL语句
        sqLiteDatabase.execSQL(sql);
        //移除数据
        list.remove(position);
        notifyItemRemoved(position);

    }

    // 构造函数
    public TimeRecycleAdapter(List<Time> list, Context context,TimeDatabaseHelper timeDatabaseHelper) {
        this.list = list;
        this.context = context;
        this.timeDatabaseHelper=timeDatabaseHelper;
    }

    // 这个方法返回viewholder，创建一个viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 关联相关样式
        View v = LayoutInflater.from(context).inflate(R.layout.time_item,parent,false);
        ViewHolder heroViewHolder = new ViewHolder(v);
        return heroViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.date.setText("计划时间:"+list.get(position).getDate());
        holder.countdown.setText("还剩"+list.get(position).getCountDown()+"天");
        if(Integer.parseInt(list.get(position).getCountDown())<=10) {
            holder.countdown.setTextColor(android.graphics.Color.RED);
        }
        // 点击事件也可以写在这里
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // RecycleView的特点之一，必须是自定义viewholder
    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView; // 这个是为了之后可以添加点击事件
        TextView name;
        TextView date;
        TextView countdown;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_item);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            countdown = (TextView) itemView.findViewById(R.id.countdown);
        }
    }

}
