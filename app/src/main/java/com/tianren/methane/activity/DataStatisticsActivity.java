package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.utils.MPChartHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class DataStatisticsActivity extends BaseActivity implements View.OnClickListener {

    private LineChart lineChart;
    private List<String> xAxisValues;
    private List<Float> yAxisValues;
    private LinearLayout ll_back;
    private TextView tv_title;
    private String statisticsName, title, tableName, columnName;
    private Bundle bundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datastatistics);

        initView();
        initData();
        MPChartHelper.setLineChart(lineChart, xAxisValues, yAxisValues, statisticsName, true);
    }

    private void initView() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        title = bundle.getString("title");
        statisticsName = bundle.getString("statisticsName");
        tableName = intent.getStringExtra("tableName");
        columnName = intent.getStringExtra("columnName");
        lineChart = (LineChart) findViewById(R.id.lineChart);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    private void initData() {
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        for (int i = 1; i < 13; ++i) {
            xAxisValues.add(String.valueOf(i));
            yAxisValues.add((float) (Math.random() * 10 + 1000));
        }
        Log.e("!", "initData: " + tableName);
        Log.e("!", "initData: " + columnName);
        Log.e("!", "initData: " + getMonthAgo());
        if (tableName != null) {
            WebServiceManage.getService(EntryService.class).getHistoricalData(tableName, columnName, getMonthAgo(), getNowTime()).setCallback(new SCallBack<BaseResponse<List<String>>>() {
                @Override
                public void callback(boolean isok, String msg, BaseResponse<List<String>> res) {
                    Log.e("1111111", "callback: " + isok);
                }
            });
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

    /**
     * 获取当前年月日时分秒
     *
     * @return
     */
    public static String getNowTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(now);
        return date;
    }

    /**
     * 获取一个月前的日期
     *
     * @return
     */
    public static String getMonthAgo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }
}
