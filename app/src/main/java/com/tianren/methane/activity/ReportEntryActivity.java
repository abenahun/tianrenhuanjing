package com.tianren.methane.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;

/**
 * @author qiushengtao
 */
public class ReportEntryActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title, moreTv;
    //生产天数、时间
    private EditText report_day;
    private TextView report_time;
    //（厨房垃圾、餐饮垃圾）当日进厂量,当日处理量,月计划完成量,月完成量,月完成率
    private EditText kitchen_enter, kitchen_deal, kitchen_finish_plan, kitchen_finish_data, kitchen_finish_rate;
    private EditText repast_enter, repast_deal, repast_finish_plan, repast_finish_data, repast_finish_rate;
    //（提油）月计划完成量,月完成量,月完成率
    private EditText oil_finish_plan, oil_finish_data, oil_finish_rate;
    //（产沼）日进料量(1号)，(2号),月计划完成量,月完成量，月完成率
    private EditText gas_enter1, gas_enter2, gas_finish_plan, gas_finish_data, gas_finish_rate;
    //（发、用电）日发电量,日供电量,日站用电率,月计划完成量,月完成量,
    // 本月完成率,日厂用电量,日网用电量,月计划网用电量,网用电率
    private EditText ele_product, ele_provide, ele_day_use_rate, ele_finish_plan, ele_finish_data,
            ele_finish_rate, ele_use_factory_data, ele_use_net_data, ele_plan_use_data, ele_net_rate;
    //（污水）日滤液产生量,库存,日沼液产生量,日污水处理量,月计划污水处理量,月污水处理量;
    private EditText water_filtrate_product, water_repertory, water_gas_product, water_bad_introduce_day, water_bad_introduce_plan, water_bad_introduce_month;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_entry);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生产报表录入");
        moreTv = (TextView) findViewById(R.id.moreTv);
        moreTv.setVisibility(View.VISIBLE);
        moreTv.setText("保存");
        moreTv.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.moreTv:

                break;
            default:
                break;
        }
    }
}
