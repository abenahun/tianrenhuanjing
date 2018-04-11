package com.tianren.methane.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianren.methane.R;

/**
 * Created by Administrator on 2018/4/2.
 */

public class DataEntryActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_yanyang,ll_chengben,ll_xiaoyi,ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataentry);
        initView();
    }

    private void initView() {

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("数据录入");

        ll_yanyang = (LinearLayout) findViewById(R.id.ll_yanyang);
        ll_chengben = (LinearLayout) findViewById(R.id.ll_chengben);
        ll_xiaoyi = (LinearLayout) findViewById(R.id.ll_xiaoyi);

        ll_yanyang.setOnClickListener(this);
        ll_chengben.setOnClickListener(this);
        ll_xiaoyi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_yanyang:
                Intent intent = new Intent( DataEntryActivity.this,EntryYanYangActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_chengben:
                Intent intent2 = new Intent( DataEntryActivity.this,EntryProductionCostActivity.class);
                startActivity(intent2);
                break;

            case R.id.ll_xiaoyi:
                Intent intent3 = new Intent( DataEntryActivity.this,EntryProductionBenefitActivity.class);
                startActivity(intent3);
                break;
        }
    }

}
