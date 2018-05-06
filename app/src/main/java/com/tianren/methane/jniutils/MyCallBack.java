package com.tianren.methane.jniutils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hisense.hs_iot_interface.Iot_JAVA_Interface;
import com.tianren.methane.constant.DefMsgConstants;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.service.SipService;

public class MyCallBack implements Iot_JAVA_Interface {

    private static final String TAG = "MyCallBack";
    private Handler sipHandler = null;

    public void setHandler(Handler handler) {
        this.sipHandler = handler;
    }

    @Override
    public int notify_Registration_Success() {
        Log.i(TAG, "notify_Registration_Success--sipHandler = " + sipHandler);
        if (sipHandler == null) {
            Log.d(TAG, "sipHandler is null,please set handler 01 !");
        } else {
            sipHandler.sendEmptyMessage(DefMsgConstants.MSG_LOGIN_SUCCESS);
        }

        if (SipService.getMyInterface() != null) {
            Log.v(TAG, "isReregister-->" + SipService.getMyInterface().getIsReregister());
            if (!SipService.getMyInterface().getIsReregister()) {
                SipService.getMyInterface().reregister();
                SipService.getMyInterface().setIsReregister(true);
            }
            SipService.getMyInterface().setFirst(false);
        }
        return 0;
    }

    @Override
    public int notify_Registration_Failure() {
        Log.i(TAG, "notify_Registration_Failure--sipHandler = " + sipHandler);
        if (sipHandler == null) {
            Log.d(TAG, "sipHandler is null,please set handler 02!");
        } else {
            if ((SipService.getMyInterface() != null) && (!SipService.getMyInterface().getFirst())) {
                sipHandler.sendEmptyMessage(DefMsgConstants.MSG_LOGIN_FAILURE);
            }
        }
//		mIsLoginFlag = false; //登录失败标志
        if (SipService.getMyInterface() != null) {
            SipService.getMyInterface().setFirst(false);
        }
        return 0;
    }

    @Override
    public int notify_Message_Answered(String ansmsg) {
        Log.i(TAG, "notify_Message_Answered=====>>>>" + ansmsg);
        Log.v(TAG, "sipHandler = " + sipHandler);
        if (sipHandler == null) {
            Log.d(TAG, "sipHandler is null,please set handler 03!");
        } else {
            Message msg = new Message();
            msg.what = MsgDefCtrl.JNI_MSG_SEND_SUCCESS;//
            msg.obj = ansmsg;
            sipHandler.sendMessage(msg);
        }
        return 0;
    }

    @Override
    public int notify_Received_Message(String revmsg) {
//        if (revmsg.contains("\\n")){
//            String[] attr = revmsg.split("\\n");
//            if (attr.length > 0) {
//                for (int i = 1; i < attr.length; i++) {
//                    if (!attr[i].contains(":")) {
//                        continue;
//                    }
//                    String[] tempArr = attr[i].split(":");
//                    modelMap.put(tempArr[0], tempArr[1]);
//                }
//            }
//        }
//        EventBus.getDefault().post(new ModelEvent());
        return 0;
    }

    //接收发现设备信息
    @Override
    public int notify_Device_Online(String device) {
        Log.i(TAG, "notify_Device_Online--sipHandler = " + sipHandler);
        Log.v(TAG, "notify_Device_Online--device = " + device);
        if (sipHandler == null) {
            Log.d(TAG, "sipHandler is null,please set handler 05 !");
        } else {
            Message message = new Message();
            message.what = DefMsgConstants.MSG_DEVICE_ONLINE;
            message.obj = device;
            sipHandler.sendMessage(message);
        }
        return 0;

    }

}
