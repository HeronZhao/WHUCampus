package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Score;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartShowActivity extends AppCompatActivity {

    //后续要修改的话，建议根据需要选择API重写，MPandroidchart每个版本区别比较大，老版本的函数和接口有很多没法使用
    PieChart pieChart;
    private String[] grade = new String[]{"90-100分段", "85-89分段", "82-84分段", "78-81分段", "75-77分段","72-74分段","68-71分段","64-67分段","60-63分段","不及格",};

    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_show);

        Intent i = getIntent();
        ArrayList<Score> scoresList = (ArrayList<Score>) i.getSerializableExtra("scoresList");


        //饼图
        pieChart = findViewById(R.id.pie_chart);
        initPieChart();
        setPieData(scoresList);
        pieChart.invalidate();

        //柱状图
        BarChart barchart = findViewById(R.id.bar_chart);
        barchart = initBarChart(barchart);
        BarData barData = setBarData(
                getGPA(scoresList,"公共必修"),
                getGPA(scoresList,"公共选修"),
                getGPA(scoresList,"专业必修"),
                getGPA(scoresList,"专业选修"));
        barchart.setData(barData);
        barchart.invalidate();

        //折线图
        lineChart = findViewById(R.id.line_Chart);
        initLineChart();
        setLineData();
        lineChart.invalidate();

    }

    /**
     * 初始化饼图表
     */
    private void initPieChart() {
        //设置X轴的动画
        pieChart.animateX(1400);
        //使用百分比
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);


        //设置图表描述
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        //设置是否可转动
        pieChart.setRotationEnabled(true);
    }

    /**
     * 饼图设置数据
     */
    private void setPieData(ArrayList<Score> scoresList) {
        //设置标题
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < grade.length; i++) {
            titles.add(grade[i]);
        }
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(getGPPercent(scoresList,4.0f),grade[0]));
        entries.add(new PieEntry(getGPPercent(scoresList,3.7f),grade[1]));
        entries.add(new PieEntry(getGPPercent(scoresList,3.3f),grade[2]));
        entries.add(new PieEntry(getGPPercent(scoresList,3.0f),grade[3]));
        entries.add(new PieEntry(getGPPercent(scoresList,2.7f),grade[4]));
        entries.add(new PieEntry(getGPPercent(scoresList,2.3f),grade[5]));
        entries.add(new PieEntry(getGPPercent(scoresList,2.0f),grade[6]));
        entries.add(new PieEntry(getGPPercent(scoresList,1.5f),grade[7]));
        entries.add(new PieEntry(getGPPercent(scoresList,1.0f),grade[8]));
        entries.add(new PieEntry(getGPPercent(scoresList,0.0f),grade[9]));

        //饼图数据集
        PieDataSet dataset = new PieDataSet(entries, "分段占比");
        //设置饼状图Item之间的间隙
        dataset.setSliceSpace(3f);
        //饼图Item被选中时变化的距离
        dataset.setSelectionShift(10f);
        //颜色填充
        dataset.setColors(new int[]{Color.rgb(0, 255, 255),
                Color.rgb(60, 179, 113),
                Color.rgb(255, 165, 0),
                Color.rgb(124, 252, 0),
                Color.rgb(255, 182, 113),
                Color.rgb(234, 255, 56),
                Color.rgb(44, 159, 220),
                Color.rgb(57, 243, 138),
                Color.rgb(168, 208, 150),
                Color.rgb(127, 55, 205),});
        //数据填充
        PieData piedata = new PieData(dataset);
        //设置饼图上显示数据的字体大小
        piedata.setValueTextSize(5);
        piedata.setDrawValues(false);
        pieChart.setData(piedata);
    }

    /**
     * 初始化折线图
     */
    private void initLineChart(){
        Description description =new Description();
        description.setText("各学期GPA走势");
        description.setTextColor(Color.RED);
        description.setTextSize(15);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
    }

    /**
     * 折线图设置数据
     */
    private void setLineData() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        entries1.add(new Entry(1,3.62f));
        entries1.add(new Entry(2,3.73f));
        entries1.add(new Entry(3,3.8f));
        entries1.add(new Entry(4,3.65f));
        entries1.add(new Entry(5,3.78f));

        entries2.add(new Entry(1,3.62f));
        entries2.add(new Entry(2,3.715f));
        entries2.add(new Entry(3,3.75f));
        entries2.add(new Entry(4,3.7f));
        entries2.add(new Entry(5,3.71f));

        LineDataSet set1 = new LineDataSet(entries1,"学期平均GPA");
        LineDataSet set2 = new LineDataSet(entries2,"总GPA");

        set1.setColor(Color.GREEN);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(3f);//设置焦点圆心的大小
        set1.setValueTextSize(10f);

        set2.setColor(Color.YELLOW);
        set2.setCircleColor(Color.GRAY);
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        set2.setValueTextSize(10f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
    }

    /**
     * 柱状图初始化
     * @return
     */
    public BarChart initBarChart(BarChart barChart){

        barChart.setDescription(null);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        List<String> xAxisValue = new ArrayList<>();
        xAxisValue.add("公共必修");
        xAxisValue.add(" ");
        xAxisValue.add("公共选修");
        xAxisValue.add(" ");
        xAxisValue.add("专业必修");
        xAxisValue.add(" ");
        xAxisValue.add("专业选修");

        setAxis(barChart,xAxisValue);
        barChart.setFitBars(true);
        return barChart;
    }

    /**
     * 设置柱状图X轴和Y轴
     */
    public void setAxis(BarChart barChart, List<String> xAxisValue){

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴在底部出现
        xAxis.setAxisLineWidth(0.5f);
        //xAxis.setAxisMaximum(4);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));

        yAxisLeft.setAxisLineWidth(1);
        yAxisLeft.setAxisMinimum(2);
        yAxisLeft.setAxisMaximum(4);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisLeft.setEnabled(true);

        yAxisRight.setEnabled(false);
    }

    /**
     * 设置柱状图数据
     * @return
     */
    public BarData setBarData(float gpaPubC,float gpaPubE,float gpaProC,float gpaProE){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0,gpaPubC));
        entries.add(new BarEntry(2f,gpaPubE));
        entries.add(new BarEntry(4,gpaProC));
        entries.add(new BarEntry(6f,gpaProE));

        BarDataSet barDataSet = new BarDataSet(entries,"各类型课程GPA");
        BarData barData = new BarData(barDataSet);
        return  barData;
    }

    /**
     * 返回分数段科目数百分比
     * @return
     */
    public float getGPPercent(ArrayList<Score> scoresList,float gp){
        float numOfSubject = 0;
        for (Score score :
                scoresList) {
            if(getGradePoint(Integer.parseInt(score.getScore())) == gp)
                numOfSubject++;
        }
        return  numOfSubject/scoresList.size();

    }

    /**
     * 根据成绩返回绩点
     * @return
     */
    public float getGradePoint(int score){
        if(score>=90)
            return 4.0f;
        else if(score>=85 && score<90)
            return 3.7f;
        else if(score>=82 && score<85)
            return 3.3f;
        else if(score>=78 && score<82)
            return 3.0f;
        else if(score>=75 && score<78)
            return 2.7f;
        else if(score>=72 && score<75)
            return 2.3f;
        else if(score>=68 && score<72)
            return 2.0f;
        else if(score>=64 && score<68)
            return 1.5f;
        else if(score>=60 && score<64)
            return 1.0f;
        else
            return 0f;
    }

    /**
     * 根据课程类型计算GPA
     * @return
     */
    public float getGPA(ArrayList<Score> scoreList, String classType){
        float creditPlusGP = 0;//学分*绩点
        float totalCredit = 0;//总学分
        for (Score score :
                scoreList) {
            //算所有课程GPA时，设置classType为All
            if (score.getType().equals(classType) || classType == "All"){
                float gradepoint = getGradePoint(Integer.parseInt(score.getScore()));
                float credit = Float.parseFloat(score.getCredit());
                creditPlusGP += gradepoint*credit;
                totalCredit += credit;
            }
        }
        return (totalCredit == 0) ? 0 : creditPlusGP/totalCredit;
    }

}

