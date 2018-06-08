package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;

/**
 * Created by Administrator on 2018/4/2.
 */

public class DataEntryActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_yanyang, ll_chengben, ll_xiaoyi, ll_back, ll_enter, ll_statement, ll_hydrolysis;
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
        ll_enter = (LinearLayout) findViewById(R.id.ll_enter);
        ll_statement = (LinearLayout) findViewById(R.id.ll_statement);
        ll_hydrolysis = (LinearLayout) findViewById(R.id.ll_hydrolysis);

        ll_yanyang.setOnClickListener(this);
        ll_chengben.setOnClickListener(this);
        ll_xiaoyi.setOnClickListener(this);
        ll_enter.setOnClickListener(this);
        ll_statement.setOnClickListener(this);
        ll_hydrolysis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_yanyang:
                Intent intent = new Intent(DataEntryActivity.this, EntryYanYangActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_chengben:
                Intent intent2 = new Intent(DataEntryActivity.this, EntryProductionCostActivity.class);
                startActivity(intent2);
                break;

            case R.id.ll_xiaoyi:
                Intent intent3 = new Intent(DataEntryActivity.this, EntryProductionBenefitActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_enter:
                Intent intent4 = new Intent(DataEntryActivity.this, InputQuantityActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_statement:
                Intent intent5 = new Intent(DataEntryActivity.this, ReportEntryActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_hydrolysis:
                Intent intent6 = new Intent(DataEntryActivity.this, HydrolysisEntryActivity.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }

}
