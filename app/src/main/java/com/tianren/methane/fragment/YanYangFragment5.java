package com.tianren.methane.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tianren.methane.R;
import com.tianren.methane.common.MPChartMarkerView;
import com.tianren.methane.common.StringAxisValueFormatter;
import com.tianren.methane.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.tianren.methane.utils.MPChartHelper.LINE_FILL_COLORS;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class YanYangFragment5 extends BaseFragment {
    private View view;
    private LineChart lineChart;
    private List<String> xAxisValues;
    private List<Float> yAxisValues;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yan_yang_fragment5, container, false);
        initView();
        return view;
    }


    private void initView() {
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        for (int i = 1; i < 10; ++i) {
            if (i == 1) {
                xAxisValues.add("04-23");
            } else if (i == 3) {
                xAxisValues.add("04-25");
            } else if (i == 5) {
                xAxisValues.add("04-28");
            } else if (i == 7) {
                xAxisValues.add("05-21");
            } else if (i == 9) {
                xAxisValues.add("05-23");
            } else {
                xAxisValues.add("");
            }
            yAxisValues.add((float) (Math.random() * 30 + 40));
        }
        setLinesChart(lineChart, xAxisValues, yAxisValues, "进料量：吨", false);
    }


    /**
     * 绘制线图，默认最多绘制三种颜色。所有线均依赖左侧y轴显示。
     *
     * @param lineChart
     * @param xAxisValue    x轴的轴
     * @param yXAxisValues  y轴的值
     * @param title         每一个数据系列的标题
     * @param showSetValues 是否在折线上显示数据集的值。true为显示，此时y轴上的数值不可见，否则相反。
     */
    public static void setLinesChart(LineChart lineChart, List<String> xAxisValue,
                                     List<Float> yXAxisValues,
                                     String title,
                                     boolean showSetValues) {
        lineChart.getDescription().setEnabled(false);//设置描述
        lineChart.setPinchZoom(true);//设置按比例放缩柱状图

        MPChartMarkerView markerView = new MPChartMarkerView(lineChart.getContext(), R.layout.custom_marker_view);
        lineChart.setMarker(markerView);

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xAxisValue.size());
        /*xAxis.setAxisLineWidth(2f);*/
        xAxis.setValueFormatter(xAxisFormatter);

        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(80);
        leftAxis.setAxisMinimum(0);
        if (showSetValues) {
            leftAxis.setDrawLabels(false);//折线上显示值，则不显示坐标轴上的值
        }
        //leftAxis.setDrawZeroLine(true);
        /*leftAxis.setAxisMinimum(0f);*/
        /*leftAxis.setAxisLineWidth(2f);*/

        lineChart.getAxisRight().setEnabled(false);
        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //设置柱状图数据
        setLinesChartData(lineChart, yXAxisValues, title, showSetValues);
        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    private static void setLinesChartData(LineChart lineChart, List<Float> yXAxisValues, String title, boolean showSetValues) {
        //entriesList的长度表示
        List<List<Entry>> entriesList = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();
        for (int j = 0, n = yXAxisValues.size(); j < n; j++) {
            entries.add(new Entry(j, yXAxisValues.get(j)));
        }
        entriesList.add(entries);
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {

            for (int i = 0; i < lineChart.getData().getDataSetCount(); ++i) {
                LineDataSet set = (LineDataSet) lineChart.getData().getDataSetByIndex(i);
                set.setValues(entriesList.get(i));
                set.setLabel(title);
                set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//线模式为圆滑曲线（默认折线）
            }

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            for (int i = 0; i < entriesList.size(); ++i) {
                LineDataSet set = new LineDataSet(entriesList.get(i), title);
                set.setColor(Color.parseColor("#FF87CEFA"));
                set.setCircleColorHole(Color.WHITE);
                set.setMode(LineDataSet.Mode.STEPPED);//线模式为圆滑曲线（默认折线）
                if (entriesList.size() == 1) {
                    set.setDrawFilled(true);
                    set.setFillColor(LINE_FILL_COLORS[i % 3]);
                }
                dataSets.add(set);
            }

            LineData data = new LineData(dataSets);
            if (showSetValues) {
                data.setValueTextSize(10f);
                data.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                        return StringUtil.double2String(value, 1);
                    }
                });
            } else {
                data.setDrawValues(false);
            }

            lineChart.setData(data);
        }
    }
}
