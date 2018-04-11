package com.tianren.methane.constant;

/**
* @Athor: CaoXiuxia
* @ClassName: MsgDefCtrl
* @Description: 处理空调/冰箱等设备的查询/控制的相关消息;
* @date 2014-12-30 下午6:51:31
*
*/ 
public class MsgDefCtrl {
	//0<x<1000
	/************* 空调管理相关 ***********/

	public static final int JNI_MSG_SEND_SUCCESS = 201;
	public static final int JNI_MSG_RECEIVED = 202;// 设置204
	public static final int JNI_MSG_RECEIVED_QUERY = 203;// 查询201
	// 查询，主控界面cxx
	public static final int MSG_AC_FRESH_CTRLMAIN_RUERY = 204;
	public static final int MSG_AC_FRESH_CTRLMAIN_RUERY_CMD = 225;
	
	public static final int MSG_AC_NOT_FRESH_CTRLMIAN_RUERY = 205;
	// 查询,more界面更新
	public static final int MSG_AC_FRESH_CTRLMORE_RUERY = 206;
	public static final int MSG_AC_NOT_FRESH_CTRLMORE_RUERY = 207;
	// public static final int MSG_SEND_RUERY_CMD = 1208;
	public static final int MSG_SEND_SET_DEGREE_CMD = 209;
	public static final int MSG_TIMING_QUERY = 210;// 定时查询
	

	public static final int MSG_QUERY_AC_STATUS = 213;
	// 设置，更新界面
	public static final int MSG_AC_FRESH_CTRL_SET = 214;
	public static final int MSG_AC_NOT_FRESH_CTRL_SET = 215;// 设置失败
	// 优化成功
	// public static final int MSG_AC_FRESH_OPTIMIZE = 216;

	public static final int MSG_INIT_SPEECH_CONTROL = 217;// 初始化语音以及操控界面初始化
	// 优化
	public static final int MSG_DISSMISS_OPTIMIZE_DIALOG = 218;// 2秒消失
	// 提交，弹出tosat
	public static final int MSG_POST_DATA = 219;// 提交预约安装,预约保养,故障申报数据
	public static final int MSG_POST_DATA_FEED_EVALUDE = 220;// 提交意见反馈和评价数据
	// 智能优化 dialog弹出
	// public static final int MSG_SHOW_HINT_POPDIALOG = 221;//
	public static final int MSG_SHOW_POPDIALOG_OPTIMIZE_OK = 222;//
	public static final int MSG_IMAGE_LOAD = 223;//
	public static final int MSG_OPTIMIZE_OK = 224;//
	public static final int MSG_COMPARE_QUERY = 225;//设备列表对比消息
	
	
	/************* 冰箱管理相关 ***********/
	public static final int MSG_FRESH_REFRIGERATOR = 301;// 进入冰箱控制界面，自主刷新一次
	public static final int MSG_REMOVE_FRESH_REFRIGERATOR = 302;// remove掉301 msg
	// 设置，更新界面
	public static final int MSG_FRIGER_FRESH_CONTROL = 303;
	public static final int MSG_FRIGER_NOT_FRESH_CONTROL = 304;// 设置失败
	public static final int MSG_PROGRESS_LOADING = 305;
	public final static int FREEZING_CHANGE_TEMP = 306;	//速冻
	public final static int 	SUPERCOLL_CHANGE_TEMP = 307;//速冷
	public final static int 	SMART_CHANGE_TEMP = 308;	//智能
	public final static int 	ECO_CHANGE_TEMP = 309;		//节能
	public final static int 	HOLIDAY_CHANGE_TEMP = 310;	//假日
	public final static int 	CUSTOM_CHANGE_TEMP = 311;	//自定义
	public static final int SET_WHEEL_CURITEM_FREEZING = 312;
	public static final int SET_WHEEL_CURITEM_REFRIGERATE = 313;
	public static final int SET_WHEEL_CURITEM_VARIATEMP = 314;
	public static final int SET_WHEEL_CURITEM_REFRIGERHOLIDAY = 315;
	public static final int MSG_SETTING_TIMEOUT = 316;
	public static final int MSG_SEND_ALL_SETTINGCMD = 317;
	public static final int MSG_RECEIVED_NOTIFY = 318;
	public static final int MSG_FRIGER_FRESH_QUERY_OK = 319;
	
	
	
	
	//update today purlist
	public static final int MSG_UPLOAD_DEL_PURLIST_SUCESS = 357; 
	public static final int MSG_UPLOAD_DEL_PURLIST_FAILER = 358; 
	public static final int MSG_UPLOAD_PURLIST_FAILED = 359; 
	public static final int MSG_UPLOAD_REMARK_SUCEESS = 360;//no use
	public static final int MSG_UPLOAD_ADD_PURLIST_SUCESS = 361; 
	
	//washer 相关
	public static final int MSG_WASHER_FRESH_QUERY_OK = 381; 
	public static final int MSG_WASHER_FRESH_CONTROL = 382; 
	public static final int MSG_WASHER_NOT_FRESH_CONTROL = 383;
	
	
	/****** 应用相关 *****/
	// app 退出消息
	public static final int APPEXIT = 901;
}
