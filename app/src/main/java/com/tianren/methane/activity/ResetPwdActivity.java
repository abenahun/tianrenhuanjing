package com.tianren.methane.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianren.methane.R;

/**
 * 重置密码
 */

public class  ResetPwdActivity extends BaseActivity implements View.OnClickListener{
    TextView tv_mobile;
    EditText edt_old_pwd;
    EditText edt_new_pwd;
    EditText edt_repeat_pwd;
    TextView bt_commit;
    private LinearLayout ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        initData();
        initTitle();
        initViews();
    }

    private void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTitle() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
    }

    private void initViews() {
        edt_old_pwd = (EditText) findViewById(R.id.edt_old_pwd);
        edt_new_pwd = (EditText) findViewById(R.id.edt_new_pwd);
        edt_repeat_pwd = (EditText) findViewById(R.id.edt_repeat_pwd);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);

        bt_commit = (TextView) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(this);

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
