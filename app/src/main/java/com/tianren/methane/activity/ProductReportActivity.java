package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.tianren.methane.R;

/**
 * Created by Administrator on 2018/5/28.
 */

public class ProductReportActivity  extends BaseActivity implements View.OnClickListener{

    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_report);
    }

    @Override
    public void onClick(View v) {

    }
}
