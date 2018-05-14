package com.tianren.methane.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.tianren.methane.R;
import com.tianren.methane.utils.MPChartHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class YanYangFragment4 extends BaseFragment {
    private View view;
    private LineChart lineChart;
    private List<String> xAxisValues;
    private List<Float> yAxisValues;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yan_yang_fragment4, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
    }

    private void initData() {
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        for (int i = 1; i < 13; ++i) {
            xAxisValues.add(String.valueOf(i));
            yAxisValues.add((float) (Math.random() * 10 + 1000));
        }
        MPChartHelper.setLineChart(lineChart, xAxisValues, yAxisValues, "聚类分析", true);
    }

}
