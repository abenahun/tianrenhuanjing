package com.tianren.methane.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.acommon.bean.ConsumptionBean;
import com.tianren.methane.bean.EntryBean;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/2.
 */

public class EntryProductionCostActivity extends BaseActivity implements View.OnClickListener {

    private Novate novate;
    private LinearLayout ll_back;
    private TextView tv_title, tv_time;
    private Button btn_submit;
    private EditText et_haoshui, et_haodian, et_haoqi;
    private String haoshui, haodian, haoqi;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryproductioncost);
        initView();
        initDatePicker();
        initTimePicker();
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
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);

        et_haoshui.addTextChangedListener(textWatcher);
        et_haodian.addTextChangedListener(textWatcher);
        et_haoqi.addTextChangedListener(textWatcher);
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_time.setText(now.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.btn_submit:
                submitData();
                break;

            case R.id.tv_time:
                //时间选择器
                pvTime.show();

                break;
        }
    }

    private void submitData() {
        if (StringUtil.isEmpty(et_haodian.getText().toString())) {
            ToastUtils.show("请输入耗电量");
            return;
        } else {
            haodian = et_haodian.getText().toString();
        }

        if (StringUtil.isEmpty(et_haoqi.getText().toString())) {
            ToastUtils.show("请输入耗气量");
            return;
        } else {
            haoqi = et_haoqi.getText().toString();
        }

        if (StringUtil.isEmpty(et_haoshui.getText().toString())) {
            ToastUtils.show("请输入耗水量");
            return;
        } else {
            haoshui = et_haoshui.getText().toString();
        }

        Gson gson = new Gson();
        ConsumptionBean bean = new ConsumptionBean();
        bean.setPowerConsumption(Double.parseDouble(haodian));
        bean.setWaterConsumption(Double.parseDouble(haoshui));
        bean.setAirConsumption(Double.parseDouble(haoqi));
        bean.setEntryType(1);
        try {
            bean.setEntryTime(StringUtil.stringToDate(tv_time.getText().toString(),"yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("consumption", gson.toJson(bean).toString());
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
                            Gson gson = new Gson();
                            EntryBean entryBean = gson.fromJson(jstr, EntryBean.class);
                            if (entryBean.getResult()) {
                                ToastUtils.show(entryBean.getMessage());
                                finish();
                            } else {
                                ToastUtils.show(entryBean.getMessage());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(EntryProductionBenefitActivity.this, tv_time.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");
                tv_time.setText(getTime(date));

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
