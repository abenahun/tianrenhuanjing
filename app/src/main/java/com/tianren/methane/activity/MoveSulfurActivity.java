package com.tianren.methane.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;

import static com.tianren.methane.activity.MainActivity.modelMap;

/**
 * Created by Qiu on 2018/4/10.
 * 脱硫
 */
public class MoveSulfurActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView h2sTv,//出口净化气浓度
            carWaterTemTv,//循环液温度
            methTv,//沼气流量
            conWaterPhTv,//循环液PH
            airTv,//空气流量
            conWaterTv,//循环液流量
            topTv,//塔顶压力
            taCenTv,//塔内液位
            chiCenTv;//池内液位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_sulfur);
        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("脱硫");

        h2sTv = (TextView) findViewById(R.id.h2sTv);
        carWaterTemTv = (TextView) findViewById(R.id.carWaterTemTv);
        methTv = (TextView) findViewById(R.id.methTv);
        conWaterPhTv = (TextView) findViewById(R.id.conWaterPhTv);
        airTv = (TextView) findViewById(R.id.airTv);
        conWaterTv = (TextView) findViewById(R.id.conWaterTv);
        topTv = (TextView) findViewById(R.id.topTv);
        taCenTv = (TextView) findViewById(R.id.taCenTv);
        chiCenTv = (TextView) findViewById(R.id.chiCenTv);
    }

    private void loadData() {
        h2sTv.setText((TextUtils.isEmpty(modelMap.get("d1")) ? "" : modelMap.get("d1")) + "ppm");
        carWaterTemTv.setText((TextUtils.isEmpty(modelMap.get("d2")) ? "" : modelMap.get("d2")) + "℃");
        methTv.setText((TextUtils.isEmpty(modelMap.get("d3")) ? "" : modelMap.get("d3")) + "m³");
        conWaterPhTv.setText((TextUtils.isEmpty(modelMap.get("d4")) ? "" : modelMap.get("d4")) + "PH");
        airTv.setText((TextUtils.isEmpty(modelMap.get("d5")) ? "" : modelMap.get("d5")) + "m³");
        conWaterTv.setText((TextUtils.isEmpty(modelMap.get("d6")) ? "" : modelMap.get("d6")) + "m³");
        topTv.setText((TextUtils.isEmpty(modelMap.get("d7")) ? "" : modelMap.get("d7")) + "kPa");
        taCenTv.setText((TextUtils.isEmpty(modelMap.get("d8")) ? "" : modelMap.get("d8")) + "m");
        chiCenTv.setText((TextUtils.isEmpty(modelMap.get("d9")) ? "" : modelMap.get("d9")) + "m");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
