package com.tianren.methane.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.CapacityBean;
import com.tianren.acommon.bean.ConsumptionBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.activity.DataStatisticsActivity;
import com.tianren.methane.activity.RunningStatusActivity;
import com.tianren.methane.manager.LineChartManager;
import com.tianren.methane.utils.MPChartHelper;
import com.tianren.methane.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private View view;

    private ViewFlipper flipper;
    private List testList;
    private int count;
    private TextView tv_luru;
    private BarChart barChart1;

    private List<Integer> xAxisValues;
    private List<Float> yAxisValues1;
    private List<Float> yAxisValues2;

    private LinearLayout ll_runningstatus, ll_zongnenghao, ll_shuihao,
            ll_dianhao, ll_rehao, ll_chanqixiaoyi,
            ll_zongxiaoyi, ll_chandianxiaoyi, ll_jinliaoliang;
    private TextView allConsume, waterConsume, eleConsume, hotConsume;
    private TextView airEarnings, eleEarnings, allEarnings;
    private TextView tv_tiyou, tv_jinliao;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_three, null);
//        initFlipper();
        initView();
//        initLineCharts();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBarCharts();
        loadData();
    }

    private void initView() {
        /*tv_luru = (TextView) view.findViewById(R.id.tv_luru);
        tv_luru.setOnClickListener(this);*/

        ll_runningstatus = (LinearLayout) view.findViewById(R.id.ll_runningstatus);
        ll_runningstatus.setOnClickListener(this);

        ll_zongnenghao = (LinearLayout) view.findViewById(R.id.ll_zongnenghao);
        ll_zongnenghao.setOnClickListener(this);

        ll_shuihao = (LinearLayout) view.findViewById(R.id.ll_shuihao);
        ll_shuihao.setOnClickListener(this);

        ll_dianhao = (LinearLayout) view.findViewById(R.id.ll_dianhao);
        ll_dianhao.setOnClickListener(this);

        ll_rehao = (LinearLayout) view.findViewById(R.id.ll_rehao);
        ll_rehao.setOnClickListener(this);

        ll_chanqixiaoyi = (LinearLayout) view.findViewById(R.id.ll_chanqixiaoyi);
        ll_chanqixiaoyi.setOnClickListener(this);

        ll_zongxiaoyi = (LinearLayout) view.findViewById(R.id.ll_zongxiaoyi);
        ll_zongxiaoyi.setOnClickListener(this);

        ll_chandianxiaoyi = (LinearLayout) view.findViewById(R.id.ll_chandianxiaoyi);
        ll_chandianxiaoyi.setOnClickListener(this);

        ll_jinliaoliang = (LinearLayout) view.findViewById(R.id.ll_jinliaoliang);
        ll_jinliaoliang.setOnClickListener(this);
        allConsume = (TextView) view.findViewById(R.id.allConsume);
        waterConsume = (TextView) view.findViewById(R.id.waterConsume);
        eleConsume = (TextView) view.findViewById(R.id.eleConsume);
        hotConsume = (TextView) view.findViewById(R.id.hotConsume);
        airEarnings = (TextView) view.findViewById(R.id.airEarnings);
        eleEarnings = (TextView) view.findViewById(R.id.eleEarnings);
        allEarnings = (TextView) view.findViewById(R.id.allEarnings);
        tv_tiyou = (TextView) view.findViewById(R.id.tv_tiyou);
        tv_jinliao = (TextView) view.findViewById(R.id.tv_jinliao);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                initBarCharts();
            }
        });

    }

    private void loadData() {
        WebServiceManage.getService(EntryService.class).getCapacity().
                setCallback(new SCallBack<BaseResponse<List<CapacityBean>>>() {
                    @Override
                    public void callback(boolean isok, String msg, BaseResponse<List<CapacityBean>> res) {
                        Log.e("isok", "callback: " + isok);
                        if (isok) {
                            Double gasProduction = res.getData().get(0).getGasProduction();
                            airEarnings.setText((gasProduction.equals(0) ? "0" : (gasProduction + "")) + "元");
                            Double powerGeneration = res.getData().get(0).getPowerGeneration();
                            eleEarnings.setText((powerGeneration.equals(0) ? "0" : (powerGeneration + "")) + "元");
                            allEarnings.setText((gasProduction + powerGeneration) + "元");
                            Double tiyou = res.getData().get(0).getLiftingCapacity();
                            tv_tiyou.setText((tiyou.equals(0) ? "0" : (tiyou + "")) + "元");
                        }
                    }
                });
        WebServiceManage.getService(EntryService.class).getConsumptionData().
                setCallback(new SCallBack<BaseResponse<List<ConsumptionBean>>>() {
                    @Override
                    public void callback(boolean isok, String msg, BaseResponse<List<ConsumptionBean>> res) {
                        if (isok) {
                            Double waterConsumption = res.getData().get(0).getWaterConsumption();
                            waterConsume.setText(waterConsumption.equals(0) ? "0" : (waterConsumption + ""));
                            Double powerConsumption = res.getData().get(0).getPowerConsumption();
                            eleConsume.setText(powerConsumption.equals(0) ? "0" : (powerConsumption + ""));
                            Double airConsumption = res.getData().get(0).getAirConsumption();
                            hotConsume.setText(airConsumption.equals(0) ? "0" : (airConsumption + ""));
                            allConsume.setText((waterConsumption + powerConsumption + airConsumption) + "");
                            Double jinliao = res.getData().get(0).getFeedAmount();
                            tv_jinliao.setText(jinliao.equals(0) ? "0" : (jinliao + "") + "吨");
                        }
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void initBarCharts() {
        barChart1 = (BarChart) view.findViewById(R.id.bar_chart1);
        xAxisValues = new ArrayList<>();
        yAxisValues1 = new ArrayList<>();
        yAxisValues2 = new ArrayList<>();
        for (int i = 1; i < 8; ++i) {
            xAxisValues.add(i);
            yAxisValues1.add((float) (Math.random() * 80 + 1500));
            yAxisValues2.add((float) (Math.random() * 80 + 1000));
        }

        MPChartHelper.setTwoBarChart(barChart1, xAxisValues, yAxisValues1, yAxisValues2, "总效益", "总能耗");

    }

    private void initLineCharts() {

        LineChart lineChart1;
        lineChart1 = (LineChart) view.findViewById(R.id.line_chart1);
//        LineChart lineChart2 = (LineChart) view.findViewById(R.id.line_chart2);

        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);
//        LineChartManager lineChartManager2 = new LineChartManager(lineChart2);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            xValues.add((float) i);
        }
        //设置y轴的数据()
       /* List<List<Float>> yValues = new ArrayList<>();
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
            yValues.add(yValue3);*/


        //设置y轴的数据()
        List<List<Float>> yValues2 = new ArrayList<>();

        List<Float> yValue5 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue5.add((float) (Math.random() * 80) + 1500);
        }
        List<Float> yValue6 = new ArrayList<>();
        for (int j = 0; j <= 7; j++) {
            yValue6.add((float) (Math.random() * 80) + 1100);
        }

        yValues2.add(yValue5);
        yValues2.add(yValue6);

      /*  //颜色集合
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
        names.add("总能耗");*/

        //颜色集合
        List<Integer> colours2 = new ArrayList<>();
        colours2.add(getResources().getColor(R.color.maincolor));
        colours2.add(getResources().getColor(R.color.orange));
        //线的名字集合
        List<String> names2 = new ArrayList<>();
        names2.add("总收益");
        names2.add("总能耗");

        //创建多条折线的图表
        lineChartManager1.showLineChart(xValues, yValues2, names2, colours2);
        lineChartManager1.setYAxis(3000, 0, 4);
        lineChartManager1.setDescription("经营数据表");

       /* lineChartManager2.showLineChart(xValues, yValues, names, colours);
        lineChartManager2.setYAxis(1000, 0, 5);
        lineChartManager2.setDescription("能耗表");*/
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
        switch (v.getId()) {
            case R.id.ll_runningstatus:
                Intent intent = new Intent(getActivity(), RunningStatusActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_zongnenghao:
                Intent intent2 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent2.putExtra("title", "总能耗统计");
                intent2.putExtra("statisticsName", "总能耗统计表");
                startActivity(intent2);
                break;

            case R.id.ll_shuihao:
                Intent intent3 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent3.putExtra("title", "水耗统计");
                intent3.putExtra("statisticsName", "水耗统计表");
                intent3.putExtra("tableName", "Consumption");
                intent3.putExtra("columnName", StringUtil.humpToLine2("waterConsumption"));
                startActivity(intent3);
                break;

            case R.id.ll_dianhao:
                Intent intent4 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent4.putExtra("title", "电耗统计");
                intent4.putExtra("statisticsName", "电耗统计表");
                startActivity(intent4);
                break;

            case R.id.ll_rehao:
                Intent intent5 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent5.putExtra("title", "热耗统计");
                intent5.putExtra("statisticsName", "热耗统计表");
                startActivity(intent5);
                break;

            case R.id.ll_chanqixiaoyi:
                Intent intent6 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent6.putExtra("title", "产气效益统计");
                intent6.putExtra("statisticsName", "产气效益统计表");
                startActivity(intent6);
                break;

            case R.id.ll_zongxiaoyi:
                Intent intent7 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent7.putExtra("title", "总效益统计");
                intent7.putExtra("statisticsName", "总效益统计表");
                startActivity(intent7);
                break;

            case R.id.ll_chandianxiaoyi:
                Intent intent8 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent8.putExtra("title", "产电效益统计");
                intent8.putExtra("statisticsName", "产电效益统计表");
                startActivity(intent8);
                break;

            case R.id.ll_jinliaoliang:
                Intent intent9 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent9.putExtra("title", "进料量统计");
                intent9.putExtra("statisticsName", "进料量统计表");
                startActivity(intent9);
                break;

        }
    }
}
