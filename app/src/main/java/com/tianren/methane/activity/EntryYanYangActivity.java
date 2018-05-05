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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.aloglibrary.ALog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.novate.Novate;
import com.tamic.novate.NovateResponse;
import com.tamic.novate.Throwable;
import com.tianren.methane.ExempleActivity;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.bean.AnaerobicTankBean;
import com.tianren.methane.bean.EntryBean;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.model.ResultModel;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.tianren.methane.R.mipmap.novate;


/**
 * Created by Administrator on 2018/4/2.
 */

public class EntryYanYangActivity extends BaseActivity implements View.OnClickListener {
    private Novate novate;
    private LinearLayout ll_back;
    private TextView tv_title,tv_time;
    private Button btn_submit;
    private Spinner spinner;
    private EditText et_ph, et_ts, et_vs, et_vfa, et_jiandu, et_andan, et_cod;
    private String ph, ts, vs, vfa, jiandu, andan, cod,quyangdian;
    private ArrayList<String> spinners;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryyanyang);
        initView();
        initDatePicker();
        initTimePicker();
    }

    private void initView() {

        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("智能厌氧反应罐录入");

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        et_ph = (EditText) findViewById(R.id.et_ph);
        et_ts = (EditText) findViewById(R.id.et_ts);
        et_vs = (EditText) findViewById(R.id.et_vs);
        et_vfa = (EditText) findViewById(R.id.et_vfa);
        et_jiandu = (EditText) findViewById(R.id.et_jiandu);
        et_andan = (EditText) findViewById(R.id.et_andan);
        et_cod = (EditText) findViewById(R.id.et_cod);
        spinner = (Spinner) findViewById(R.id.spinner);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);

        et_ph.addTextChangedListener(textWatcher);
        et_ts.addTextChangedListener(textWatcher);
        et_vs.addTextChangedListener(textWatcher);
        et_vfa.addTextChangedListener(textWatcher);
        et_jiandu.addTextChangedListener(textWatcher);
        et_andan.addTextChangedListener(textWatcher);
        et_cod.addTextChangedListener(textWatcher);

        //数据源
        spinners = new ArrayList<>();
        spinners.add("取样点1");
        spinners.add("取样点2");

        //设置ArrayAdapter内置的item样式-这里是单行显示样式
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, spinners);
        //这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //设置Adapter了
        spinner.setAdapter(adapter);
        //监听Spinner的操作
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //选取时候的操作
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                quyangdian = spinners.get(position).toString();
            }
            //没被选取时的操作
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        if (StringUtil.isEmpty(et_ph.getText().toString())){
            ToastUtils.show("请输入pH值");
            return;
        }else{
            ph = et_ph.getText().toString();
        }

        if (StringUtil.isEmpty(et_ts.getText().toString())){
            ToastUtils.show("请输入TS值");
            return;
        }else{
            ts = et_ts.getText().toString();
        }

        if (StringUtil.isEmpty(et_vs.getText().toString())){
            ToastUtils.show("请输入VS值");
            return;
        }else{
            vs = et_vs.getText().toString();
        }

        if (StringUtil.isEmpty(et_vfa.getText().toString())){
            ToastUtils.show("请输入VFA值");
            return;
        }else{
            vfa = et_vfa.getText().toString();
        }

        if (StringUtil.isEmpty(et_jiandu.getText().toString())){
            ToastUtils.show("请输入碱度值");
            return;
        }else{
            jiandu = et_jiandu.getText().toString();
        }

        if (StringUtil.isEmpty(et_andan.getText().toString())){
            ToastUtils.show("请输入氨氮值");
            return;
        }else{
            andan = et_andan.getText().toString();
        }

        if (StringUtil.isEmpty(et_cod.getText().toString())){
            ToastUtils.show("请输入COD值");
            return;
        }else{
            cod = et_cod.getText().toString();
        }

        Gson gson = new Gson();
        AnaerobicTankBean bean = new AnaerobicTankBean();
        bean.setPh(ph);
        bean.setTs(ts);
        bean.setVfa(vfa);
        bean.setCod(cod);
        bean.setVs(vs);
        bean.setAlkalinity(jiandu);
        bean.setSamplingPoint(spinners.get(spinner.getSelectedItemPosition()).toString());
        bean.setAmmoniaNitrogen(andan);
        try {
            bean.setEntryTime(StringUtil.stringToDate(tv_time.getText().toString(),"yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("anaerobicTankData", gson.toJson(bean).toString());
        novate = new Novate.Builder(this)
                .connectTimeout(8)
                .baseUrl(Constant.BASE_URL)
                //.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        novate.post(Constant.ENTRYYANYANG_URL, parameters,
                new MyBaseSubscriber<ResponseBody>(EntryYanYangActivity.this) {
            @Override
            public void onError(Throwable e) {
                if (!TextUtils.isEmpty(e.getMessage())) {
                    Toast.makeText(EntryYanYangActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jstr = new String(responseBody.bytes());
                    Gson gson = new Gson();
                    EntryBean entryBean = gson.fromJson(jstr,EntryBean.class);
                    if (entryBean.getResult()){
                        ToastUtils.show(entryBean.getMessage());
                        finish();
                    }else {
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