package com.tianren.methane.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianren.methane.R;
import com.tianren.methane.utils.ToastUtils;
/**
 * Created by sunflower on 17/12/5.
 */

public class LinkServiceActivity extends BaseActivity implements View.OnClickListener{
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private TextView tv_call;
    private TextView tv_email;
    private LinearLayout ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_service);

        initTitle();
        initView();

    }


    private void initTitle() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("客户服务");
    }
    private void initView() {
        tv_call= (TextView) findViewById(R.id.tv_call);
        tv_email= (TextView) findViewById(R.id.tv_email);
        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                     // 检查该权限是否已经获取
                     int i = ContextCompat.checkSelfPermission(LinkServiceActivity.this, permissions[0]);
                     // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                     if (i != PackageManager.PERMISSION_GRANTED) {
                             // 如果没有授予该权限，就去提示用户请求
                       ToastUtils.show("没有拨打权限");
//                             showDialogTipUserRequestPermission();
                     }
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tv_call.getText().toString()));
                startActivity(intent);

            }
        });
        tv_email.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
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
