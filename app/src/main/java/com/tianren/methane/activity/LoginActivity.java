package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tianren.methane.MyBaseSubscriber;
import com.tianren.methane.R;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.constant.DefMsgConstants;
import com.tianren.methane.jniutils.AESTool;
import com.tianren.methane.jniutils.AllenEncode;
import com.tianren.methane.jniutils.MyInterface;
import com.tianren.methane.service.SipService;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/8.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "LoginActivity";
    private WaitDialog waitDialog; //登录等待对话框
    private static final int MSG_IMAGE_ANIMATE = 0;
    private Button btn_login,button;
    private EditText et_passwd,et_username;
    private String passwd,username;
    private boolean ifTimeOut = false;    	   //是否超时
    private boolean ifReceSuccessMsg = false;  //是否接收到登录成功的消息
    private boolean ifReceFailMsg = false;     //是否接收到登录失败的消息
    private boolean isChinese = true;    	   //是否为中文模式
    private final String httpKey = "lzky";
    private Novate novate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_username  = (EditText)findViewById (R.id.et_username);
//        et_username.setText(username);
//        if (!username.equals("")) {
//            et_username.setSelection(username.length());
//        }
        et_passwd = (EditText)findViewById (R.id.et_passwd);
//        et_passwd.setText(passwd);
//        if (!passwd.equals("")) {
//            et_passwd.setSelection(passwd.length());
//        }
        btn_login = (Button)findViewById(R.id.btn_login);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        Log.v(TAG, "isFirstEnterLogin: " + SipService.isFirstEnterLogin);
        if (SipService.isFirstEnterLogin) {
            SipService.isFirstEnterLogin = false;
//            setViewInvisible();
            //第一次登录界面才启service
            Intent intent = new Intent(LoginActivity.this, SipService.class);
            startService(intent);

            handler.sendEmptyMessage(MSG_IMAGE_ANIMATE);
        } else {
//            setViewVisible();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                handler.removeMessages(DefMsgConstants.MSG_LOGIN_SUCCESS);
                handler.removeMessages(DefMsgConstants.MSG_LOGIN_FAILURE);
                handler.removeMessages(DefMsgConstants.MSG_LOGIN_TIME_OUT);//消息移除，防止重复弹出toast
                ifReceSuccessMsg = false; //标志复原
                ifReceFailMsg = false;
                ifTimeOut = false;

                //判断用户名和密码是否为空
                if (StringUtil.isEmpty(et_username.getText().toString())) {
                    ToastUtils.show("请输入用户名");

                } else if (StringUtil.isEmpty(et_passwd.getText().toString())) {
                    ToastUtils.show("请输入密码");

                } else {
                    if (SipService.getMyInterface() != null) {
                        SipService.getMyInterface().setHandler(handler);
                        SipService.getMyCallBack().setHandler(handler);
                        SipService.getMyInterface().loginByUsr(et_username.getText().toString(), et_passwd.getText().toString());
                    }
                    //点击登录，开启登录等待对话框
//                    startWaitDlg();
                    handler.sendEmptyMessageDelayed(DefMsgConstants.MSG_LOGIN_TIME_OUT, Constant.TIME_OUT_LOGIN);//超时提醒
                }

                break;

            case R.id.button:

//                if (SipService.getMyInterface() != null) {
//                    SipService.getMyInterface().sendMsgInWAN(to, from, msg_body,domainStr);
//                }

                break;
        }
    }

    private MyHandler handler = new MyHandler(this);

    public static class MyHandler extends Handler {
        WeakReference<LoginActivity> activityWeakReference;
        MyHandler(LoginActivity activity){
            this.activityWeakReference = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final LoginActivity activity = activityWeakReference.get();
            switch (msg.what) {

                case DefMsgConstants.MSG_LOGIN_SUCCESS:
                    Log.i(TAG, "MSG_LOGIN_SUCCESS---------");

                    ToastUtils.show("登录成功");
                    activity.ifReceSuccessMsg = true;
                    //保存用户名和密码
                    activity.putValueToTable("username", activity.et_username.getText().toString());
                    activity.putValueToTable("password", activity.et_passwd.getText().toString());
//                    activity.dismissWaitDlg();

                    //如果没有超时，自动登录，启EditAcFragment
                    if (activity.ifTimeOut == false) {
//                        activity.goToHomeActivity();
                    }
                    break;
                case DefMsgConstants.MSG_LOGIN_FAILURE:
                    Log.i(TAG, "MSG_LOGIN_FAILURE---------");
                    activity.ifReceFailMsg = true;
                    break;
                case DefMsgConstants.MSG_LOGIN_TIME_OUT:
                    Log.i(TAG, "MSG_LOGIN_TIME_OUT---------");
                    Log.v(TAG, "activity: " + activity);
                    activity.ifTimeOut = true;
//                    activity.dismissWaitDlg();

                    if (activity.ifReceSuccessMsg) {
                        Log.v(TAG, "MSG_LOGIN_TIME_OUT--activity.ifReceSuccessMsg == true");
                        SipService.getMyCallBack().setHandler(null);
                    } else if (activity.ifReceFailMsg) {
                        Log.v(TAG, "MSG_LOGIN_TIME_OUT--activity.ifReceFailMsg == true");
                        SipService.getMyCallBack().setHandler(null);
//                        ToastCustom.makeText(activity, activity.getResources().getString(R.string.login_failture), Toast.LENGTH_SHORT).show();

                    } else {
                        Log.v(TAG, "MSG_LOGIN_TIME_OUT--else--time out");
                        SipService.getMyCallBack().setHandler(null);
                        activity.handler.removeMessages(DefMsgConstants.MSG_LOGIN_SUCCESS);
                        activity.handler.removeMessages(DefMsgConstants.MSG_LOGIN_FAILURE);
//                        ToastCustom.makeText(activity, activity.getResources().getString(R.string.login_time_out), Toast.LENGTH_SHORT).show();
                    }

                   /* if (activity.et_username.getVisibility() != 0) {
                        sendEmptyMessageDelayed(MSG_ET_USERNAME_VISIBLE, DELAY_IMG_ANIMATE);
                    }*/
                    break;

                case DefMsgConstants.MSG_NO_CONNECT_NET://未连网
                    Log.i(TAG, "MSG_NO_CONNECT_NET---------");
                   /* activity.dismissWaitDlg();
                    activity.handler.removeMessages(DefMsgConstants.MSG_LOGIN_TIME_OUT);
                    if (activity.et_username.getVisibility() != 0) {
                        sendEmptyMessageDelayed(MSG_ET_USERNAME_VISIBLE, DELAY_IMG_ANIMATE);
                    }*/
                    break;

                case DefMsgConstants.MSG_GET_BIND_DEV_INFO_SUCCESS://获取绑定的设备信息 deviceId、昵称、条形码
                    Log.i(TAG, "DefMsgConstants.MSG_GET_BIND_DEV_INFO_SUCCESS"+msg.obj.toString());
                    break;

                case DefMsgConstants.MSG_GET_BIND_DEV_INFO_FAIL:
                    Log.i(TAG, "DefMsgConstants.MSG_GET_BIND_DEV_INFO_FAIL");
                    break;
                default:
                    break;
            }
        }
    }

    public void startWaitDlg() {
        waitDialog = new WaitDialog(LoginActivity.this, R.style.dialog_style);
        waitDialog.getTxWaitTip().setText("登录中...");
        WindowManager.LayoutParams localLayoutParams = waitDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        waitDialog.show();
    }
}
