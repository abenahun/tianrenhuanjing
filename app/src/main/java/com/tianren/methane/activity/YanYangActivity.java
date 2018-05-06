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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;
import com.tianren.methane.adapter.ModelAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.activity.MainActivity.modelMap;
import static com.tianren.methane.activity.MainActivity.sensorDataMap;

/**
 * Created by Qiu on 2018/4/09.
 */
public class YanYangActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_qiguishaixuan;
    private Button btn_data;
    private String[] items = {"厌氧罐 No1", "厌氧罐 No2", "厌氧罐 No3"};

    //    private LineChart diagnosisChart;//智能诊断曲线图
//    private LineChart cycleChart1, cycleChart2;//搅拌周期折线图
//    private RadarChart predictChart;//产气预测蛛网图
    private LinearLayout ll_back;
    private TextView tv_title;
    private SwipeMenuRecyclerView recyclerView;
    private ModelAdapter adapter;

    private ImageView moreIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanyang);
        initView();
//        initChart();
        loadData();
    }


    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("厌氧");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setImageResource(R.mipmap.call_police);
        moreIv.setOnClickListener(this);
        moreIv.setVisibility(View.VISIBLE);
//        diagnosisChart = (LineChart) findViewById(R.id.diagnosisChart);
//        cycleChart1 = (LineChart) findViewById(R.id.cycleChart1);
//        cycleChart2 = (LineChart) findViewById(R.id.cycleChart2);
//        predictChart = (RadarChart) findViewById(R.id.predictChart);

        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recyclerView);
        adapter = new ModelAdapter(this, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewSwipeEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.head_yanyang, recyclerView, false);
        tv_qiguishaixuan = (TextView) headView.findViewById(R.id.tv_qiguishaixuan);
        btn_data = (Button) headView.findViewById(R.id.btn_data);
        tv_qiguishaixuan.setOnClickListener(this);
        btn_data.setOnClickListener(this);
        recyclerView.addHeaderView(headView);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        List<ModelAdapter.ModelBean> list = new ArrayList<>();
        if (sensorDataMap == null) {
            list = null;
        } else {
            list.add(getModel("d37"));
            list.add(getModel("d38"));
            list.add(getModel("d39"));
            list.add(getModel("d40"));
            list.add(getModel("d41"));
            list.add(getModel("d42"));
            list.add(getModel("d43"));
            list.add(getModel("d44"));
            list.add(getModel("d45"));
            list.add(getModel("d46"));
            list.add(getModel("d47"));
            list.add(getModel("d48"));
            list.add(getModel("d49"));
            list.add(getModel("d50"));
        }
        adapter.addItems(list);
    }

    public ModelAdapter.ModelBean getModel(String s) {
        if (sensorDataMap == null) {
            return null;
        } else {
            return new ModelAdapter.ModelBean(s, sensorDataMap.get(s).getNickName(), modelMap.get(s),
                    sensorDataMap.get(s).getSuitableMaximum(), sensorDataMap.get(s).getSuitableMinimum(),
                    sensorDataMap.get(s).getSensorUnit());
        }
    }
//
//    private void initChart() {
//        //显示边界
//        diagnosisChart.setDrawBorders(false);
//        cycleChart1.setDrawBorders(false);
//        cycleChart2.setDrawBorders(false);
//        diagnosisChart.getXAxis().setGridColor(Color.parseColor("#00ffffff"));
//        cycleChart1.getXAxis().setGridColor(Color.parseColor("#00ffffff"));
//        cycleChart2.getXAxis().setGridColor(Color.parseColor("#00ffffff"));
//        diagnosisChart.getAxisRight().setEnabled(false);
//        cycleChart1.getAxisRight().setEnabled(false);
//        cycleChart2.getAxisRight().setEnabled(false);
//        diagnosisChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        cycleChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        cycleChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        cycleChart2.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        cycleChart1.getXAxis().setAxisMinimum(0);
//        cycleChart1.getXAxis().setAxisMaximum(24);
//        cycleChart2.getXAxis().setAxisMinimum(0);
//        cycleChart2.getXAxis().setAxisMaximum(24);
//
//
//        //初始化蛛网图
//        predictChart.setBackgroundColor(Color.WHITE);
//        predictChart.getDescription().setEnabled(false);
//        predictChart.setWebLineWidth(1f);
//        predictChart.setWebColor(Color.BLACK);
//        predictChart.setWebLineWidthInner(1f);
//        predictChart.setWebColorInner(Color.BLACK);
//        predictChart.setWebAlpha(100);
//
//        //设置数据
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            entries.add(new Entry(i, (float) (Math.random()) * 10));
//        }
//        List<Entry> entries1 = new ArrayList<>();
//        for (int i = 1; i < 24; i++) {
//            entries1.add(new Entry(i, (float) (Math.random()) * 10));
//        }
//        List<Entry> entries2 = new ArrayList<>();
//        for (int i = 1; i < 24; i++) {
//            entries2.add(new Entry(i, (float) (Math.random()) * 10));
//        }
//
//        //为智能诊断设置曲线数据
//        LineDataSet lineDataDia = new LineDataSet(entries, "智能诊断");
//        lineDataDia.setColor(Color.BLUE);
//        lineDataDia.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        lineDataDia.setDrawCircles(false);
//        LineData diaData = new LineData(lineDataDia);
//        diagnosisChart.setData(diaData);
//
//        //为搅拌周期设置曲线数据
//        LineData cycleData1 = new LineData(new LineDataSet(entries1, "搅拌速率"));
//        LineData cycleData2 = new LineData(new LineDataSet(entries2, "电费标准"));
//        cycleChart1.setData(cycleData1);
//        cycleChart2.setData(cycleData2);
//
//        //为产气预测蛛网图做初始化
//        predictChart.animateXY(1400, 1400,
//                Easing.EasingOption.EaseInOutQuad,
//                Easing.EasingOption.EaseInOutQuad);
//        XAxis xAxis = predictChart.getXAxis();
//        xAxis.setTextSize(12f);
//        xAxis.setYOffset(0f);
//        xAxis.setXOffset(0f);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//
//            private String[] mActivities = new String[]{"产气", "产电", "市场", "研发"};
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return mActivities[(int) value % mActivities.length];
//            }
//        });
//        xAxis.setTextColor(Color.BLACK);
//
//        YAxis yAxis = predictChart.getYAxis();
//        yAxis.setLabelCount(5, false);
//        yAxis.setTextSize(9f);
//        yAxis.setAxisMinimum(0f);
//        yAxis.setAxisMaximum(80f);
//        yAxis.setDrawLabels(false);
//        setData();
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qiguishaixuan:
                AlertDialog dialog = new AlertDialog.Builder(YanYangActivity.this).setTitle("厌氧罐筛选")
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
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.moreIv:
                startActivity(new Intent(this, CallPoliceActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 设置蛛网图的数据
     */
//    public void setData() {
//        float mult = 80;
//        float min = 20;
//        int cnt = 4;
//        ArrayList<RadarEntry> entries1 = new ArrayList<>();
//        for (int i = 0; i < cnt; i++) {
//            float val1 = (float) (Math.random() * mult) + min;
//            entries1.add(new RadarEntry(val1));
//        }
//
//        RadarDataSet set1 = new RadarDataSet(entries1, "产气预测");
//        set1.setColor(Color.RED);
//        set1.setDrawFilled(true);
//        set1.setLineWidth(2f);
//        set1.setFillAlpha(0);
//        set1.setDrawHighlightCircleEnabled(true);
//        set1.setDrawHighlightIndicators(false);
//
//        ArrayList<IRadarDataSet> sets = new ArrayList<>();
//        sets.add(set1);
//
//        RadarData data = new RadarData(sets);
//        data.setValueTextSize(8f);
//        data.setDrawValues(false);
//        data.setValueTextColor(Color.WHITE);
//
//        predictChart.setData(data);
//        predictChart.invalidate();
//    }

    private ModelAdapter.ModelListener listener = new ModelAdapter.ModelListener() {

        @Override
        public void onClick(ModelAdapter.ModelBean bean) {
            Intent intent7 = new Intent(YanYangActivity.this, DataStatisticsActivity.class);
            intent7.putExtra("title", "厌氧走势");
            intent7.putExtra("statisticsName", "厌氧走势");
            startActivity(intent7);
        }
    };
}
