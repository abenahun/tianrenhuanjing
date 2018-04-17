package com.tianren.methane.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.tianren.methane.constant.Constant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Administrator on 2018/3/20.
 */

public class QiGuiActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_qiguishaixuan;
    private Button btn_data;
    private String[] items = {"pro00000001.1", "pro00000002.1", "pro00000003.1", "pro00000004.1"};

    private LineChart mLineChart;
    private BarChart barChart;
    private LinearLayout ll_back;
    private TextView tv_title;

    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qigui);
        initView();
//        initChart();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("气柜");

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_qigui, recyclerView, false);
        tv_qiguishaixuan = (TextView) headView.findViewById(R.id.tv_qiguishaixuan);
        btn_data = (Button) headView.findViewById(R.id.btn_data);
        tv_qiguishaixuan.setOnClickListener(this);
        btn_data.setOnClickListener(this);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void initChart() {
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "耗电量");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);

        //设置数据
        List<BarEntry> barentries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            barentries.add(new BarEntry(i, (float) (Math.random()) * 70));
        }
        BarDataSet barDataSet = new BarDataSet(barentries, "总能耗");
        BarData bardata = new BarData(barDataSet);
        barChart.setData(bardata);
    }

    private void loadData() {
        List<ModelAdapter.ModelBean> list = new ArrayList<>();
        if (sensorDataMap == null){
            list = null;
        }else {
            list.add(getModel("d29"));
            list.add(getModel("d30"));
            list.add(getModel("d31"));
            list.add(getModel("d32"));
            list.add(getModel("d33"));
            list.add(getModel("d34"));
            list.add(getModel("d35"));
            list.add(getModel("d36"));
        }
        adapter.addItems(list);
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null){
            return null;
        }else {
        return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s), sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum(),sensorDataMap.get(s).getSensorUnit());
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qiguishaixuan:
                AlertDialog dialog = new AlertDialog.Builder(QiGuiActivity.this).setTitle("气柜筛选")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_qiguishaixuan.setText(items[which]);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;

            case R.id.btn_data:
                Intent intent = new Intent(QiGuiActivity.this, ListActivity.class);
                intent.putExtra("type", Constant.INTENT_TYPE_YANYANG);
                startActivity(intent);
                break;

            case R.id.ll_back:
                finish();
                break;
        }
    }
}
