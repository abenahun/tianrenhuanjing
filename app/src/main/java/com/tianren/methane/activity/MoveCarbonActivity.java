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
 * 脱碳
 */
public class MoveCarbonActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView yeweiTV,//液位计
            electTv,//电磁流量计
            hotWaterTv1, hotWaterTv2,//热水回流温度
            tempTv1, tempTv2, tempTv3, tempTv4, tempTv5, tempTv6;//温度计

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_carbon);
        initView();
        loadData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("脱碳");
        yeweiTV = (TextView) findViewById(R.id.yeweiTV);
        electTv = (TextView) findViewById(R.id.electTv);
        hotWaterTv1 = (TextView) findViewById(R.id.hotWaterTv1);
        hotWaterTv2 = (TextView) findViewById(R.id.hotWaterTv2);
        tempTv1 = (TextView) findViewById(R.id.tempTv1);
        tempTv2 = (TextView) findViewById(R.id.tempTv2);
        tempTv3 = (TextView) findViewById(R.id.tempTv3);
        tempTv4 = (TextView) findViewById(R.id.tempTv4);
        tempTv5 = (TextView) findViewById(R.id.tempTv5);
        tempTv6 = (TextView) findViewById(R.id.tempTv6);
    }

    private void loadData() {
        yeweiTV.setText((TextUtils.isEmpty(modelMap.get("d10")) ? "" : modelMap.get("d10")) + " m");
        tempTv1.setText((TextUtils.isEmpty(modelMap.get("d11")) ? "" : modelMap.get("d11")) + " ℃");
        tempTv2.setText((TextUtils.isEmpty(modelMap.get("d12")) ? "" : modelMap.get("d12")) + " ℃");
        tempTv3.setText((TextUtils.isEmpty(modelMap.get("d13")) ? "" : modelMap.get("d13")) + " ℃");
        tempTv4.setText((TextUtils.isEmpty(modelMap.get("d14")) ? "" : modelMap.get("d14")) + " ℃");
        tempTv5.setText((TextUtils.isEmpty(modelMap.get("d15")) ? "" : modelMap.get("d15")) + " ℃");
        tempTv6.setText((TextUtils.isEmpty(modelMap.get("d16")) ? "" : modelMap.get("d16")) + " ℃");
        hotWaterTv1.setText((TextUtils.isEmpty(modelMap.get("d17")) ? "" : modelMap.get("d17")) + " ℃");
        hotWaterTv2.setText((TextUtils.isEmpty(modelMap.get("d18")) ? "" : modelMap.get("d18")) + " ℃");
        electTv.setText((TextUtils.isEmpty(modelMap.get("d19")) ? "" : modelMap.get("d19")) + " A");
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
