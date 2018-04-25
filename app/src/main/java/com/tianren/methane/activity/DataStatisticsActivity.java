package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.tianren.methane.R;
import com.tianren.methane.utils.MPChartHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class DataStatisticsActivity extends BaseActivity implements View.OnClickListener{

    private BarChart barChart1;
    private List<String> xAxisValues;
    private List<Float> yAxisValues;
    private LinearLayout ll_back;
    private TextView tv_title;
    private String statisticsName,title;
    private Bundle bundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datastatistics);

        initView();
        initData();
        MPChartHelper.setBarChart(barChart1,xAxisValues,yAxisValues,statisticsName,10,null);
    }

    private void initView(){
        Intent intent = getIntent();
        bundle = intent.getExtras();
        title = bundle.getString("title");
        statisticsName = bundle.getString("statisticsName");
        barChart1=(BarChart)findViewById(R.id.barChart1);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    private void initData(){
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        for(int i=0;i<10;++i){
            xAxisValues.add(String.valueOf(i));
            yAxisValues.add((float)(Math.random()*80+2000));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }
}
