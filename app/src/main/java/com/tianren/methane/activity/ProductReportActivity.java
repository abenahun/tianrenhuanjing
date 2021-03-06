package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.ReportBean;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.EntryService;
import com.tianren.methane.R;
import com.tianren.methane.utils.ToastUtils;

/**
 * @author Administrator
 * @date 2018/5/28
 */
public class ProductReportActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private ImageView moreIv;

    private Integer reportId;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_report);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生产报表");
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setVisibility(View.VISIBLE);
        moreIv.setImageResource(R.mipmap.edit);
        moreIv.setOnClickListener(this);
        reportId = getIntent().getIntExtra("reportId", -1);
        if (reportId == -1) {
            ToastUtils.show("出现错误");
            finish();
        }
        report_day = (TextView) findViewById(R.id.report_day);
        report_time = (TextView) findViewById(R.id.report_time);

        kitchen_enter = (TextView) findViewById(R.id.kitchen_enter);
        kitchen_deal = (TextView) findViewById(R.id.kitchen_deal);
        kitchen_finish_plan = (TextView) findViewById(R.id.kitchen_finish_plan);
        kitchen_finish_data = (TextView) findViewById(R.id.kitchen_finish_data);
        kitchen_finish_rate = (TextView) findViewById(R.id.kitchen_finish_rate);

        repast_enter = (TextView) findViewById(R.id.repast_enter);
        repast_deal = (TextView) findViewById(R.id.repast_deal);
        repast_finish_plan = (TextView) findViewById(R.id.repast_finish_plan);
        repast_finish_data = (TextView) findViewById(R.id.repast_finish_data);
        repast_finish_rate = (TextView) findViewById(R.id.repast_finish_rate);

        oil_finish_plan = (TextView) findViewById(R.id.oil_finish_plan);
        oil_finish_data = (TextView) findViewById(R.id.oil_finish_data);
        oil_finish_rate = (TextView) findViewById(R.id.oil_finish_rate);

        gas_enter1 = (TextView) findViewById(R.id.gas_enter1);
        gas_enter2 = (TextView) findViewById(R.id.gas_enter2);
        gas_day_produce = (TextView) findViewById(R.id.gas_day_produce);
        gas_finish_plan = (TextView) findViewById(R.id.gas_finish_plan);
        gas_finish_data = (TextView) findViewById(R.id.gas_finish_data);
        gas_finish_rate = (TextView) findViewById(R.id.gas_finish_rate);

        ele_product = (TextView) findViewById(R.id.ele_product);
        ele_provide = (TextView) findViewById(R.id.ele_provide);
        ele_day_use_rate = (TextView) findViewById(R.id.ele_day_use_rate);
        ele_finish_plan = (TextView) findViewById(R.id.ele_finish_plan);
        ele_finish_data = (TextView) findViewById(R.id.ele_finish_data);
        ele_finish_rate = (TextView) findViewById(R.id.ele_finish_rate);
        ele_use_factory_data = (TextView) findViewById(R.id.ele_use_factory_data);
        ele_use_net_data = (TextView) findViewById(R.id.ele_use_net_data);
        ele_plan_use_data = (TextView) findViewById(R.id.ele_plan_use_data);
        ele_net_rate = (TextView) findViewById(R.id.ele_net_rate);

        water_filtrate_product = (TextView) findViewById(R.id.water_filtrate_product);
        water_repertory = (TextView) findViewById(R.id.water_repertory);
        water_gas_product = (TextView) findViewById(R.id.water_gas_product);
        water_bad_introduce_day = (TextView) findViewById(R.id.water_bad_introduce_day);
        water_bad_introduce_plan = (TextView) findViewById(R.id.water_bad_introduce_plan);
        water_bad_introduce_month = (TextView) findViewById(R.id.water_bad_introduce_month);

        loadData();
    }

    private void loadData() {
        WebServiceManage.getService(EntryService.class).getProItemData(reportId).setCallback(new SCallBack<BaseResponse<ReportBean>>() {
            @Override
            public void callback(boolean isok, String msg, BaseResponse<ReportBean> res) {
                if (isok) {
                    if (res != null && res.getData() != null) {
                        ReportBean bean = res.getData();
                        report_day.setText(bean.getReportDays() == null ? "/" : (bean.getReportDays() + ""));
                        report_time.setText(TextUtils.isEmpty(bean.getReportTime()) ? "/" : (bean.getReportTime().split(" ")[0]));

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.moreIv:
                Intent intent = new Intent(ProductReportActivity.this, ReportEntryActivity.class);
                intent.putExtra("reportId", reportId);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
