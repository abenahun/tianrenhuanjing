package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.ReportBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.event.ReportEvent;
import com.tianren.methane.utils.DateUtil;
import com.tianren.methane.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author qiushengtao
 */
public class ReportEntryActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title, moreTv;
    private int reportId = -1;
    //生产天数、时间
    private EditText report_day;
    private TextView report_time;
    //（厨房垃圾、餐饮垃圾）当日进厂量,当日处理量,月计划完成量,月完成量,月完成率
    private EditText kitchen_enter, kitchen_deal, kitchen_finish_plan, kitchen_finish_data, kitchen_finish_rate;
    private EditText repast_enter, repast_deal, repast_finish_plan, repast_finish_data, repast_finish_rate;
    //（提油）月计划完成量,月完成量,月完成率
    private EditText oil_finish_plan, oil_finish_data, oil_finish_rate;
    //（产沼）日进料量(1号)，(2号),当日产气量,月计划完成量,月完成量，月完成率
    private EditText gas_enter1, gas_enter2, gas_day_produce, gas_finish_plan, gas_finish_data, gas_finish_rate;
    //（发、用电）日发电量,日供电量,日站用电率,月计划完成量,月完成量,
    // 本月完成率,日厂用电量,日网用电量,月计划网用电量,网用电率
    private EditText ele_product, ele_provide, ele_day_use_rate, ele_finish_plan, ele_finish_data,
            ele_finish_rate, ele_use_factory_data, ele_use_net_data, ele_plan_use_data, ele_net_rate;
    //（污水）日滤液产生量,库存,日沼液产生量,日污水处理量,月计划污水处理量,月污水处理量;
    private EditText water_filtrate_product, water_repertory, water_gas_product, water_bad_introduce_day, water_bad_introduce_plan, water_bad_introduce_month;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_entry);
        reportId = getIntent().getIntExtra("reportId", -1);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (reportId == -1) {
            tv_title.setText("生产报表录入");
        } else {
            tv_title.setText("生产报表修改");
        }
        moreTv = (TextView) findViewById(R.id.moreTv);
        moreTv.setVisibility(View.VISIBLE);
        moreTv.setText("保存");
        moreTv.setOnClickListener(this);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_MONTH, -1);
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        report_day = (EditText) findViewById(R.id.report_day);
        report_time = (TextView) findViewById(R.id.report_time);

        kitchen_enter = (EditText) findViewById(R.id.kitchen_enter);
        kitchen_deal = (EditText) findViewById(R.id.kitchen_deal);
        kitchen_finish_plan = (EditText) findViewById(R.id.kitchen_finish_plan);
        kitchen_finish_data = (EditText) findViewById(R.id.kitchen_finish_data);
        kitchen_finish_rate = (EditText) findViewById(R.id.kitchen_finish_rate);

        repast_enter = (EditText) findViewById(R.id.repast_enter);
        repast_deal = (EditText) findViewById(R.id.repast_deal);
        repast_finish_plan = (EditText) findViewById(R.id.repast_finish_plan);
        repast_finish_data = (EditText) findViewById(R.id.repast_finish_data);
        repast_finish_rate = (EditText) findViewById(R.id.repast_finish_rate);

        oil_finish_plan = (EditText) findViewById(R.id.oil_finish_plan);
        oil_finish_data = (EditText) findViewById(R.id.oil_finish_data);
        oil_finish_rate = (EditText) findViewById(R.id.oil_finish_rate);

        gas_enter1 = (EditText) findViewById(R.id.gas_enter1);
        gas_enter2 = (EditText) findViewById(R.id.gas_enter2);
        gas_day_produce = (EditText) findViewById(R.id.gas_day_produce);
        gas_finish_plan = (EditText) findViewById(R.id.gas_finish_plan);
        gas_finish_data = (EditText) findViewById(R.id.gas_finish_data);
        gas_finish_rate = (EditText) findViewById(R.id.gas_finish_rate);

        ele_product = (EditText) findViewById(R.id.ele_product);
        ele_provide = (EditText) findViewById(R.id.ele_provide);
        ele_day_use_rate = (EditText) findViewById(R.id.ele_day_use_rate);
        ele_finish_plan = (EditText) findViewById(R.id.ele_finish_plan);
        ele_finish_data = (EditText) findViewById(R.id.ele_finish_data);
        ele_finish_rate = (EditText) findViewById(R.id.ele_finish_rate);
        ele_use_factory_data = (EditText) findViewById(R.id.ele_use_factory_data);
        ele_use_net_data = (EditText) findViewById(R.id.ele_use_net_data);
        ele_plan_use_data = (EditText) findViewById(R.id.ele_plan_use_data);
        ele_net_rate = (EditText) findViewById(R.id.ele_net_rate);

        water_filtrate_product = (EditText) findViewById(R.id.water_filtrate_product);
        water_repertory = (EditText) findViewById(R.id.water_repertory);
        water_gas_product = (EditText) findViewById(R.id.water_gas_product);
        water_bad_introduce_day = (EditText) findViewById(R.id.water_bad_introduce_day);
        water_bad_introduce_plan = (EditText) findViewById(R.id.water_bad_introduce_plan);
        water_bad_introduce_month = (EditText) findViewById(R.id.water_bad_introduce_month);

        report_time.setOnClickListener(this);
        report_time.setText(DateUtil.format("yyyy-MM-dd", ca.getTime()));
        loadData();
    }

    private void loadData() {
        if (reportId != -1) {
            WebServiceManage.getService(EntryService.class).getProItemData(reportId).setCallback(new SCallBack<BaseResponse<ReportBean>>() {
                @Override
                public void callback(boolean isok, String msg, BaseResponse<ReportBean> res) {
                    if (isok) {
                        if (res != null && res.getData() != null) {
                            ReportBean bean = res.getData();
                            report_day.setText(bean.getReportDays() == null ? "" : (bean.getReportDays() + ""));
                            report_time.setText(TextUtils.isEmpty(bean.getReportTime()) ? "" : (bean.getReportTime().split(" ")[0]));

                            kitchen_enter.setText(bean.getKitchenEnter() == null ? "" : (bean.getKitchenEnter() + ""));
                            kitchen_deal.setText(bean.getKitchenDeal() == null ? "" : (bean.getKitchenDeal() + ""));
                            kitchen_finish_plan.setText(bean.getKitchenPlan() == null ? "" : (bean.getKitchenPlan() + ""));
                            kitchen_finish_data.setText(bean.getKitchenFinish() == null ? "" : (bean.getKitchenFinish() + ""));
                            kitchen_finish_rate.setText(bean.getKitchenRate() == null ? "" : (bean.getKitchenRate() + ""));

                            repast_enter.setText(bean.getRepastEnter() == null ? "" : (bean.getRepastEnter() + ""));
                            repast_deal.setText(bean.getRepastDeal() == null ? "" : (bean.getRepastDeal() + ""));
                            repast_finish_plan.setText(bean.getRepastPlan() == null ? "" : (bean.getRepastPlan() + ""));
                            repast_finish_data.setText(bean.getRepastFinish() == null ? "" : (bean.getRepastFinish() + ""));
                            repast_finish_rate.setText(bean.getRepastRate() == null ? "" : (bean.getRepastRate() + ""));

                            oil_finish_plan.setText(bean.getOilPlan() == null ? "" : (bean.getOilPlan() + ""));
                            oil_finish_data.setText(bean.getOilFinish() == null ? "" : (bean.getOilFinish() + ""));
                            oil_finish_rate.setText(bean.getOilRate() == null ? "" : (bean.getOilRate() + ""));

                            gas_enter1.setText(bean.getGasEnter1() == null ? "" : (bean.getGasEnter1() + ""));
                            gas_enter2.setText(bean.getGasEnter2() == null ? "" : (bean.getGasEnter2() + ""));
                            gas_day_produce.setText(bean.getGasDayProduce() == null ? "" : (bean.getGasDayProduce() + ""));
                            gas_finish_plan.setText(bean.getGasPlan() == null ? "" : (bean.getGasPlan() + ""));
                            gas_finish_data.setText(bean.getGasFinish() == null ? "" : (bean.getGasFinish() + ""));
                            gas_finish_rate.setText(bean.getGasRate() == null ? "" : (bean.getGasRate() + ""));

                            ele_product.setText(bean.getEleProduct() == null ? "" : (bean.getEleProduct() + ""));
                            ele_provide.setText(bean.getEleProvider() == null ? "" : (bean.getEleProvider() + ""));
                            ele_day_use_rate.setText(bean.getEleDayUseRate() == null ? "" : (bean.getEleDayUseRate() + ""));
                            ele_finish_plan.setText(bean.getElePlan() == null ? "" : (bean.getElePlan() + ""));
                            ele_finish_data.setText(bean.getEleFinish() == null ? "" : (bean.getEleFinish() + ""));
                            ele_finish_rate.setText(bean.getEleDayRate() == null ? "" : (bean.getEleDayRate() + ""));
                            ele_use_factory_data.setText(bean.getEleUseFactoryData() == null ? "" : (bean.getEleUseFactoryData() + ""));
                            ele_use_net_data.setText(bean.getEleUseNetData() == null ? "" : (bean.getEleUseNetData() + ""));
                            ele_plan_use_data.setText(bean.getElePlanUseData() == null ? "" : (bean.getElePlanUseData() + ""));
                            ele_net_rate.setText(bean.getEleNetRate() == null ? "" : (bean.getEleNetRate() + ""));

                            water_filtrate_product.setText(bean.getWaterFiltrateProduct() == null ? "" : (bean.getWaterFiltrateProduct() + ""));
                            water_repertory.setText(bean.getWaterRepertory() == null ? "" : (bean.getWaterRepertory() + ""));
                            water_gas_product.setText(bean.getWaterGasProduct() == null ? "" : (bean.getWaterGasProduct() + ""));
                            water_bad_introduce_day.setText(bean.getWaterBadIntroduceDay() == null ? "" : (bean.getWaterBadIntroduceDay() + ""));
                            water_bad_introduce_plan.setText(bean.getWaterBadIntroducePlan() == null ? "" : (bean.getWaterBadIntroducePlan() + ""));
                            water_bad_introduce_month.setText(bean.getWaterBadIntroduceMonth() == null ? "" : (bean.getWaterBadIntroduceMonth() + ""));
                        }
                    } else {
                        ToastUtils.show(msg);
                    }
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
            case R.id.moreTv:
                commit();
                break;
            case R.id.report_time:
                final AlertDialog dialog3 = new AlertDialog.Builder(this).setView(R.layout.dialog_datepicker).show();
                ((TextView) dialog3.findViewById(R.id.tv_title)).setText("日历");
                Window window = dialog3.getWindow();
                window.setGravity(Gravity.CENTER);
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                final TextView tv_date_tmp = (TextView) dialog3.findViewById(R.id.tv_date_tmp);
                DatePicker datePicker = (DatePicker) dialog3.findViewById(R.id.dp_date);
                datePicker.setVisibility(View.VISIBLE);
                datePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        // 获得日历实例
                        String getDate = convertDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        tv_date_tmp.setText(getDate);
                    }
                });
                dialog3.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog3.dismiss();
                    }
                });
                dialog3.findViewById(R.id.iv_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = tv_date_tmp.getText().toString();
                        report_time.setText(TextUtils.isEmpty(text) ? convertDate(mYear, mMonth, mDay) : text);
                        dialog3.dismiss();
                    }
                });
                dialog3.findViewById(R.id.tp_time).setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 报表保存并提交
     */
    private void commit() {
        String reportDays = report_day.getText().toString();
        String reportTime = report_time.getText().toString();
        String kitchenEnter = kitchen_enter.getText().toString();
        String kitchenDeal = kitchen_deal.getText().toString();
        String kitchenPlan = kitchen_finish_plan.getText().toString();
        String kitchenFinish = kitchen_finish_data.getText().toString();
        String kitchenRate = kitchen_finish_rate.getText().toString();

        String repastEnter = repast_enter.getText().toString();
        String repastDeal = repast_deal.getText().toString();
        String repastPlan = repast_finish_plan.getText().toString();
        String repastFinish = repast_finish_data.getText().toString();
        String repastRate = repast_finish_rate.getText().toString();

        String oilPlan = oil_finish_plan.getText().toString();
        String oilFinish = oil_finish_data.getText().toString();
        String oilRate = oil_finish_rate.getText().toString();

        String gasEnter1 = gas_enter1.getText().toString();
        String gasEnter2 = gas_enter2.getText().toString();
        String gasDayProduce = gas_day_produce.getText().toString();
        String gasPlan = gas_finish_plan.getText().toString();
        String gasFinish = gas_finish_data.getText().toString();
        String gasRate = gas_finish_rate.getText().toString();

        String eleProduct = ele_product.getText().toString();
        String eleProvider = ele_provide.getText().toString();
        String eleDayUseRate = ele_day_use_rate.getText().toString();
        String elePlan = ele_finish_plan.getText().toString();
        String eleFinish = ele_finish_data.getText().toString();
        String eleDayRate = ele_finish_rate.getText().toString();
        String eleUseFactoryData = ele_use_factory_data.getText().toString();
        String eleUseNetData = ele_use_net_data.getText().toString();
        String elePlanUseData = ele_plan_use_data.getText().toString();
        String eleNetRate = ele_net_rate.getText().toString();

        String waterFiltrateProduct = water_filtrate_product.getText().toString();
        String waterRepertory = water_repertory.getText().toString();
        String waterGasProduct = water_gas_product.getText().toString();
        String waterBadIntroduceDay = water_bad_introduce_day.getText().toString();
        String waterBadIntroducePlan = water_bad_introduce_plan.getText().toString();
        String waterBadIntroduceMonth = water_bad_introduce_month.getText().toString();
        ReportBean bean = new ReportBean();
        bean.setReportDays(TextUtils.isEmpty(reportDays) ? null : Integer.parseInt(reportDays));
        bean.setReportTime(reportTime);
        bean.setKitchenEnter(getD(kitchenEnter));
        bean.setKitchenDeal(getD(kitchenDeal));
        bean.setKitchenPlan(getD(kitchenPlan));
        bean.setKitchenFinish(getD(kitchenFinish));
        bean.setKitchenRate(getD(kitchenRate));

        bean.setRepastEnter(getD(repastEnter));
        bean.setRepastDeal(getD(repastDeal));
        bean.setRepastPlan(getD(repastPlan));
        bean.setRepastFinish(getD(repastFinish));
        bean.setRepastRate(getD(repastRate));

        bean.setOilPlan(getD(oilPlan));
        bean.setOilFinish(getD(oilFinish));
        bean.setOilRate(getD(oilRate));

        bean.setGasEnter1(getD(gasEnter1));
        bean.setGasEnter2(getD(gasEnter2));
        bean.setGasDayProduce(getD(gasDayProduce));
        bean.setGasFinish(getD(gasFinish));
        bean.setGasPlan(getD(gasPlan));
        bean.setGasRate(getD(gasRate));

        bean.setEleProduct(getD(eleProduct));
        bean.setEleProvider(getD(eleProvider));
        bean.setEleDayUseRate(getD(eleDayUseRate));
        bean.setElePlan(getD(elePlan));
        bean.setEleFinish(getD(eleFinish));
        bean.setEleDayRate(getD(eleDayRate));
        bean.setEleUseFactoryData(getD(eleUseFactoryData));
        bean.setEleUseNetData(getD(eleUseNetData));
        bean.setElePlanUseData(getD(elePlanUseData));
        bean.setEleNetRate(getD(eleNetRate));

        bean.setWaterFiltrateProduct(getD(waterFiltrateProduct));
        bean.setWaterRepertory(getD(waterRepertory));
        bean.setWaterGasProduct(getD(waterGasProduct));
        bean.setWaterBadIntroduceDay(getD(waterBadIntroduceDay));
        bean.setWaterBadIntroducePlan(getD(waterBadIntroducePlan));
        bean.setWaterBadIntroduceMonth(getD(waterBadIntroduceMonth));
        if (reportId == -1) {
            String s = new Gson().toJson(bean);
            WebServiceManage.getService(EntryService.class).entryProData(s).setCallback(new SCallBack<BaseResponse<Boolean>>() {
                @Override
                public void callback(boolean isok, String msg, BaseResponse<Boolean> res) {
                    ToastUtils.show(msg);
                    if (isok && res.getData()) {
                        EventBus.getDefault().post(new ReportEvent());
                        finish();
                    }
                }
            });
        } else {
            bean.setReportId(reportId);
            String s = new Gson().toJson(bean);
            WebServiceManage.getService(EntryService.class).updateProData(s).setCallback(new SCallBack<BaseResponse<Boolean>>() {
                @Override
                public void callback(boolean isok, String msg, BaseResponse<Boolean> res) {
                    ToastUtils.show(msg);
                    if (isok && res.getData()) {
                        EventBus.getDefault().post(new ReportEvent());
                        finish();
                    }
                }
            });
        }
    }

    /**
     * 日期转换
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String convertDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    private Double getD(String s) {
        if (TextUtils.isEmpty(s.trim())) {
            return null;
        } else {
            return Double.parseDouble(s);
        }
    }
}
