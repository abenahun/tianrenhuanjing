package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.bean.AnaerobicTankBean;
import com.tianren.methane.bean.CapacityBean;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/2.
 */

public class EntryProductionBenefitActivity extends BaseActivity implements View.OnClickListener {

    private Novate novate;
    private LinearLayout ll_back;
    private TextView tv_title;
    private Button btn_submit;
    private EditText et_chanqi,et_chandian;
    private String chanqi,chandian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryproductionbenefit);
        initView();
    }

    private void initView() {

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生产效益录入");

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        et_chanqi = (EditText) findViewById(R.id.et_chanqi);
        et_chandian = (EditText) findViewById(R.id.et_chandian);

        et_chanqi.addTextChangedListener(textWatcher);
        et_chandian.addTextChangedListener(textWatcher);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.btn_submit:
                submitData();
                break;

        }
    }

    private void submitData() {
        if (StringUtil.isEmpty(et_chanqi.getText().toString())){
            ToastUtils.show("请输入产气量");
            return;
        }else{
            chanqi = et_chanqi.getText().toString();
        }

        if (StringUtil.isEmpty(et_chandian.getText().toString())){
            ToastUtils.show("请输入产电量");
            return;
        }else{
            chandian = et_chandian.getText().toString();
        }

        Gson gson = new Gson();
        CapacityBean bean = new CapacityBean();
        bean.setGasProduction(chanqi);
        bean.setPowerGeneration(chandian);
        bean.setType("1");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constant.CAPACITY_URL, gson.toJson(bean).toString());
        novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Constant.BASE_URL)
                //.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        novate.post(Constant.ENTRYCAPACITY_URL, parameters,
                new MyBaseSubscriber<ResponseBody>(EntryProductionBenefitActivity.this) {
                    @Override
                    public void onError(Throwable e) {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            ToastUtils.show(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jstr = new String(responseBody.bytes());
                            ToastUtils.show(jstr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
