package com.tianren.methane.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.LineChart;
import com.tianren.methane.R;
import com.tianren.methane.activity.DataEntryActivity;
import com.tianren.methane.manager.LineChartManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private View view;

    private ViewFlipper flipper;
    private List testList;
    private int count;

    private TextView tv_luru;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_two, null);
//        initFlipper();
//        initView();

        initLineCharts();
        return view;
    }

    private void initLineCharts() {

        LineChart lineChart1 = (LineChart) view.findViewById(R.id.line_chart1);
        LineChart lineChart2 = (LineChart) view.findViewById(R.id.line_chart2);

        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);
        LineChartManager lineChartManager2 = new LineChartManager(lineChart2);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            xValues.add((float) i);
        }
        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
            List<Float> yValue = new ArrayList<>();
            for (int j = 0; j <= 7; j++) {
                yValue.add((float) (Math.random() * 80) + 200);
            }
        List<Float> yValue2 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue2.add((float) (Math.random() * 80) + 350);
        }

        List<Float> yValue3 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue3.add((float) (Math.random() * 80) + 800);
        }

        List<Float> yValue4 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue4.add((float) (Math.random() * 80) + 500);
        }

            yValues.add(yValue);
            yValues.add(yValue2);
            yValues.add(yValue4);
            yValues.add(yValue3);


        //设置y轴的数据()
        List<List<Float>> yValues2 = new ArrayList<>();

        List<Float> yValue5 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue5.add((float) (Math.random() * 80) + 1000);
        }
        List<Float> yValue6 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue6.add((float) (Math.random() * 80) + 1500);
        }

        List<Float> yValue7 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue7.add((float) (Math.random() * 80) + 2500);
        }

        yValues2.add(yValue5);
        yValues2.add(yValue6);
        yValues2.add(yValue7);


        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.BLUE);
        colours.add(Color.GREEN);
        colours.add(Color.RED);
        colours.add(Color.YELLOW);
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("水耗");
        names.add("电耗");
        names.add("热耗");
        names.add("总能耗");

        //颜色集合
        List<Integer> colours2 = new ArrayList<>();
        colours2.add(Color.BLUE);
        colours2.add(Color.GREEN);
        colours2.add(Color.RED);
        //线的名字集合
        List<String> names2 = new ArrayList<>();
        names2.add("产电效益");
        names2.add("产期效益");
        names2.add("总效益");

        //创建多条折线的图表
        lineChartManager1.showLineChart(xValues, yValues2, names2, colours2);
        lineChartManager1.setYAxis(3000, 0, 6);
        lineChartManager1.setDescription("效益表");

        lineChartManager2.showLineChart(xValues, yValues, names, colours);
        lineChartManager2.setYAxis(1000, 0, 5);
        lineChartManager2.setDescription("能耗表");
    }

    private void initView() {
        tv_luru = (TextView) view.findViewById(R.id.tv_luru);
        tv_luru.setOnClickListener(this);
    }

    private void initFlipper() {
        flipper = (ViewFlipper) view.findViewById(R.id.flipper);
        //初始化list数据
        testList = new ArrayList();
        testList.add(0, "气柜压力超标");
        testList.add(1, "未持卡人员闯入报警区域内");
        testList.add(2, "预处理搅拌机运行异常");
        testList.add(3, "厌氧罐出现异常运转");
        testList.add(4, "脱碳出现异常");
        count = testList.size();
        for (int i = 0; i < count; i++) {
            final View ll_content = View.inflate(getActivity(), R.layout.item_flipper, null);
            TextView tv_content = (TextView) ll_content.findViewById(R.id.tv_content);
            tv_content.setText(testList.get(i).toString());
            flipper.addView(ll_content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_luru:
                Intent intent = new Intent(getActivity(), DataEntryActivity.class);
                startActivity(intent);
                break;

        }
    }
}
