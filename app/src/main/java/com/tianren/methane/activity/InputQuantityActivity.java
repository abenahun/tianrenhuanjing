package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianren.methane.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author qiushengtao
 */
public class InputQuantityActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title, tv_time;
    private ImageView moreIv;
    private LinearLayout ll_back;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quantity);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("进料量录入");
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        moreIv = (ImageView) findViewById(R.id.moreIv);
        moreIv.setVisibility(View.VISIBLE);
        moreIv.setImageResource(R.mipmap.history);
        moreIv.setOnClickListener(this);
        initDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
                break;
            case R.id.moreIv:
                Intent intent = new Intent(this, InputQuantityHisActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_time.setText(now.toString());
    }
}
