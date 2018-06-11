package com.tianren.methane.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.tianren.methane.R;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.constant.DefMsgConstants;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.jniutils.CommandDev;
import com.tianren.methane.jniutils.ParseDataFromDev;
import com.tianren.methane.service.SipService;
import com.tianren.methane.utils.SharedPreferenceUtil;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.WaitDialog;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/5/5.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    boolean isFirstIn = false;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟4s
    private static final long SPLASH_DELAY_MILLIS = 2000;

    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    private CommandDev commandDevObj = null;
    private ParseDataFromDev dataParseDevObj = null;
    private static String TAG = "SplashActivity";
    private WaitDialog waitDialog; //登录等待对话框
    private static final int MSG_IMAGE_ANIMATE = 0;
    private boolean ifTimeOut = false;           //是否超时
    private boolean ifReceSuccessMsg = false;  //是否接收到登录成功的消息
    private boolean ifReceFailMsg = false;     //是否接收到登录失败的消息
    private boolean isChinese = true;           //是否为中文模式
    private final String httpKey = "lzky";
    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        initSip();
    }

    private void initSip() {
        if (SipService.isFirstEnterLogin) {
            SipService.isFirstEnterLogin = false;
//            setViewInvisible();
            //第一次登录界面才启service
            Intent intent = new Intent(SplashActivity.this, SipService.class);
            startService(intent);

            handler.sendEmptyMessage(MSG_IMAGE_ANIMATE);
        } else {
//            setViewVisible();
        }

        handler.removeMessages(DefMsgConstants.MSG_LOGIN_SUCCESS);
        handler.removeMessages(DefMsgConstants.MSG_LOGIN_FAILURE);
        handler.removeMessages(DefMsgConstants.MSG_LOGIN_TIME_OUT);//消息移除，防止重复弹出toast
        ifReceSuccessMsg = false; //标志复原
        ifReceFailMsg = false;
        ifTimeOut = false;
        if (SipService.getMyInterface() != null) {
            SipService.getMyInterface().setHandler(handler);
            SipService.getMyCallBack().setHandler(handler);
            SipService.getMyInterface().loginByUsr
                    (SharedPreferenceUtil.getSharedPreString(Constant.SP_USERNAME),
                            SharedPreferenceUtil.getSharedPreString(Constant.SP_USERPASS));
        }
        //点击登录，开启登录等待对话框
//                    startWaitDlg();
        handler.sendEmptyMessageDelayed(DefMsgConstants.MSG_LOGIN_TIME_OUT, Constant.TIME_OUT_LOGIN);//超时提醒
    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的�?�，如果没有该�?�，说明还未写入，用true作为默认�?
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
       /* if (!isFirstIn) {
            // 使用Handler的postDelayed方法�?3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }*/

        mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);

    }

    private void goHome() {
        if (StringUtil.isEmpty(SharedPreferenceUtil.
                getSharedPreString(Constant.SP_USERNAME))) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }

    private void goGuide() {
        /*Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();*/
    }

    private MyHandler handler = new MyHandler(this);

    public class MyHandler extends Handler {
        WeakReference<SplashActivity> activityWeakReference;

        MyHandler(SplashActivity activity) {
            this.activityWeakReference = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SplashActivity activity = activityWeakReference.get();
            switch (msg.what) {

                case DefMsgConstants.MSG_LOGIN_SUCCESS:
                    Log.i(TAG, "MSG_LOGIN_SUCCESS---------");
                    ToastUtils.show("登录成功");
                    activity.ifReceSuccessMsg = true;
//                    activity.dismissWaitDlg();

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
                    Log.i(TAG, "DefMsgConstants.MSG_GET_BIND_DEV_INFO_SUCCESS" + msg.obj.toString());
                    break;

                case DefMsgConstants.MSG_GET_BIND_DEV_INFO_FAIL:
                    Log.i(TAG, "DefMsgConstants.MSG_GET_BIND_DEV_INFO_FAIL");
                    break;

                case MsgDefCtrl.MSG_FRESH_REFRIGERATOR:
//                    freshFridgeManageUI();
//                    Log.i(TAG, "`````````1111111111111111111111111111" + msg.obj.toString());
                    handler.sendEmptyMessageDelayed(MsgDefCtrl.MSG_FRESH_REFRIGERATOR, 5000);
                    break;

                default:

                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void startWaitDlg() {

    }
}
