package com.tianren.methane.service;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.hisense.hs_iot_interface.CallBackFun;
import com.tianren.methane.constant.MsgDefCtrl;
import com.tianren.methane.jniutils.MsgBroadcastReceiver;
import com.tianren.methane.jniutils.MyCallBack;
import com.tianren.methane.jniutils.MyInterface;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SipService extends Service {

	private static final String TAG = "SipService";
	private static MyInterface myInterface = null;
	private static MyCallBack myCallBack = null;
	public static boolean ifAlreadyGetDevInfo = false;//用于设备列表界面，是否需要重新获取设备列表
	public static boolean isFirstEnterLogin = true;//如果是第一次进入登录界面，则有动画效果
	public static boolean networkTip = true;//给设备配网时，断网不需要提示
	public static boolean isFirstEnterHome = true;//是否第一次进入主页
	private MsgBroadcastReceiver mReceiver;//广播接收器，add by changchen
	private static Context mContext;
	
	public static Handler midlHandler;
	private String revmsg;
	private final String ERROR_REVMSG = "Talk to phone";
	
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
		myCallBack = new MyCallBack();
		myInterface = new MyInterface();
		mContext = getApplicationContext();
		myInterface.setmContext(mContext);
		CallBackFun.setCallfunc(myCallBack);
		Log.i(TAG, "onCreate--setCallfunc--isInitWan = " + myInterface.getIsInitWan());
		
		initImageLoaderConfig();
		myCallBack.setServiceHandler(serHandler);
		
		mReceiver = new MsgBroadcastReceiver(this);
		mReceiver.registerAction();
	}
	
	public static Handler getMidlHandler() {
		return midlHandler;
	}
	
	public static void setMidlHandler(Handler midlHandler) {
		SipService.midlHandler = midlHandler;
	}
	
	public static MyInterface getMyInterface() {
		if (myInterface == null) {
			Log.e(TAG, "myInterface == null");
			if (mContext != null) {
//				ToastCustom.makeText(mContext, mContext.getResources().getString(R.string.re_login), Toast.LENGTH_SHORT).show();
			}
		}
		return myInterface;
	}
	
	public static MyCallBack getMyCallBack() {
		if (myCallBack == null) {
			Log.e(TAG, "myCallBack == null");
			myCallBack = new MyCallBack();
			CallBackFun.setCallfunc(myCallBack);
		}
		return myCallBack;
	}
	
	

	// 设置图片下载策略
	public void initImageLoaderConfig() {
		/*DisplayImageOptions options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.a)
				// .showImageForEmptyUri(R.drawable.a)
				// .showImageOnFail(R.drawable.a)
				.cacheInMemory(true)
				.cacheOnDisc(true)

				// .displayer(new SimpleBitmapDisplayer())
				// RoundedBitmapDisplayer（int roundPixels）设置圆角图片
				// FakeBitmapDisplayer（）这个类什么都没做
				// FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
				// 　　　　　　　 SimpleBitmapDisplayer()正常显示一张图片　
//				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				// EXACTLY :图像将完全按比例缩小的目标大小
				// EXACTLY_STRETCHED:图片会缩放到目标大小完全
				// IN_SAMPLE_INT:图像将被二次采样的整数倍
				// IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
				// NONE:图片不会调整
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(5)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(options).build();

		ImageLoader.getInstance().init(config);
		Log.v(TAG, "initImageLoaderConfig over");*/
	}

	public static int idDrawable(Context context, String imageName) {
		Resources res = context.getResources();
		return res.getIdentifier(imageName, "drawable", context.getPackageName());
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
//		myInterface = null;
//		myCallBack = null;
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}
	/**
	 * @author CaoXiuxia
	 * @Describe：服务器与手机之间收发信息的中转
	 */
	private Handler serHandler = 
	new Handler() {
		public void handleMessage(Message msg) {
			revmsg = (String) msg.obj.toString();
			switch (msg.what) {
			case MsgDefCtrl.MSG_RECEIVED_NOTIFY:
				Log.v(TAG, "revmsg ："+revmsg);	
				if (revmsg != null && !revmsg.equals(ERROR_REVMSG) ){
					try {
						JSONTokener jsonParser = new JSONTokener(revmsg);
						JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
							//冰箱相关add by cxx
							if (jsonObject.optString("cmdtype").equals("304") || jsonObject.optString("cmdtype").equals("404")) {
								//设置冰箱的参数
								if (midlHandler != null) {
//									ParseDataFromDev.getInstance().freshSetStatusFromDev(midlHandler,revmsg);
								}
							}
							if (jsonObject.optString("cmdtype").equals("301") || jsonObject.optString("cmdtype").equals("401") ) {
								//查询冰箱的参数
								Log.w(TAG, "recv 301 :"+revmsg);
								int isQueryOk = -1;
//								isQueryOk = ParseDataFromDev.getInstance().freshQueryStatusFromDev(midlHandler,revmsg);
								switch (isQueryOk) {
								case 301:
									if (midlHandler != null) {
										midlHandler.sendEmptyMessage(MsgDefCtrl.MSG_FRIGER_FRESH_QUERY_OK);
									}
									break;
								case 401:
									if (midlHandler != null) {
										midlHandler.sendEmptyMessage(MsgDefCtrl.MSG_FRIGER_FRESH_QUERY_OK);
									}
									break;
								}
							}
							
							if(jsonObject.optString("cmdtype").equals("401")){
								if(midlHandler != null ){
//									boolean isOk = ParseDataFromDev.getInstance().freshQueryStatusFromWasher(midlHandler,revmsg);
//									if (isOk) {
//										midlHandler.sendEmptyMessage(MsgDefCtrl.MSG_WASHER_FRESH_QUERY_OK);
//									}
								}
							}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						Log.e(TAG,"recv msg format error! ");
						e1.printStackTrace();
					}						
				}else {
					Log.v(TAG, "revmsg = null !");
				}
				break;
				
			default:
				break;
			}
		};
	};
}
