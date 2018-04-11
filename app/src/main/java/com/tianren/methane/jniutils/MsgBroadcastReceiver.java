package com.tianren.methane.jniutils;

import com.hisense.hs_iot_interface.Iot_JNI_Interface;
import com.tianren.methane.activity.MainActivity;
import com.tianren.methane.service.SipService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MsgBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "MsgBroadcastReceiver";
	private Context mContext;
	private int count;
	
	private HomeKeyListener homeKeyListener = null;
	private String SYSTEM_REASON = "reason";  
	private String SYSTEM_HOME_KEY = "homekey"; 
	
	public MsgBroadcastReceiver(Context context){  
		mContext = context;  
		count = 0;
    } 
	
	//home键监听回调接口。
	public void setHomeKeyListener(HomeKeyListener listener) {
		homeKeyListener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive!--intent = " + intent.getAction());
		count++;
		boolean isNetOk = false;
		this.mContext = context;
		
		if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && (count > 1)) {
			Log.d(TAG, "receive network change broadcast receiver");
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 			
	        if (connectivityManager != null) { 
	            NetworkInfo [] networkInfos = connectivityManager.getAllNetworkInfo(); 
//	            Log.d(TAG, "network size = " + networkInfos.length);
	            for (int i = 0; i < networkInfos.length; i++) { 
	                State state = networkInfos[i].getState(); 
	                if (State.CONNECTED == state) {
	                    isNetOk = true; 
	                    break; 
	                } 
	            } 
	        }	
	        Log.v(TAG, "isNetOk = " + isNetOk);
	        if (isNetOk) {
	        	login();
			} else {
				Log.e(TAG, "network is not connected!--SipService.networkTip: " + SipService.networkTip);
				if (SipService.networkTip) {
//					ToastCustom.makeText(mContext.getApplicationContext(), mContext.getResources().getString(R.string.detect_network), Toast.LENGTH_SHORT).show();
				}
			}
	        
		} else if (intent.getAction().equals("com.wochacha.hisense.food")){
            String scanfoodname = intent.getStringExtra("result");
            String scanbarcode = intent.getStringExtra("barcode");
            String scantype = intent.getStringExtra("type");
            Log.v(TAG, "食品的名字=  "+scanfoodname+" 条码=  "+scanbarcode+" 类型= "+scantype);
            if(scanfoodname.equals("")){
//            	ToastCustom.makeText(context, R.string.getscannamefail, Toast.LENGTH_SHORT);
            }else{
                Intent scanNewFood = new Intent(context, MainActivity.class);
                scanNewFood.putExtra("scanfoodname", scanfoodname);
                
                scanNewFood.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                context.startActivity(scanNewFood);
            }         
		} else if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			String reason = intent.getStringExtra(SYSTEM_REASON);  
            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
                //表示按了home键,程序到了后台  
            	if (homeKeyListener != null) {
            		Log.d(TAG, "SYSTEM_HOME_KEY");
                	homeKeyListener.onHomeKeyChange(0);
				}
            }
		}
	}
	
	//开一个线程登录
	public void login(){
		new Thread() {			
			@Override
			public void run() {
				String username = getStringFromTable("username", "");
				String password = getStringFromTable("password", "");
				if ((!username.equals("")) && (!password.equals(""))) {
					Log.i(TAG, "login in MsgBroadcastReceiver");
					if (SipService.getMyInterface() != null) {
						SipService.getMyInterface().loginByUsr(username, password);
					}
				}
			}
		}.start();
	}
	
	//获取已经存储的用户名或密码
	public String getStringFromTable(String key, String defaultValue) {
		SharedPreferences sp = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		String value = sp.getString(key, defaultValue);
		return value;
	}

	//动态注册广播
    public void registerAction(){  
    	IntentFilter filter = new IntentFilter();  
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("com.wochacha.hisense.food");
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mContext.registerReceiver(this, filter);  
    }
    
    //注册按下home键广播
    public void registerHomeKeyAction(){  
    	IntentFilter filter = new IntentFilter();  
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mContext.registerReceiver(this, filter);  
    }
    
    //type:0---单击；1---长按。
	public interface HomeKeyListener {
		public void onHomeKeyChange(int type);
	}
}
