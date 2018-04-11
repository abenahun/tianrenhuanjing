package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.bean.CapacityBean;
import com.tianren.methane.bean.ConsumptionBean;
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

public class EntryProductionCostActivity extends BaseActivity implements View.OnClickListener {

    private Novate novate;
    private LinearLayout ll_back;
    private TextView tv_title;
    private Button btn_submit;
    private EditText et_haoshui,et_haodian,et_haoqi;
    private String haoshui,haodian,haoqi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryproductioncost);
        initView();
    }

    private void initView() {

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生产成本录入");

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        et_haoshui = (EditText) findViewById(R.id.et_haoshui);
        et_haodian = (EditText) findViewById(R.id.et_haodian);
        et_haoqi = (EditText) findViewById(R.id.et_haoqi);

        et_haoshui.addTextChangedListener(textWatcher);
        et_haodian.addTextChangedListener(textWatcher);
        et_haoqi.addTextChangedListener(textWatcher);
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
        if (StringUtil.isEmpty(et_haodian.getText().toString())){
            ToastUtils.show("请输入耗电量");
            return;
        }else{
            haodian = et_haodian.getText().toString();
        }

        if (StringUtil.isEmpty(et_haoqi.getText().toString())){
            ToastUtils.show("请输入耗气量");
            return;
        }else{
            haoqi = et_haoqi.getText().toString();
        }

        if (StringUtil.isEmpty(et_haoshui.getText().toString())){
            ToastUtils.show("请输入耗水量");
            return;
        }else{
            haoshui = et_haoshui.getText().toString();
        }

        Gson gson = new Gson();
        ConsumptionBean bean = new ConsumptionBean();
        bean.setPowerConsumption(haodian);
        bean.setWaterConsumption(haoshui);
        bean.setAirConsumption(haoqi);
        bean.setEntryType("1");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constant.CONSUMPTION_URL, gson.toJson(bean).toString());
        novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Constant.BASE_URL)
                //.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        novate.post(Constant.ENTRYCONSUMPTION_URL, parameters,
                new MyBaseSubscriber<ResponseBody>(EntryProductionCostActivity.this) {
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
