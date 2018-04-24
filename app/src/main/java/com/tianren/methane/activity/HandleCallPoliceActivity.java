package com.tianren.methane.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.acommon.remote.callback.SCallBack;
import com.tianren.acommon.service.AlarmService;
import com.tianren.methane.R;
import com.tianren.methane.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Mr.Qiu
 */
public class HandleCallPoliceActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "HandleCallPoliceActivit";
    private LinearLayout ll_back;
    private TextView tv_title;
    private EditText editText, et_time, et_date;
    private TextView text_num;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int hour;
    private int minute;

    private int alarmId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_call_police);
        alarmId = getIntent().getIntExtra("alarmId", -1);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        hour = ca.get(Calendar.HOUR_OF_DAY);
        minute = ca.get(Calendar.MINUTE);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("处理报警");
        findViewById(R.id.now_submit).setOnClickListener(this);
        findViewById(R.id.tv_datepicker).setOnClickListener(this);
        findViewById(R.id.tv_timepicker).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);

        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);
        text_num = (TextView) findViewById(R.id.text_num);
        //标题的输入监听事件
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (length <= 100) {
                    text_num.setText(length + "/100");
                }
            }
        });
        et_date.setText(convertDate(mYear, mMonth, mDay));
        et_time.setText(convertTime(hour, minute));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.now_submit:
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.show("描述不能为空!");
                    break;
                }
                String date = et_date.getText().toString();
                String time = et_time.getText().toString();
                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                    ToastUtils.show("测量时间跟日期不能为空");
                    return;
                }
                WebServiceManage.getService(AlarmService.class).handleAlarm(alarmId, date + " " + time, s).setCallback(new SCallBack<BaseResponse<Boolean>>() {
                    @Override
                    public void callback(boolean isok, String msg, BaseResponse<Boolean> res) {
                        showToast(msg);
                        if (isok && res.getData()) {
                            finish();
                        }
                    }
                });
                break;
            case R.id.tv_datepicker:
                final AlertDialog dialog3 = new AlertDialog.Builder(this).setView(R.layout.dialog_datepicker).show();
                ((TextView) dialog3.findViewById(R.id.tv_title)).setText("日历");
                Window window = dialog3.getWindow();
                window.setGravity(Gravity.CENTER);
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                final TextView tv_date_tmp = (TextView) dialog3.findViewById(R.id.tv_date_tmp);
                DatePicker datePicker = (DatePicker) dialog3.findViewById(R.id.dp_date);
                datePicker.setVisibility(View.VISIBLE);
                datePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        // 获得日历实例
                        String getDate = convertDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        tv_date_tmp.setText(getDate);
                    }
                });
                dialog3.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog3.dismiss();
                    }
                });
                dialog3.findViewById(R.id.iv_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = tv_date_tmp.getText().toString();
                        et_date.setText(TextUtils.isEmpty(text) ? convertDate(mYear, mMonth, mDay) : text);
                        dialog3.dismiss();
                    }
                });
                dialog3.findViewById(R.id.tp_time).setVisibility(View.GONE);
                break;
            case R.id.tv_timepicker:
                final AlertDialog dialog4 = new AlertDialog.Builder(this).setView(R.layout.dialog_datepicker).show();
                ((TextView) dialog4.findViewById(R.id.tv_title)).setText("时间");
                Window window1 = dialog4.getWindow();
                window1.setGravity(Gravity.CENTER);
                window1.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp1 = window1.getAttributes();
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window1.setAttributes(lp1);
                final TextView tv_time_tmp = (TextView) dialog4.findViewById(R.id.tv_time_tmp);
                dialog4.findViewById(R.id.dp_date).setVisibility(View.GONE);
                TimePicker timePicker = (TimePicker) dialog4.findViewById(R.id.tp_time);
                timePicker.setVisibility(View.VISIBLE);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                        tv_time_tmp.setText(convertTime(i, i1));
                    }
                });
                dialog4.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.dismiss();
                    }
                });
                dialog4.findViewById(R.id.iv_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = tv_time_tmp.getText().toString();
                        et_time.setText(TextUtils.isEmpty(text) ? convertTime(hour, minute) : text);
                        dialog4.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 日期转换
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String convertDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 时间转换
     *
     * @param hour
     * @param minute
     * @return
     */
    private String convertTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hour, minute);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }
}
