package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.LineChart;
import com.tianren.methane.R;
import com.tianren.methane.utils.MPChartHelper;
import com.tianren.methane.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class RunningStatusActivity extends BaseActivity implements View.OnClickListener{

    private LineChart lineChart;
    private List<String> xAxisValues;
    private List<Float> yAxisValues;
    private LinearLayout ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runningstatus);

        initView();
        initData();
        MPChartHelper.setLineChart(lineChart,xAxisValues,yAxisValues,"安全指数",true);
    }

    private void initView(){
        lineChart=(LineChart)findViewById(R.id.lineChart);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("运行状态");
    }

    private void initData(){
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        for(int i=1;i<13;++i){
            xAxisValues.add(String.valueOf(i));
            yAxisValues.add((float)(Math.random()*10+1000));
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
