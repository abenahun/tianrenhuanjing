package com.tianren.methane.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.CapacityBean;
import com.tianren.acommon.bean.ConsumptionBean;
import com.tianren.acommon.bean.ReportBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.activity.DataStatisticsActivity;
import com.tianren.methane.utils.MPChartHelper;
import com.tianren.methane.utils.MathUtils;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;

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
    private TextView tv_handle1, tv_handle2, tv_produce1, tv_produce2;
    private SwipeRefreshLayout refreshLayout;

    //生产天数、时间
    private TextView report_day;
    private TextView report_time;
    //（厨房垃圾、餐饮垃圾）当日进厂量,当日处理量,月计划完成量,月完成量,月完成率
    private TextView kitchen_enter, kitchen_deal, kitchen_finish_plan, kitchen_finish_data, kitchen_finish_rate;
    private TextView repast_enter, repast_deal, repast_finish_plan, repast_finish_data, repast_finish_rate;
    //（提油）月计划完成量,月完成量,月完成率
    private TextView oil_finish_plan, oil_finish_data, oil_finish_rate;
    //（产沼）日进料量(1号)，(2号),当日产气量,月计划完成量,月完成量，月完成率
    private TextView gas_enter1, gas_enter2, gas_day_produce, gas_finish_plan, gas_finish_data, gas_finish_rate;
    //（发、用电）日发电量,日供电量,日站用电率,月计划完成量,月完成量,
    // 本月完成率,日厂用电量,日网用电量,月计划网用电量,网用电率
    private TextView ele_product, ele_provide, ele_day_use_rate, ele_finish_plan, ele_finish_data,
            ele_finish_rate, ele_use_factory_data, ele_use_net_data, ele_plan_use_data, ele_net_rate;
    //（污水）日滤液产生量,库存,日沼液产生量,日污水处理量,月计划污水处理量,月污水处理量;
    private TextView water_filtrate_product, water_repertory, water_gas_product, water_bad_introduce_day, water_bad_introduce_plan, water_bad_introduce_month;


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
        tv_handle1 = (TextView) view.findViewById(R.id.tv_handle1);
        tv_handle2 = (TextView) view.findViewById(R.id.tv_handle2);
        tv_produce1 = (TextView) view.findViewById(R.id.tv_produce1);
        tv_produce2 = (TextView) view.findViewById(R.id.tv_produce2);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

        report_day = (TextView) view.findViewById(R.id.report_day);
        report_time = (TextView) view.findViewById(R.id.report_time);

        kitchen_enter = (TextView) view.findViewById(R.id.kitchen_enter);
        kitchen_deal = (TextView) view.findViewById(R.id.kitchen_deal);
        kitchen_finish_plan = (TextView) view.findViewById(R.id.kitchen_finish_plan);
        kitchen_finish_data = (TextView) view.findViewById(R.id.kitchen_finish_data);
        kitchen_finish_rate = (TextView) view.findViewById(R.id.kitchen_finish_rate);

        repast_enter = (TextView) view.findViewById(R.id.repast_enter);
        repast_deal = (TextView) view.findViewById(R.id.repast_deal);
        repast_finish_plan = (TextView) view.findViewById(R.id.repast_finish_plan);
        repast_finish_data = (TextView) view.findViewById(R.id.repast_finish_data);
        repast_finish_rate = (TextView) view.findViewById(R.id.repast_finish_rate);

        oil_finish_plan = (TextView) view.findViewById(R.id.oil_finish_plan);
        oil_finish_data = (TextView) view.findViewById(R.id.oil_finish_data);
        oil_finish_rate = (TextView) view.findViewById(R.id.oil_finish_rate);

        gas_enter1 = (TextView) view.findViewById(R.id.gas_enter1);
        gas_enter2 = (TextView) view.findViewById(R.id.gas_enter2);
        gas_day_produce = (TextView) view.findViewById(R.id.gas_day_produce);
        gas_finish_plan = (TextView) view.findViewById(R.id.gas_finish_plan);
        gas_finish_data = (TextView) view.findViewById(R.id.gas_finish_data);
        gas_finish_rate = (TextView) view.findViewById(R.id.gas_finish_rate);

        ele_product = (TextView) view.findViewById(R.id.ele_product);
        ele_provide = (TextView) view.findViewById(R.id.ele_provide);
        ele_day_use_rate = (TextView) view.findViewById(R.id.ele_day_use_rate);
        ele_finish_plan = (TextView) view.findViewById(R.id.ele_finish_plan);
        ele_finish_data = (TextView) view.findViewById(R.id.ele_finish_data);
        ele_finish_rate = (TextView) view.findViewById(R.id.ele_finish_rate);
        ele_use_factory_data = (TextView) view.findViewById(R.id.ele_use_factory_data);
        ele_use_net_data = (TextView) view.findViewById(R.id.ele_use_net_data);
        ele_plan_use_data = (TextView) view.findViewById(R.id.ele_plan_use_data);
        ele_net_rate = (TextView) view.findViewById(R.id.ele_net_rate);

        water_filtrate_product = (TextView) view.findViewById(R.id.water_filtrate_product);
        water_repertory = (TextView) view.findViewById(R.id.water_repertory);
        water_gas_product = (TextView) view.findViewById(R.id.water_gas_product);
        water_bad_introduce_day = (TextView) view.findViewById(R.id.water_bad_introduce_day);
        water_bad_introduce_plan = (TextView) view.findViewById(R.id.water_bad_introduce_plan);
        water_bad_introduce_month = (TextView) view.findViewById(R.id.water_bad_introduce_month);

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
                            airEarnings.setText((gasProduction.equals(0) ? "0" : (MathUtils.scale(gasProduction) + "")) + "元");
                            Double powerGeneration = res.getData().get(0).getPowerGeneration();
                            eleEarnings.setText((powerGeneration.equals(0) ? "0" : (MathUtils.scale(powerGeneration) + "")) + "元");
                            allEarnings.setText(MathUtils.scale((gasProduction + powerGeneration + 3500)) + "元");
                        }
                    }
                });
        WebServiceManage.getService(EntryService.class).getConsumptionData().
                setCallback(new SCallBack<BaseResponse<List<ConsumptionBean>>>() {
                    @Override
                    public void callback(boolean isok, String msg, BaseResponse<List<ConsumptionBean>> res) {
                        if (isok) {
                            Double waterConsumption = res.getData().get(0).getWaterConsumption();
                            waterConsume.setText(waterConsumption.equals(0) ? "0" : (MathUtils.scale2(waterConsumption) + "") + "吨");
                            Double powerConsumption = res.getData().get(0).getPowerConsumption();
                            eleConsume.setText(powerConsumption.equals(0) ? "0" : (MathUtils.scale2(powerConsumption) + "") + "kw");
                            Double airConsumption = res.getData().get(0).getAirConsumption();
//                            hotConsume.setText(airConsumption.equals(0) ? "0" : (MathUtils.scale2(airConsumption) + "") + "m³");
                            allConsume.setText(MathUtils.scale2((waterConsumption + powerConsumption + airConsumption)) + "元");
                        }
                        refreshLayout.setRefreshing(false);
                    }
                });
        WebServiceManage.getService(EntryService.class).getProItemData(-1).setCallback(new SCallBack<BaseResponse<ReportBean>>() {
            @Override
            public void callback(boolean isok, String msg, BaseResponse<ReportBean> res) {
                if (isok) {
                    if (res != null && res.getData() != null) {
                        ReportBean bean = res.getData();
                        report_day.setText(bean.getReportDays() == null ? "/" : (bean.getReportDays() + ""));
                        report_time.setText(TextUtils.isEmpty(bean.getReportTime()) ? "/" : bean.getReportTime());

                        kitchen_enter.setText(bean.getKitchenEnter() == null ? "/" : (bean.getKitchenEnter() + ""));
                        kitchen_deal.setText(bean.getKitchenDeal() == null ? "/" : (bean.getKitchenDeal() + ""));
                        kitchen_finish_plan.setText(bean.getKitchenPlan() == null ? "/" : (bean.getKitchenPlan() + ""));
                        kitchen_finish_data.setText(bean.getKitchenFinish() == null ? "/" : (bean.getKitchenFinish() + ""));
                        kitchen_finish_rate.setText(bean.getKitchenRate() == null ? "/" : (bean.getKitchenRate() + ""));

                        repast_enter.setText(bean.getRepastEnter() == null ? "/" : (bean.getRepastEnter() + ""));
                        repast_deal.setText(bean.getRepastDeal() == null ? "/" : (bean.getRepastDeal() + ""));
                        repast_finish_plan.setText(bean.getRepastPlan() == null ? "/" : (bean.getRepastPlan() + ""));
                        repast_finish_data.setText(bean.getRepastFinish() == null ? "/" : (bean.getRepastFinish() + ""));
                        repast_finish_rate.setText(bean.getRepastRate() == null ? "/" : (bean.getRepastRate() + ""));

                        oil_finish_plan.setText(bean.getOilPlan() == null ? "/" : (bean.getOilPlan() + ""));
                        oil_finish_data.setText(bean.getOilFinish() == null ? "/" : (bean.getOilFinish() + ""));
                        oil_finish_rate.setText(bean.getOilRate() == null ? "/" : (bean.getOilRate() + ""));

                        gas_enter1.setText(bean.getGasEnter1() == null ? "/" : (bean.getGasEnter1() + ""));
                        gas_enter2.setText(bean.getGasEnter2() == null ? "/" : (bean.getGasEnter2() + ""));
                        gas_day_produce.setText(bean.getGasDayProduce() == null ? "/" : (bean.getGasDayProduce() + ""));
                        gas_finish_plan.setText(bean.getGasPlan() == null ? "/" : (bean.getGasPlan() + ""));
                        gas_finish_data.setText(bean.getGasFinish() == null ? "/" : (bean.getGasFinish() + ""));
                        gas_finish_rate.setText(bean.getGasRate() == null ? "/" : (bean.getGasRate() + ""));

                        ele_product.setText(bean.getEleProduct() == null ? "/" : (bean.getEleProduct() + ""));
                        ele_provide.setText(bean.getEleProvider() == null ? "/" : (bean.getEleProvider() + ""));
                        ele_day_use_rate.setText(bean.getEleDayUseRate() == null ? "/" : (bean.getEleDayUseRate() + ""));
                        ele_finish_plan.setText(bean.getElePlan() == null ? "/" : (bean.getElePlan() + ""));
                        ele_finish_data.setText(bean.getEleFinish() == null ? "/" : (bean.getEleFinish() + ""));
                        ele_finish_rate.setText(bean.getEleDayRate() == null ? "/" : (bean.getEleDayRate() + ""));
                        ele_use_factory_data.setText(bean.getEleUseFactoryData() == null ? "/" : (bean.getEleUseFactoryData() + ""));
                        ele_use_net_data.setText(bean.getEleUseNetData() == null ? "/" : (bean.getEleUseNetData() + ""));
                        ele_plan_use_data.setText(bean.getElePlanUseData() == null ? "/" : (bean.getElePlanUseData() + ""));
                        ele_net_rate.setText(bean.getEleNetRate() == null ? "/" : (bean.getEleNetRate() + ""));

                        water_filtrate_product.setText(bean.getWaterFiltrateProduct() == null ? "/" : (bean.getWaterFiltrateProduct() + ""));
                        water_repertory.setText(bean.getWaterRepertory() == null ? "/" : (bean.getWaterRepertory() + ""));
                        water_gas_product.setText(bean.getWaterGasProduct() == null ? "/" : (bean.getWaterGasProduct() + ""));
                        water_bad_introduce_day.setText(bean.getWaterBadIntroduceDay() == null ? "/" : (bean.getWaterBadIntroduceDay() + ""));
                        water_bad_introduce_plan.setText(bean.getWaterBadIntroducePlan() == null ? "/" : (bean.getWaterBadIntroducePlan() + ""));
                        water_bad_introduce_month.setText(bean.getWaterBadIntroduceMonth() == null ? "/" : (bean.getWaterBadIntroduceMonth() + ""));
                    }
                } else {
                    ToastUtils.show(msg);
                }
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

        MPChartHelper.setTwoBarChart(barChart1, xAxisValues, yAxisValues1, yAxisValues2, "总收益", "总能耗");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_runningstatus:
                Intent intent = new Intent(getActivity(), DataStatisticsActivity.class);
                intent.putExtra("title", "提油量统计");
                intent.putExtra("statisticsName", "提油量统计表");
                intent.putExtra("tableName", "Capacity");
                intent.putExtra("columnName", StringUtil.humpToLine2("liftingCapacity"));
                startActivity(intent);
                break;

            case R.id.ll_zongnenghao:
//                Intent intent2 = new Intent(getActivity(), DataStatisticsActivity.class);
//                intent2.putExtra("title", "总能耗统计");
//                intent2.putExtra("statisticsName", "总能耗统计表");
//                startActivity(intent2);
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
                intent4.putExtra("tableName", "Consumption");
                intent4.putExtra("columnName", StringUtil.humpToLine2("powerConsumption"));
                startActivity(intent4);
                break;

            case R.id.ll_rehao:
                Intent intent5 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent5.putExtra("title", "热耗统计");
                intent5.putExtra("statisticsName", "热耗统计表");
                intent5.putExtra("tableName", "Consumption");
                intent5.putExtra("columnName", StringUtil.humpToLine2("airConsumption"));
                startActivity(intent5);

                break;

            case R.id.ll_chanqixiaoyi:
                Intent intent6 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent6.putExtra("title", "产气效益统计");
                intent6.putExtra("statisticsName", "产气效益统计表");
                intent6.putExtra("tableName", "Capacity");
                intent6.putExtra("columnName", StringUtil.humpToLine2("gasProduction"));
                startActivity(intent6);
                break;

            case R.id.ll_zongxiaoyi:
//                Intent intent7 = new Intent(getActivity(), DataStatisticsActivity.class);
//                intent7.putExtra("title", "总效益统计");
//                intent7.putExtra("statisticsName", "总效益统计表");
//                startActivity(intent7);
                break;

            case R.id.ll_chandianxiaoyi:
                Intent intent8 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent8.putExtra("title", "产电效益统计");
                intent8.putExtra("statisticsName", "产电效益统计表");
                intent8.putExtra("tableName", "Capacity");
                intent8.putExtra("columnName", StringUtil.humpToLine2("powerGeneration"));
                startActivity(intent8);
                break;

            case R.id.ll_jinliaoliang:
                Intent intent9 = new Intent(getActivity(), DataStatisticsActivity.class);
                intent9.putExtra("title", "进料量统计");
                intent9.putExtra("statisticsName", "进料量统计表");
                intent9.putExtra("tableName", "Consumption");
                intent9.putExtra("columnName", StringUtil.humpToLine2("feedAmount"));
                startActivity(intent9);
                break;
            default:
                break;
        }
    }
}
