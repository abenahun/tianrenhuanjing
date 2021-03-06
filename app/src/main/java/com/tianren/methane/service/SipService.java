package com.tianren.methane.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.hisense.hs_iot_interface.CallBackFun;
import com.tianren.methane.jniutils.MsgBroadcastReceiver;
import com.tianren.methane.jniutils.MyCallBack;
import com.tianren.methane.jniutils.MyInterface;

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
}
