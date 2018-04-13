/**
* @Title: MyInterface.java
* @Package com.hisense.acclient.utils.cc
* @Description: TODO(用一句话描述该文件做什么)
* @author lixiaolan
* @date 2014-9-1 上午11:14:44
* @version V1.0
*/

package com.tianren.methane.jniutils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hisense.hitv.hicloud.bean.account.AppCodeReply;
import com.hisense.hitv.hicloud.bean.account.AppCodeSSO;
import com.hisense.hitv.hicloud.bean.account.ReplyInfo;
import com.hisense.hitv.hicloud.bean.account.SignonReplyInfo;
import com.hisense.hitv.hicloud.bean.global.HiSDKInfo;
import com.hisense.hitv.hicloud.factory.HiCloudServiceFactory;
import com.hisense.hitv.hicloud.service.HiCloudAccountService;
import com.hisense.hitv.hicloud.util.DeviceConfig;
import com.hisense.hitv.hicloud.util.Params;
import com.hisense.hs_iot_interface.Iot_JNI_Interface;
import com.tianren.methane.constant.DefMsgConstants;
import com.tianren.methane.service.SipService;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @ClassName: MyInterface
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lixiaolan
 * @date 2014-9-1 上午11:14:44
 *
 */

public class MyInterface{

	private static final String TAG = "cc.MyInterface";
	private static final int EXPIRY = 1200; // unit s，注册有效时间
	private static final String DOMAIN = "iotac.tianren.com";
	private static final String DOMAIN_HTTP = "http://iotu.tianren.com:8080/sipserver/";
//	private static final String DOMAIN_HTTP = "http://203.130.46.105:8080/sipserver/";//for test
	private final String httpKey = "lzky";
	private boolean first = true ;    //去除HOT_FAILURE 信息
	private boolean mIsNotificationMsgCanRecive = true;//通知栏是否接收消息
	private boolean isInitWan = false ;//是否已经广域网初始化
	private boolean isSetrevmsg = false ;
	private boolean isReregister = false ;//是否已经进行reregister
	private boolean isThreadEnable = false;	//是否已经调用setThreadEnable函数

	private final int MSG_KILL_PROCESS = 111;
	private final int MSG_STOP_SERVICE = 112;
	private final int MSG_TOAST_SHOW = 113;//弹出toast消息，在子线程中弹，因此要发消息
	private Context mContext;

	private static final String appKey = "1560000886";
	private static final String appSecret = "e544cdgwt27i5tk2qv0j43tt28o5458p";
	private String appCode = null;
	private String token = "";
	private String appCodeSSO = null;
	private String tokenSSO = null;

	public MyInterface(){
		Log.i(TAG, "MyInterface() function");
	}

	public void setmContext(Context context) {
		mContext = context;
	}

	public void setIsThreadEnable(boolean isThreadEnable) {
		this.isThreadEnable = isThreadEnable;
	}

	public boolean getIsThreadEnable() {
		return isThreadEnable;
	}

	public void setInitWan(boolean isInitWan) {
		this.isInitWan = isInitWan;
	}

	public boolean getIsInitWan() {
		return isInitWan;
	}

	public void setFirst(boolean flag) {
		this.first = flag;
	}

	public boolean getFirst() {
		return first;
	}

	public void setIsReregister(boolean flag) {
		this.isReregister = flag;
	}

	public boolean getIsReregister() {
		return isReregister;
	}

	public void setNotificationReciveFlag(boolean flag){
		mIsNotificationMsgCanRecive = flag;
	}

	public boolean getNotificationReciveFlag(){
		return mIsNotificationMsgCanRecive;
	}

	public void destroyInstance(){
		Log.d(TAG,"destroy interface instance! ");
		logoffByUsr();
		handler.sendEmptyMessageDelayed(MSG_STOP_SERVICE, 500);//延时原因：等收到注销成功或失败消息后，才是真正注销完成
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_KILL_PROCESS:
				Log.i(TAG, "MSG_KILL_PROCESS");
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			case MSG_STOP_SERVICE:
				Log.i(TAG, "MSG_STOP_SERVICE");
				mIsNotificationMsgCanRecive = true;
				setInitWan(false);//释放了资源，将初始化标志位 置false
				setThreadEnable(false);

				new Thread() {
					@Override
					public void run() {
						unInit();
					}
				}.start();
				mContext.stopService(new Intent(mContext, SipService.class));
				handler.sendEmptyMessageDelayed(MSG_KILL_PROCESS, 500);//等待service以及logoff结束，再kill
				break;
			case MSG_TOAST_SHOW:
				Log.i(TAG, "MSG_TOAST_SHOW");
//				ToastCustom.makeText(mContext, mContext.getResources().getString(R.string.detect_network), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	// 初始化接口，只调用一次。初始化成功才可以使用别的广域网的接口。
	public boolean init_wan() {
		boolean res = Iot_JNI_Interface.hs_iot_init() == 0 ? true : false;// 0:成功,-1：failed;
		return res;
	}

	//已经注册的用户登录，先登录海视云服务器，成功后再登录王蒙服务器
	public void loginByUsr(final String usr, final String pw){

		if (!getPhoneIpAddress().equals("")) {
			haiSignon(usr, pw);
		} else {
			if (sipHandler != null) {
				handler.sendEmptyMessage(MSG_TOAST_SHOW);
				sipHandler.sendEmptyMessage(DefMsgConstants.MSG_NO_CONNECT_NET);
			}
		}
		Log.v(TAG, "sipHandler = " + sipHandler);
	}

	//sip服务器登录
	public void sipLogin(final String usr, final String pw) {
		Log.i(TAG, "sipLogin--isInitWan = " + isInitWan + "--usr: " + usr + ", pw: " + pw);
		if(!isInitWan){
			boolean hasInitFlag = init_wan();
			Log.i(TAG, "hasInitFlag = " + hasInitFlag);
			if(hasInitFlag ){
				isInitWan = true ;
			}else{
				isInitWan = false ;
			}
		}
		if (!isThreadEnable) {
			Log.i(TAG, "setThreadEnable----");
			setThreadEnable(true);
			isThreadEnable = true;
		}
		if(!isSetrevmsg){
			Log.i(TAG, "setrevmsg----");
			setrevmsg();
			isSetrevmsg = true ;
		}

		first = true ;
		Log.v(TAG, "sipLogin--domain_login = " + getValueFromTable("domain_login", ""));
		if (!getValueFromTable("domain_login", "").equals("")) {
			Iot_JNI_Interface.hs_iot_register(usr, AllenEncode.AllenEncode(pw).substring(0, 10), EXPIRY, getValueFromTable("domain_login", ""), getPhoneIpAddress());//res=0:正常
		} else {
			Iot_JNI_Interface.hs_iot_register(usr, AllenEncode.AllenEncode(pw).substring(0, 10), EXPIRY, DOMAIN, getPhoneIpAddress());//res=0:正常
		}
	}

	//若王蒙那有资源被回收，则重新进行一些初始化
	public void reInit() {
		setInitWan(false);//释放了资源，将初始化标志位 置false
		setThreadEnable(false);
		setIsThreadEnable(false);

		String username = getValueFromTable("username", "");
		String password = getValueFromTable("password", "");
		Log.i(TAG, "logoffByUsr--username: " + username + ", password: " + password);
		setHandler(null);
		if (!username.equals("") && !password.equals("")) {
			loginByUsr(username, password);
		}
	}

	//注销
	public void logoffByUsr(){
		haiLogout();//海视云注销

		//sip服务器log out
		String username = getValueFromTable("username", "");
		String password = getValueFromTable("password", "");
		Log.i(TAG, "logoffByUsr--username: " + username + ", password: " + password);
		setHandler(null);
		if (!username.equals("") && !password.equals("")) {
			Log.v(TAG, "logoffByUsr--domain_login = " + getValueFromTable("domain_login", ""));
			if (!getValueFromTable("domain_login", "").equals("")) {
				Iot_JNI_Interface.hs_iot_register(username, AllenEncode.AllenEncode(password).substring(0, 10), 0, getValueFromTable("domain_login", ""), getPhoneIpAddress());
			} else {
				Iot_JNI_Interface.hs_iot_register(username, AllenEncode.AllenEncode(password).substring(0, 10), 0, DOMAIN, getPhoneIpAddress());
			}
		}
	}

	//资源释放
	public void unInit() {
		Log.i(TAG, "unInit");
		Iot_JNI_Interface.hs_iot_uninit();
	}

	//定时注册和接收消息使能设置接口
	public boolean setThreadEnable(boolean isEnable) {
		boolean res = true;
		if (isEnable) {
			res = Iot_JNI_Interface.hs_iot_setThread(1, 1)==0 ? true : false;// 0:成功,-1：failed;
		} else {
			res = Iot_JNI_Interface.hs_iot_setThread(0, 0)==0 ? true : false;
		}
		return res;
	}

	// 定时注册接口，底层会循环定时发送注册消息
	public void reregister() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Iot_JNI_Interface.hs_iot_reregister();
			}
		}.start();
	}

	// 回调消息接收接口,使用回调的话需先调用此函数
	public void setrevmsg(){
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Iot_JNI_Interface.hs_iot_setrevmsg();
			}
		}.start();
	}

	public void createNewUser(final Handler handler, final String username, final String pwd, final boolean ifAddHaishiyunUser){
		new Thread(){
			public void run() {
//				appAuthBlock();
//				deviceId = DeviceConfig.getDeviceId(mContext);//获取设备id的方法
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					jsonSend.put("cmd", "addUser");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("password", AllenEncode.AllenEncode(pwd).substring(0, 10));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					Log.d(TAG, "createNewUser--user:"+username+",AllenEncode.AllenEncode(pwd):"+AllenEncode.AllenEncode(pwd).substring(0, 10));
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String urlString = DOMAIN_HTTP + "HttpAddUSer?param=" + jsonSend.toString();
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(urlString));
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					if(200 == jsonResult.optInt("code")){
						handler.sendEmptyMessage(DefMsgConstants.MSG_REGISTER_SUCCESS);
					}else{
						Message registerErrorMsg = new Message();
						registerErrorMsg.what = DefMsgConstants.MSG_REGISTER_FAILURE;
						registerErrorMsg.obj = jsonResult.optString("error");
						handler.sendMessage(registerErrorMsg);
					}
					Log.v(TAG, "ifAddHaishiyunUser: " + ifAddHaishiyunUser);
					if (ifAddHaishiyunUser) {
						sipLogin(username, pwd);
					}
				} catch (JSONException e) {
					if (ifAddHaishiyunUser) {
						sipLogin(username, pwd);
					} else {
						handler.sendEmptyMessage(DefMsgConstants.MSG_REGISTER_TIME_OUT);
					}
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	//获取绑定的设备信息（设备编号、昵称、条形码）
	public void queryBindDeviceInfo() {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					String username = getValueFromTable("username", "");
					jsonSend.put("cmd", "queryBindDeviceinfo");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "queryBindDeviceInfo--user: " + username);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpQueryBindDeviceinfo?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();

					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_GET_BIND_DEV_INFO_SUCCESS;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_GET_BIND_DEV_INFO_FAIL;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//上传设备昵称
	public void updateNickname(final String deviceId, final String nickName) {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					jsonSend.put("cmd", "updateNickname");
					jsonSend.put("user", AESTool.getInstance().content(deviceId, AESTool.AES_KEY));
					jsonSend.put("nickname", AESTool.getInstance().content(URLEncoder.encode(nickName, "UTF-8"), AESTool.AES_KEY));// URLEncoder.encode(nickName, "UTF-8")
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "updateNickname--deviceId: " + deviceId);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpUpdateNickname?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--updateNickname = " + jsonResult.toString());
					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_UPDATE_NICKNAME_SUCCESS;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_UPDATE_NICKNAME_FAIL;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//上传条码
	public void updateBarcode(final String deviceId, final String barcode) {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					Log.i(TAG, "updateBarcode--barcode = " + barcode);
					jsonSend.put("cmd", "updateBarcode");
					jsonSend.put("user", AESTool.getInstance().content(deviceId, AESTool.AES_KEY));
					jsonSend.put("barcode", barcode);
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "updateBarcode--deviceId: " + deviceId);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpUpdateBarcode?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();

					Log.v(TAG, "jsonobj--updateBarcode = " + jsonResult.toString());
					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_UPDATE_BARCODE_SUCCESS;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_UPDATE_BARCODE_FAIL;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//上传个人信息（姓名、性别、地址等）
	public void updateUserInfo(final String name, final String sex, final String province, final String city, final String address, final String email) {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					String username = getValueFromTable("username", "");
					Log.i(TAG, "updateUserInfo--username: " + username + ", name: " + name + ", provinceId: " + province + ", cityId: " + city + ", sex: " + sex);
					jsonSend.put("cmd", "updateUserinfo");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("name", AESTool.getInstance().content(URLEncoder.encode(name, "UTF-8"), AESTool.AES_KEY));//URLEncoder.encode(name, "UTF-8")
					jsonSend.put("sex", AESTool.getInstance().content(sex, AESTool.AES_KEY));
					jsonSend.put("province", AESTool.getInstance().content(province, AESTool.AES_KEY));
					jsonSend.put("city", AESTool.getInstance().content(city, AESTool.AES_KEY));
					jsonSend.put("address", AESTool.getInstance().content(URLEncoder.encode(address, "UTF-8"), AESTool.AES_KEY));
					jsonSend.put("email", AESTool.getInstance().content(email, AESTool.AES_KEY));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpUpdateUserinfo?param=" + jsonSend.toString()));
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();

					Log.v(TAG, "jsonobj--updateUserInfo = " + jsonResult.toString());
					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_UPDATE_USERINFO_SUCCESS;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						} else {
							sipHandler.sendEmptyMessage(DefMsgConstants.MSG_UPDATE_USERINFO_FAIL);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//获取用户信息（姓名、性别、地址等）
	public void queryUserinfo() {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					String username = getValueFromTable("username", "");
					jsonSend.put("cmd", "queryUserinfo");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "queryUserinfo--user: " + username);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {

					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpQueryUserinfo?param=" + jsonSend.toString()));
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();

					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message msg = new Message();
							msg.what = DefMsgConstants.MSG_GET_USERINFO_SUCCESS;
							msg.obj = jsonResult;
							sipHandler.sendMessage(msg);
						} else {
//							sipHandler.sendEmptyMessage(DefMsgConstants.MSG_GET_USERINFO_FAIL);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//绑定设备，入参：设备Id + 设备密码
	public void bindDev(final String deviceId, final String devicePasswd, final String deviceDomain) {//
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					String username = getValueFromTable("username", "");
					jsonSend.put("cmd", "bindDev");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("bind_user", AESTool.getInstance().content(deviceId, AESTool.AES_KEY));
					jsonSend.put("bind_passwd", AESTool.getInstance().content(devicePasswd, AESTool.AES_KEY));
					jsonSend.put("bind_domain", AESTool.getInstance().content(deviceDomain, AESTool.AES_KEY));
					jsonSend.put("verifycode", AllenEncode.AllenEncode(deviceId + devicePasswd));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "bind--username = " + username + ", deviceId = " + deviceId + ", devicePasswd = " + devicePasswd + ", deviceDomain = " + deviceDomain);//
				} catch (JSONException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String urlString = DOMAIN_HTTP + "HttpUserBind?param=" + jsonSend.toString();
					JSONTokener jsonParser = new JSONTokener(DataDownLoader.DownLoad(urlString));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--bind = " + jsonResult.toString());
					Log.v(TAG, "code = " + jsonResult.getInt("code"));
					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message bindSuccessMsg = new Message();
							bindSuccessMsg.what = DefMsgConstants.MSG_BIND_SUCCESS;
							Bundle bundle = new Bundle();
							bundle.putString("bindBarcode", jsonResult.optString("barcode"));
							bundle.putString("bindNickname", jsonResult.optString("nickname"));
							bundle.putString("bindDevId", deviceId);
							bundle.putString("bindDevDomain", deviceDomain);
							bindSuccessMsg.obj = bundle;
							sipHandler.sendMessage(bindSuccessMsg);
						} else if (jsonResult.optInt("code") == 401) {
							Message bindFailMsg = new Message();
							bindFailMsg.what = DefMsgConstants.MSG_BIND_EXCESSIVE;
							bindFailMsg.obj = deviceId;
							sipHandler.sendMessage(bindFailMsg);
						} else {
							Message bindFailMsg = new Message();
							bindFailMsg.what = DefMsgConstants.MSG_BIND_FAIL;
							bindFailMsg.obj = deviceId;
							sipHandler.sendMessage(bindFailMsg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//解绑，入参：设备编号
	public void UnbindDev(final String deviceId) {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					String username = getValueFromTable("username", "");
					jsonSend.put("cmd", "unbindDev");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("bind_user", AESTool.getInstance().content(deviceId, AESTool.AES_KEY));
					jsonSend.put("verifycode", AllenEncode.AllenEncode(username + deviceId));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("sipUser", "");
					Log.d(TAG, "unbind--deviceId = "+ deviceId + ", username = " + username);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpUserUnBind?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--bind = " + jsonResult.toString());
					Log.v(TAG, "code = " + jsonResult.optInt("code"));
					if (sipHandler != null) {
						if (jsonResult.optInt("code") == 200) {
							Message unbindSuccessMsg = new Message();
							unbindSuccessMsg.what = DefMsgConstants.MSG_UNBIND_SUCCESS;
							unbindSuccessMsg.obj = deviceId;
							sipHandler.sendMessage(unbindSuccessMsg);
						} else {
							Message unbindFailMsg = new Message();
							unbindFailMsg.what = DefMsgConstants.MSG_UNBIND_FAIL;
							unbindFailMsg.obj = deviceId;
							sipHandler.sendMessage(unbindFailMsg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//密码找回
	public void findPasswd(final String user) {
		new Thread(){
			public void run() {
				validateMobile(user);
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					jsonSend.put("cmd", "retrievePasswd");
					jsonSend.put("user", AESTool.getInstance().content(user, AESTool.AES_KEY));
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("msg_type", getValueFromTable("validateMobile", ""));
					jsonSend.put("hsyToken", tokenSSO);
					jsonSend.put("sipUser", "");
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpRetrievePasswd?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--bind = " + jsonResult.toString());

					if (sipHandler != null) {
						Message resultMsg = new Message();
						if (jsonResult.optInt("code") == 200) {
							resultMsg.what = DefMsgConstants.MSG_FIND_PASSWD_SUCCESS;
							sipHandler.sendMessage(resultMsg);
						} else {
							resultMsg.what = DefMsgConstants.MSG_FIND_PASSWD_FAIL;
							sipHandler.sendMessage(resultMsg);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//密码重置
	public void resetPasswd(final String user, final String passwd, final String verifyCode) {
		new Thread(){
			public void run() {
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					jsonSend.put("cmd", "resetPasswd");
					jsonSend.put("user", AESTool.getInstance().content(user, AESTool.AES_KEY));
					jsonSend.put("passwd", AESTool.getInstance().content(passwd, AESTool.AES_KEY));
					jsonSend.put("verifycode", verifyCode);
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("msg_type", getValueFromTable("validateMobile", ""));
					jsonSend.put("hsyToken", tokenSSO);
					jsonSend.put("sipUser", "");
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpResetPasswd?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--bind = " + jsonResult.toString());

					if (sipHandler != null) {
						Message resultMsg = new Message();
						if (jsonResult.optInt("code") == 200) {
							resultMsg.what = DefMsgConstants.MSG_RESET_PASSWD_SUCCESS;
							sipHandler.sendMessage(resultMsg);
						} else {
							resultMsg.what = DefMsgConstants.MSG_RESET_PASSWD_FAIL;
							sipHandler.sendMessage(resultMsg);
						}
					}
				} catch (JSONException e) {
					sipHandler.sendEmptyMessage(DefMsgConstants.MSG_PASSWORD_SET_TIME_OUT);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//密码修改
	public void modifyPasswd(final String oldPasswd, final String newPasswd) {
		Log.i(TAG, "modifyPasswd: newPasswd-" + newPasswd + ", token: " + token);
		new Thread(){
			public void run() {
				String username = getValueFromTable("username", "");
				validateMobile(username);
				String currentTime = String.valueOf(System.currentTimeMillis());
				JSONObject jsonSend = new JSONObject();
				try {
					jsonSend.put("cmd", "modifyPassword");
					jsonSend.put("user", AESTool.getInstance().content(username, AESTool.AES_KEY));
					jsonSend.put("oldPassword", AESTool.getInstance().content(oldPasswd, AESTool.AES_KEY));//AllenEncode.AllenEncode(oldPasswd).substring(0, 10)
					jsonSend.put("newPassword", AESTool.getInstance().content(newPasswd, AESTool.AES_KEY));//AllenEncode.AllenEncode(newPasswd).substring(0, 10)
					jsonSend.put("timeStamp", currentTime);
					jsonSend.put("accessToken", AllenEncode.AllenEncode(currentTime + httpKey));
					jsonSend.put("msg_type", getValueFromTable("validateMobile", ""));
					jsonSend.put("hsyToken", token);
					jsonSend.put("sipUser", "");
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONTokener jsonParser = new JSONTokener(getResultByHttp(DOMAIN_HTTP + "HttpModifyPassword?param=" + jsonSend.toString()));//iot.hisense.com
					JSONObject jsonResult = (JSONObject) jsonParser.nextValue();
					Log.v(TAG, "jsonobj--bind = " + jsonResult.toString());

					if (sipHandler != null) {
						Message resultMsg = new Message();
						if (jsonResult.optInt("code") == 200) {
							resultMsg.what = DefMsgConstants.MSG_MODIFY_PASSWD_SUCCESS;
							sipHandler.sendMessage(resultMsg);
						} else {
							resultMsg.what = DefMsgConstants.MSG_MODIFY_PASSWD_FAIL;
							sipHandler.sendMessage(resultMsg);
						}
					}
				} catch (JSONException e) {
					sipHandler.sendEmptyMessage(DefMsgConstants.MSG_MODIFY_PASSWD_TIME_OUT);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	//获取http返回结果
	public String getResultByHttp(String strUrl) throws Exception {

		return DataDownLoader.DownLoad(strUrl);
	}

	private Handler sipHandler = null;

	public void setHandler(Handler handler) {
		this.sipHandler = handler;
	}

	/***************** 局域网接口 start *******************/
	/**
	 * @Title: getDeviceList
	 * @Description: 获取设备列表
	 * @param @return
	 * @return String
	 */
	public String getDeviceList() {
		return Iot_JNI_Interface.hs_lan_discovery_get();
	}

	/**
	 * @Title: initLan
	 * @Description: 局域网设备初始化,call before getDeviceList(),耗时比较久，所以开线程
	 * @param @return
	 * @return boolean
	 */
	public void initDevInLan() {
		new Thread() {
			@Override
			public void run() {
				Iot_JNI_Interface.hs_lan_discovery_init();
			}
		}.start();
	}

	/**
	 * @Title: sendMsgInLan
	 * @Description: 局域网消息发送接口
	 * @param from_user
	 *            ：发送用户名
	 * @param to_user
	 *            ：接收用户名
	 * @param msg_body
	 *            ：发送的消息内容
	 * @return boolean
	 */

	public boolean sendMsgInLan(String from_user, String to_user,
			String msg_body) {
		boolean res = Iot_JNI_Interface.hs_lan_message_send(from_user, to_user,
				msg_body) == 0 ? true : false;
		return res;
	}

	/***************** 局域网接口 end *******************/

	/**
	 * @Title: sendMsgInWAN
	 * @Description: 广域网发送消息
	 * @param ：to 用户名
	 * @param ：usrname 用户名
	 * @param ：msg 消息实体
	 * @return Boolean
	 * @modify:By CaoXiuixia
	 */
	public int sendMsgInWAN(String to, String usrname, String msg,String domainStr) {
		/*boolean res = Iot_JNI_Interface.hs_iot_sendmsg(to, usrname, msg) == 0 ? true
				: false;*/
		int res = -4;
		if(domainStr != null && !domainStr.equals("")   ){
			res = Iot_JNI_Interface.hs_iot_sendmsg(to, usrname, msg, domainStr);
		}else{
			Log.e(TAG,"******************************DEFAULT :");
			res = Iot_JNI_Interface.hs_iot_sendmsg(to, usrname, msg, DOMAIN);
		}
		Log.e(TAG,"iot sendMsgInWAN res :" +res);
		if(res == -2){
		   //长按home键，手机闲置一段时间之后，会出现iot的资源被回收，服务器返回-2为标识;
		   //重新初始化
			reInit();
			if(domainStr != null && !domainStr.equals("")){
				res= Iot_JNI_Interface.hs_iot_sendmsg(to, usrname, msg, domainStr);
			}else{
				res= Iot_JNI_Interface.hs_iot_sendmsg(to, usrname, msg, DOMAIN);
			}

		}
		return res;
	}

	//获取手机的IP地址
	public String getPhoneIpAddress() {
		if (mContext != null) {
			if (detectNetwork()) {
				if (getCurrentNetType().equals("wifi")) {
					return getWifiIpAddress();
				} else {
					return getLocalIpAddress();
				}
			}
		}
		return "";
	}

	private boolean detectNetwork() {
		ConnectivityManager manager = null;
		if (mContext != null) {
			manager = (ConnectivityManager)mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		}
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
	}

	private String getCurrentNetType() {
		String type = "";
		NetworkInfo info = null;
		if (mContext != null) {
			ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			info = cm.getActiveNetworkInfo();
		}

		if (info == null) {
			type = "null";
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = "wifi";
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
			|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
				type = "2g";
			} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
			|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
			|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
				type = "3g";
			} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
				type = "4g";
			}
		}
		return type;
	}

	private String getWifiIpAddress() {
		if (mContext != null) {
			WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	        // 获取32位整型IP地址
	        int ipAddress = wifiInfo.getIpAddress();
	        //返回整型地址转换成“*.*.*.*”地址
	        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
		}
        return "";
    }

	//3G
	private String getLocalIpAddress(){
         try{
             Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
             while(en.hasMoreElements()){
                     NetworkInterface nif = en.nextElement();
                     Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
                     while(enumIpAddr.hasMoreElements()){
                             InetAddress mInetAddress = enumIpAddr.nextElement();
                             /*if(!mInetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(mInetAddress.getHostAddress())){
                                     return mInetAddress.getHostAddress().toString();
                             }*/
                     }
             }
         }catch(SocketException ex){
             Log.e(TAG, "get local ip address fail");
         }
         return "";
	 }

	public String getValueFromTable(String key, String defaultValue){
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		    String value = sp.getString(key, defaultValue);
		    return value;
		}
	    return "";
	}

	public void putValueToTable(String key, String value){
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	//海视云登录
	private void haiSignon(final String usr, final String pw) {
		new Thread() {
			@Override
			public void run() {
				appAuthBlock();
				HashMap<String, String> map = new HashMap<String, String>();
				if(mContext==null){
					Log.e(TAG, "mContext=null");
				}
				map.put(Params.DEVICEID, DeviceConfig.getDeviceId(mContext));
				map.put(Params.LOGINNAME, usr);
				map.put(Params.PASSWORD, pw);
				map.put(Params.APPCODE, appCode);
				HiSDKInfo  info = new HiSDKInfo();
				HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
				SignonReplyInfo reply = service.signon(map);
				if(reply != null && reply.getReply() == 0)	{
					Log.d(TAG, "signon hai_shi_yun success");
					token = reply.getToken();//海视云登录成功后，获取到的token

					createNewUser(handler, usr, pw, true);
				} else {
					Log.e(TAG, "hai_shi_yun signon fail");//海视云登录失败，不用出来，直接登录sip
					sipLogin(usr, pw);
				}
			}
		}.start();
	}

	/**检查手机号是否已经注册过*/
	public void validateMobile(final String mobilePhone) {
		appAuthSSOBlock();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Params.MOBILEPHONE, mobilePhone);
		HiSDKInfo  info = new HiSDKInfo();
		info.setToken(tokenSSO);
		HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
		ReplyInfo reply = service.validateMobile(map);
		if (reply != null) {
			Log.v(TAG, "validateMobile--reply: " + reply.getReply());
			if (reply.getReply() == 0) {
				putValueToTable("validateMobile", "0");
			} else {
				putValueToTable("validateMobile", "1");
			}
		}
	}

	//开线程检查手机号是否已经注册过
	public void validateMobileThread(final String mobilePhone) {
		new Thread() {
			@Override
			public void run() {
				appAuthSSOBlock();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Params.MOBILEPHONE, mobilePhone);
				HiSDKInfo  info = new HiSDKInfo();
				info.setToken(tokenSSO);
				HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
				ReplyInfo reply = service.validateMobile(map);
				if (reply != null) {
					Log.v(TAG, "validateMobile--reply: " + reply.getReply());
					if (reply.getReply() == 0) {
						putValueToTable("validateMobile", "0");
					} else {
						putValueToTable("validateMobile", "1");
					}
				}
			}
		}.start();
	}

	//hai shi yun log out
	private void haiLogout() {
		new Thread() {
			@Override
			public void run() {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Params.DEVICEID, DeviceConfig.getDeviceId(mContext));
				HiSDKInfo  info = new HiSDKInfo();
				info.setToken(token);
				HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
				ReplyInfo reply = service.logout(map);
				Log.d(TAG, "logout hai_shi_yun start");
				if(reply != null && reply.getReply() == 0)	{
					Log.d(TAG, "logout hai_shi_yun success");
				}
			}
		}.start();
	}

	/** 应用认证 获取appCode（线程阻塞）*/
	public void appAuthBlock() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Params.APPKEY, appKey);
		map.put(Params.APPSECRET, appSecret);
		HiSDKInfo  info = new HiSDKInfo();
		HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
		for(int i=0;i<3;i++){
			AppCodeReply reply = service.appAuth(map);
			if(reply != null && reply.getReply() == 0)	{
				appCode = reply.getCode();
				Log.i(TAG, "get appCode success--appCode: " + appCode);
				break;
			}else{
				Log.e(TAG, "get appCode failed!!!!!!!!!!");
			}
		}
	}

	/**SSO应用认证, 有用户登录时返回token, 否则返回appCode（线程阻塞）*/
	public void appAuthSSOBlock() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Params.APPKEY, appKey);
		map.put(Params.APPSECRET, appSecret);
		map.put(Params.DEVICEID, DeviceConfig.getDeviceId(mContext));
		HiSDKInfo  info = new HiSDKInfo();
		HiCloudAccountService service = HiCloudServiceFactory.getHiCloudAccountService(info);
		AppCodeSSO appCodeSSO1 = service.appAuthSSO(map);
		if (appCodeSSO1 == null || appCodeSSO1.getReply() == 1) {
            Log.d(TAG, "认证, mChcaService=null");
        } else if (appCodeSSO1.getReply() == 2) { // 有SSO, 认证成功
        	appCode = appCodeSSO1.getCode();
        	token = appCodeSSO1.getToken();
            Log.d(TAG, "有SSO, 认证成功");
        } else { // 无SSO, 继续匿名认证
            Log.d(TAG, "无SSO, 继续匿名认证");
            appCodeSSO = appCodeSSO1.getCode();
            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put(Params.APPCODE, appCodeSSO);
            map1.put(Params.LOGINNAME, "");
            map1.put(Params.DEVICEID, DeviceConfig.getDeviceId(mContext));
            SignonReplyInfo signonReply = service.signon(map1); //signon
            if (signonReply == null || signonReply.getReply() == 1) {
                Log.d(TAG, "signon error:" + (signonReply == null ? "" : signonReply.getError().getErrorCode()));
            } else {
            	tokenSSO = signonReply.getToken();
            }
        }
	}

}
