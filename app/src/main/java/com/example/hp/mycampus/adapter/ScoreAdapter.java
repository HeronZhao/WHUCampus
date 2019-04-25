package com.example.hp.mycampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Score;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<Score> {


    public ScoreAdapter(Context context, int resource, List<Score> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.score_layout,null);
        }
        Score score = getItem(position);
        if(score != null){
            TextView textViewYear = (TextView) v.findViewById(R.id.textViewYear);
            TextView textViewSemester = (TextView) v.findViewById(R.id.textViewSemester);
            TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
            TextView textViewCredit = (TextView) v.findViewById(R.id.textViewCredit);
            TextView textViewScore = (TextView) v.findViewById(R.id.textViewScore);
            textViewYear.setText(score.getYear());
            textViewSemester.setText(score.getSemester());
            textViewName.setText(score.getName());
            textViewCredit.setText(score.getCredit());
            textViewScore.setText(score.getScore() + "");
        }
        return v;
    }
}