package com.tianren.methane.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.tianren.methane.R;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;

import java.io.File;
/**
 * Created by sunflower on 17/12/5.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{

    EditText edt_content;
    private View tv_add_pic;
    private ImageView img_pic;
    String base64="";
    String filename="";
    private EditText edt_tel;
    private LinearLayout ll_back;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initTitle();
        initView();
    }

    private void initView(){
        tv_add_pic= (View) findViewById(R.id.tv_add_pic);
        img_pic= (ImageView) findViewById(R.id.img_pic);
        edt_content= (EditText) findViewById(R.id.edt_content);
        edt_tel= (EditText) findViewById(R.id.edt_tel);
    }

    private void initTitle() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("意见反馈");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
    String phone;
    private void feedback(String base64,String filename){
        if(StringUtil.isEmpty(edt_content.getText().toString())){
           ToastUtils.show("请先输入要反馈的意见");
            return;
        }


        if(StringUtil.isEmpty(edt_tel.getText().toString())){
            phone="";
        }else {
            //正则判断手机号码
            if(!StringUtil.isEmpty(edt_tel.getText().toString())){
                ToastUtils.show("请输入正确的手机号码");
                return;
            }else{
                phone=edt_tel.getText().toString();
            }

        }

    }

}
