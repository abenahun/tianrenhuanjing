package com.tianren.methane.jniutils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tianren.methane.bean.DevInfo;
import com.tianren.methane.constant.DefContants;
import com.tianren.methane.constant.MsgDefCtrl;

/**
 * @ClassName: ControlCommut
 * @Description: 主要完成client端查询设备端的数据解析
 * @date 2014-9-6 下午2:05:53
 */

public class ParseDataFromDev {
	private static String TAG = "ParseDataFromDev";
	private static ParseDataFromDev controlCommut = null;
	private static final int SET_CHECK_TIME =10000;//10 * 1000s，查询一次
	public static boolean result_query = false;
	private boolean isChecking = false;// 发送定时查询命令的开关，由各个界面自己管理
	private final int TEMP_DIFFER_VALUE = 40;//冰箱三区温度数据：返回来的数据-40，发给冰箱的+40
	private ArrayList<String> devList = new ArrayList<String>();
	private List<DevInfo>  lists;
	private static final int MSG_START_COMPARE_DEVLIST = 600;
	
	//cxx，发查询的时候，20s之后某个设备不存在于list中，则认为是下线，mode=10
	private final static int time_compare = 32*1000;//在设备列表界面,每
	private int startCompareMsg_cnt = 0;
	private ParseDataFromDev() { 
		// TODO Auto-generated constructor stub
	}
	public static ParseDataFromDev getInstance() {
		if (controlCommut == null) {
			controlCommut = new ParseDataFromDev();
		}
		return controlCommut;
	}


	/**
	 * @Title: freshQueryStatusFromAc
	 * @Description: TODO
	 * @param :data
	 * @param :devType,根据张立震 TI设备的存存储能力，直接一次性返回查询的数据。 而高通的设备，只能分两次返回查询的数据;
	 * @return void
	 * @throws
	 */
	public void freshQueryStatusFromAc(String data, String devType,
			int uictrlnum) {
		try {
			Log.d(TAG, "-----data--->" + data);
			// 手机端设置状态参数之后,空调给手机设备返回查询cmdType
			// JSONObject jsonObject = new JSONObject(data);
			if (devType.equals("HP")) {
				// int uictrl_query_flag = jsonObject.getInt("uictrlnum");
				int uictrl_query_flag = uictrlnum;
				if (uictrl_query_flag == 1) {// 得到主控界面的数据
					getMainStaFromAcData(data);
				} else if (uictrl_query_flag == 2) {
					getMoreStaFromAC(data);
				}
			} else if (devType.equals("TI")) {
				getAllAcStatusFromAc(data);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.getCause();
			e.printStackTrace();
		}
	}

	public void freshSetStatusFromAc(Handler handler, String data) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			int cmdtype = jsonObject.getInt("cmdtype");
			switch (cmdtype) {
			case 204:// 设置
				Log.d(TAG, "--into 204------");
				boolean res = saveSettingAcStatus(data);
				if (res) {// 设置成功
					Log.d(TAG, "parse into 204 SET_RESULT = true");
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_AC_FRESH_CTRL_SET, data);
					handler.sendMessage(msg);
				} else {
					Log.d(TAG, "parse into 204 SET_RESULT = false");
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_AC_NOT_FRESH_CTRL_SET, data);
					handler.sendMessage(msg);
				}
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getCause();
			e.printStackTrace();
			// 是否写查询失败
		}
	}

	/**
	 * @Title: saveAcStatus
	 * @Description: 获取设置的返回信息
	 * @param ：data
	 * @return void
	 * @throws
	 */
	public boolean saveSettingAcStatus(String data) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
			if (jsonObject.optInt("result") == 0) {
				Log.d(TAG, "parse set result is   true  !");
				// 把结果写入变量中
				String devID = jsonObject.optString("devicename", null);
				String cmd = jsonObject.optString("setaction", null);

				return true;

			} else {
				return false;
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			e1.getCause();
			return false;
		}
	}

	/**
	 * @Title: getMainStaFromAcData
	 * @Description: 查询获取主控界面的空调参数
	 * @param @param data
	 * @return void
	 * @throws
	 */
	private void getMainStaFromAcData(String data) {
		Log.d(TAG, "getMainStaFromAcData--->" + data.toString());
		try {
			JSONObject jsonObject = new JSONObject(data);
			;
			String devID = jsonObject.optString("devicename", null);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			e1.getCause();
		}

	}

	/**
	 * @Title: getMoreStaFromAC
	 * @Description: 查询获取更多界面的空调参数
	 * @param ： data，回调函数notify_Received_Message（msg）返回的数据
	 * @return void
	 * @throws
	 */
	public void getMoreStaFromAC(String data) {
		Log.d(TAG, "getMoreStaFromAC");
		try {
			JSONObject jsonObject = new JSONObject(data);
			String devID = jsonObject.optString("devicename", null);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			e1.getCause();
		}
	}

	/**
	 * @Title: getAllAcStatusFromAc
	 * @Description: 获取空调全部的状态值（查询的）
	 * @param @param data
	 * @throws
	 */
	private void getAllAcStatusFromAc(String data) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			String devID = jsonObject.optString("devicename", null);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			e1.getCause();
		}
	}

	/**
	 * @Title: checkOnce
	 * @Description: 发送一次查询命令，to设备；
	 * @return void
	 */
	public void checkOnce(List<DevInfo> lists) {
		Log.d(TAG, "check once dev info ----->"+DevInfo.TYPE_FR);
		if (lists != null) {
			for (int i = 0; i < lists.size(); i++) {
				switch (lists.get(i).getType()) {
				case DevInfo.TYPE_AC:
					CommandDev.getCmdDevInstance().InitSendQueryCmd();
					CommandDev.getCmdDevInstance().sendCmdToDev(
							lists.get(i).getDevId(),lists.get(i).getDomain());
					break;
				case DevInfo.TYPE_FR:
					CommandDev.getCmdDevInstance().InitSendQueryFrigerCmd(lists.get(i).getDevId());
					CommandDev.getCmdDevInstance().sendCmdToDev(
						lists.get(i).getDevId(),lists.get(i).getDomain());
					break;
				case DevInfo.TYPE_WS:
					CommandDev.getCmdDevInstance().InitSendQueryWasherCmd(lists.get(i).getDevId());
					CommandDev.getCmdDevInstance().sendCmdToDev(
							lists.get(i).getDevId(),lists.get(i).getDomain());
					break;
				}
			}

		}
	}

	public void checkOnTime(List<DevInfo> lists) {
		// 定时发送查询指令，all device
		Log.e(TAG," isChecking  :"+isChecking);
		//if (isChecking == false) {
			Log.e(TAG,"******************SEND TIMING MSG*********************");
			Message message = new Message();
			message.what = MsgDefCtrl.MSG_TIMING_QUERY;
			message.obj = lists;
			/*去掉该部分,Bug #1194设备断网时，我的家电界面下的设备状态（温度、模式、在线）不更新。
			 * mHandler.removeMessages(MsgDefCtrl.MSG_TIMING_QUERY);
			 * mHandler.removeMessages(MSG_COMPARE_QUERY);//设备列表闪灭的问题15版本
			 */			
			isChecking = true;
			mHandler.sendMessage(message);
			
		//}
	}

	public void stopCheckOnTime() {
		if (isChecking == true) {
			isChecking = false;
			mHandler.removeMessages(MsgDefCtrl.MSG_TIMING_QUERY);
			mHandler.removeMessages(MsgDefCtrl.MSG_COMPARE_QUERY);//2015.04.10
			startCompareMsg_cnt = 0;
		}
	}
	/**
	 * @Title: freshSetStatusFromFriger
	 * @Description: 处理冰箱设置相关的
	 * @param @param msgData
	 * @return void
	 * @author:CaoXiuxia
	 */
	public void freshSetStatusFromDev(Handler handler, String msgData) {
		try {
			JSONObject jsonObject = new JSONObject(msgData);
			int cmdtype = jsonObject.getInt("cmdtype");
			switch (cmdtype) {
			case 304:
				boolean res = saveSettingFrigerStatus(msgData);
				if (res) {// 设置成功
					Log.d(TAG, "parse into 304 SET_RESULT = true");
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_FRIGER_FRESH_CONTROL, msgData);
					handler.sendMessage(msg);
					
				} else {
					Log.d(TAG, "parse into 304 SET_RESULT = false");
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_FRIGER_NOT_FRESH_CONTROL, msgData);
					handler.sendMessage(msg);
				}
				break;
			case 404:
				boolean res_set = saveSettingWasherStatus(msgData);
				if(res_set){
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_WASHER_FRESH_CONTROL, msgData);
					handler.sendMessage(msg);
				}else{
					Message msg = handler.obtainMessage(
							MsgDefCtrl.MSG_WASHER_NOT_FRESH_CONTROL, msgData);
					handler.sendMessage(msg);
				}
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}


	/**
	 *@Describe:存储从冰箱端获取的设置参数
	 *@author:CaoXiuxia
	 *@time:2015-5-5上午10:48:10
	 */
	private boolean saveSettingFrigerStatus(String msgData) {
		JSONObject jsonObject_all = null;
		try {
			jsonObject_all = new JSONObject(msgData);
			if (jsonObject_all.getInt("result") == 0) {
				String devID = jsonObject_all.optString("devicename", null);
				Log.d(TAG, "parse fridge devID   :"+devID);
				String cmd = jsonObject_all.optString("setaction", null);
				Log.d(TAG, "setaction parse  :"+cmd);
				return true;
			} else {
				
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param handler
	 * @param msgData
	 * @TODO:查询冰箱的状态
	 */
	public int freshQueryStatusFromDev(Handler handler,String msgData){
		int res = -1;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(msgData);
			String devID = jsonObject.optString("devicename", null);
			if(devList.size() == 0){
				devList.add(devID);
				Log.i(TAG, "xxxxxxxxxxxx query msgData devid --->"+devID);
			}
			boolean isEqualFlag = false;
			for(int i=0;i<devList.size();i++){
				if (devList.get(i).equals( devID)){
					isEqualFlag = true;
				}
			}
			if(isEqualFlag == false){
				devList.add(devID);
				Log.d(TAG, "xxxxxxxxxxxx query msgData devid --->"+devID);
			}
			int cmdtype = jsonObject.getInt("cmdtype");
			switch (cmdtype) {
			case 301:
				boolean is301Ok = freshQueryStatusFromRefrig(handler,msgData);
				if (is301Ok) {
					res = 301;
				}
				break;
			case 401:
				boolean is401Ok = freshQueryStatusFromWasher(handler,msgData);
				if (is401Ok) {
					res = 401;
				}
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = -1;
		}
		return res;
	}
	
	/**
	 * @Title: freshQueryStatusFromRefrig
	 * @Description: 处理冰箱查询相关的,并保存查询后的数据（模式和温度）
	 * @param : msgData
	 * @return void
	 */
	
	
	public boolean freshQueryStatusFromRefrig(Handler handler,String msgData) {
		try {
			JSONObject jsonObject = new JSONObject(msgData);
			Log.e(TAG, "query msgData--->"+msgData);
			String devID = jsonObject.optString("devicename", null);
			Log.e(TAG, "xxxxxxxxxxxx query msgData devid --->"+devID);
//			if(devList.size() == 0){
//				devList.add(devID);
//				Log.i(TAG, "xxxxxxxxxxxx query msgData devid --->"+devID);
//			}
//			boolean isEqualFlag = false;
//			for(int i=0;i<devList.size();i++){
//				if (devList.get(i).equals( devID)){
//					isEqualFlag = true;
//				}
//			}
//			if(isEqualFlag == false){
//				devList.add(devID);
//				Log.d(TAG, "xxxxxxxxxxxx query msgData devid --->"+devID);
//			}
			return true;
			//测试用
//			for (int i = 0; i < SlidFragActivity.getStatusList().size(); i++) {
//				Log.i(TAG, "freshQueryStatusFromRefrig--status list[" + i + "], id = " + SlidFragActivity.getStatusList().get(i).getmDevID() + ", freezer temp = " + SlidFragActivity.getStatusList().get(i).getmFreezingTemp());
//			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/****************washer 数据解析************************/
	public boolean freshQueryStatusFromWasher(Handler handler,String msgData){
		boolean res = false;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(msgData);
			Log.e(TAG, "query msgData--->"+msgData);
			String devID = jsonObject.optString("devicename", null);
			Log.e(TAG, "query washer msgData devid --->"+devID);

			res = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}
		return res;
	}
		
	public boolean saveSettingWasherStatus(String msgData){
		boolean res = false;
		JSONObject jsonObject_all = null;
		try {
			jsonObject_all = new JSONObject(msgData);
			if(jsonObject_all.getInt("result") == 0){
				String devID = jsonObject_all.optString("devicename", null);
				Log.d(TAG, "parse washer devID   :"+devID);
				
			}
			res = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	/****************************************/
	
	private MyHandler mHandler = new MyHandler(this);
	public static class MyHandler extends Handler {
		WeakReference<ParseDataFromDev> activityWeakReference;
		MyHandler(ParseDataFromDev obj) {
			this.activityWeakReference = new WeakReference<ParseDataFromDev>(
					obj);
		}

		@Override
		public void handleMessage(Message msg) {
			final ParseDataFromDev obj = activityWeakReference.get();
			switch (msg.what) {
			case MsgDefCtrl.MSG_TIMING_QUERY:
				obj.mHandler.sendEmptyMessage(MSG_START_COMPARE_DEVLIST);
				Log.e(TAG,"=====MSG_TIMING_QUERY :"+obj.isChecking);
				if (obj.isChecking) {
					activityWeakReference.get().lists = (List<DevInfo>) msg.obj;
					for (int i = 0; i < activityWeakReference.get().lists.size(); i++) {
						Log.v(TAG, "activityWeakReference.get().lists[" + i + "] dev id: " + activityWeakReference.get().lists.get(i).getDevId());
					}
					if (activityWeakReference.get().lists != null) {
						for (int i = 0; i < activityWeakReference.get().lists.size(); i++) {
							/*if (activityWeakReference.get().lists.get(i).getType() == DevInfo.TYPE_AC) {
								CommandDev.getCmdDevInstance().InitSendQueryCmd();
								CommandDev.getCmdDevInstance().sendCmdToDev(
										activityWeakReference.get().lists.get(i).getDevId(),activityWeakReference.get().lists.get(i).getDomain());
							} else if (activityWeakReference.get().lists.get(i).getType() == DevInfo.TYPE_FR) {
								CommandDev.getCmdDevInstance().InitSendQueryFrigerCmd(activityWeakReference.get().lists.get(i).getDevId()
										);
								CommandDev.getCmdDevInstance().sendCmdToDev(
										activityWeakReference.get().lists.get(i).getDevId(),activityWeakReference.get().lists.get(i).getDomain());
							}*/
								switch (activityWeakReference.get().lists.get(i).getType()) {
								case DevInfo.TYPE_AC:
									CommandDev.getCmdDevInstance().InitSendQueryCmd();
									CommandDev.getCmdDevInstance().sendCmdToDev(
											activityWeakReference.get().lists.get(i).getDevId(),activityWeakReference.get().lists.get(i).getDomain());
									break;
								case DevInfo.TYPE_FR:
									CommandDev.getCmdDevInstance().InitSendQueryFrigerCmd(activityWeakReference.get().lists.get(i).getDevId()
											);
									CommandDev.getCmdDevInstance().sendCmdToDev(
											activityWeakReference.get().lists.get(i).getDevId(),activityWeakReference.get().lists.get(i).getDomain());
									break;
								case DevInfo.TYPE_WS:
									CommandDev.getCmdDevInstance().InitSendQueryWasherCmd(activityWeakReference.get().lists.get(i).getDevId()
											);
									CommandDev.getCmdDevInstance().sendCmdToDev(
											activityWeakReference.get().lists.get(i).getDevId(),activityWeakReference.get().lists.get(i).getDomain());
									break;
								}
						}

					}
					Message message = new Message();
					message.copyFrom(msg);
					sendMessageDelayed(message,SET_CHECK_TIME);
				}
				break;
		
			case MSG_START_COMPARE_DEVLIST://bug:1194,20150417
				obj.startCompareMsg_cnt++;
				Log.e(TAG,"start compare devlist cnt :"+obj.startCompareMsg_cnt);
				//在设备列表界面，关屏过段时间再打开，在线变为离线问题。界面停止时停止MSG_COMPARE_QUERY
				if(activityWeakReference.get().isChecking == true && obj.startCompareMsg_cnt%3 ==0){//每30S对比一次 ，3*10s
					obj.mHandler.sendEmptyMessage(MsgDefCtrl.MSG_COMPARE_QUERY);
				}
				if(obj.startCompareMsg_cnt > 210){//循环210次计数清0
					obj.startCompareMsg_cnt =0;
				}
				removeMessages(MSG_START_COMPARE_DEVLIST);
				break;
			case MsgDefCtrl.MSG_COMPARE_QUERY://冰箱下线状态处理
				obj.mHandler.removeMessages(MSG_START_COMPARE_DEVLIST);
				Log.e(TAG," compare off/on line list ");
				if(activityWeakReference.get().lists !=null ){
				/*if (activityWeakReference.get().lists.size() == activityWeakReference.get().devList.size()){
						Log.e(TAG,"=================  list 相等 ");
						break;
				}else{*/
					for(int i=0;i<obj.lists.size();i++){
						boolean isEqualFlag = false;
						Log.e(TAG,"mobile phone devlist :"+obj.lists.get(i).getDevId());
						for(int j=0;j<obj.devList.size();j++){
							if (obj.lists.get(i).getDevId().equals( obj.devList.get(j))){
								isEqualFlag = true;
							}
						}
						if(isEqualFlag == false){
							Log.e(TAG,"offline devlist :"+"["+i+"]"+obj.lists.get(i).getDevId());
//							if (SlidFragActivity.getStatusByID(obj.lists.get(i).getDevId()) !=null) {//注销死机
//								SlidFragActivity.getStatusByID(obj.lists.get(i).getDevId()).setModeFridge(DefContants.MODE_OFFLINE);
//							}
						}
					}
				/*}*/
					obj.devList.clear();
					Log.e(TAG,"**************ISCHECKING COMPARE :"+activityWeakReference.get().isChecking);
				}
				break;
			default:
				break;
			}
		}
	}
}
