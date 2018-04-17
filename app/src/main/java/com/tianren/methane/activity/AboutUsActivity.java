package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianren.methane.R;

/**
 * Created by sunflower on 17/12/5.
 */

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    EditText edt_content;
    TextView tv_version;
    TextView dangyuan_agreement;
    TextView dangyuan_agreement2;
    private LinearLayout ll_back;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initView();
        initTitle();
        initData();
    }


    private void initTitle() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("关于我们");
    }
    private void initView() {
        tv_version= (TextView) findViewById(R.id.tv_version);
        dangyuan_agreement = (TextView) findViewById(R.id.dangyuan_agreement);
        dangyuan_agreement2 = (TextView) findViewById(R.id.dangyuan_agreement2);
        dangyuan_agreement.setOnClickListener(this);
        dangyuan_agreement2.setOnClickListener(this);
    }
    private void initData() {
        tv_version.setText("智能沼气工程");
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
