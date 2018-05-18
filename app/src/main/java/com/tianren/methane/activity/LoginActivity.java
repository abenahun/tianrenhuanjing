package com.tianren.methane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tamic.novate.Novate;
import com.tianren.methane.R;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.constant.DefMsgConstants;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.service.SipService;
import com.tianren.methane.utils.SharedPreferenceUtil;
import com.tianren.methane.utils.StringUtil;
import com.tianren.methane.utils.ToastUtils;
import com.tianren.methane.view.WaitDialog;

import org.zoolu.net.IpAddress;
import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.call.RegistrationClient;
import org.zoolu.sip.call.RegistrationClientListener;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.SipStack;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/4/8.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private WaitDialog waitDialog; //登录等待对话框
    private static final int MSG_IMAGE_ANIMATE = 0;
    private Button btn_login, button;
    private EditText et_passwd, et_username;
    private String passwd, username;
    private boolean ifTimeOut = false;           //是否超时
    private boolean ifReceSuccessMsg = false;  //是否接收到登录成功的消息
    private boolean ifReceFailMsg = false;     //是否接收到登录失败的消息
    private boolean isChinese = true;           //是否为中文模式
    private final String httpKey = "lzky";
    private Novate novate;

    private LinearLayout ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
//        et_username.setText(username);
//        if (!username.equals("")) {
//            et_username.setSelection(username.length());
//        }
        et_passwd = (EditText) findViewById(R.id.et_passwd);
//        et_passwd.setText(passwd);
//        if (!passwd.equals("")) {
//            et_passwd.setSelection(passwd.length());
//        }
        findViewById(R.id.ll_back).setVisibility(View.GONE);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");

        btn_login = (Button) findViewById(R.id.btn_login);
        button = (Button) findViewById(R.id.button);
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

    private static final String TAG = "Mjsip-Test-App|| ";
    private IpAddress ip;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
                toRegister();
                break;

            case R.id.ll_back:
                finish();
                break;
        }
    }

    private void toRegister() {
        if (!SipStack.isInit()) {
            SipStack.init();
            Log.v(TAG, "Stack.init");
        }
        ip = IpAddress.getLocalHostAddress();
        SipProvider sipProvider = new SipProvider("");
        SipURL registrar = new SipURL("iotac.tianren.com");
        NameAddress target_url = new NameAddress("1001");
        NameAddress from_url = new NameAddress("18561589055");
        RegistrationClient reg = new RegistrationClient(sipProvider, registrar, from_url,
                target_url, "18561589055", "", "123456", new RegistrationClientListener() {
            @Override
            public void onRegistrationSuccess(RegistrationClient registrationClient, NameAddress nameAddress, NameAddress nameAddress1, String s) {
                Log.e(TAG, "onRegistrationSuccess: ");
            }

            @Override
            public void onRegistrationFailure(RegistrationClient registrationClient, NameAddress nameAddress, NameAddress nameAddress1, String s) {
                Log.e(TAG, "onRegistrationFailure: ");
            }
        });
        reg.register();


    }

    private MyHandler handler = new MyHandler(this);

    public class MyHandler extends Handler {
        WeakReference<LoginActivity> activityWeakReference;

        MyHandler(LoginActivity activity) {
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
                    activity.putValueToTable(Constant.SP_USERNAME, activity.et_username.getText().toString());
                    activity.putValueToTable(Constant.SP_USERPASS, activity.et_passwd.getText().toString());
//                    activity.dismissWaitDlg();

                    //如果没有超时，自动登录，启EditAcFragment
                    if (activity.ifTimeOut == false) {
//                        activity.goToHomeActivity();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    SharedPreferenceUtil.saveSharedPreString
                            (Constant.SP_USERNAME, et_username.getText().toString());
                    SharedPreferenceUtil.saveSharedPreString
                            (Constant.SP_USERPASS, et_username.getText().toString());
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

                    } else {
                        Log.v(TAG, "MSG_LOGIN_TIME_OUT--else--time out");
                        SipService.getMyCallBack().setHandler(null);
                        activity.handler.removeMessages(DefMsgConstants.MSG_LOGIN_SUCCESS);
                        activity.handler.removeMessages(DefMsgConstants.MSG_LOGIN_FAILURE);
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

    public void startWaitDlg() {
        waitDialog = new WaitDialog(LoginActivity.this, R.style.dialog_style);
        waitDialog.getTxWaitTip().setText("登录中...");
        WindowManager.LayoutParams localLayoutParams = waitDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        waitDialog.show();
    }
}
