package com.tianren.methane.constant;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Constant {
    /**
     * http请求的baseUrl
     */
//    public static final String BASE_URL = "http://engineerlee.top:8080/";
//    public static final String TIANREN_URL = "";
    //http://engineerlee.top:8080/entry/entryAnaerobicTankData?anaerobicTankData
    public static final String TIANREN_URL = "tianren/";
    public static final String BASE_URL = "http://iot.tianren.com:8080/";

    /**
     * activity页面传参
     */
    public static final int INTENT_TYPE_YANYANG = 1;//厌氧列表
    public static final int INTENT_TYPE_QIGUI = 2;//气柜列表
    /**
     * http接口
     */
    public static final String ENTRYYANYANG_URL = TIANREN_URL + "entry/entryAnaerobicTankData";
    public static final String ENTRYCAPACITY_URL = TIANREN_URL + "entry/entryCapacity";
    public static final String ENTRYCONSUMPTION_URL = TIANREN_URL + "entry/entryConsumptionData";
    public static final String ENTRYALARM_URL = TIANREN_URL + "alarm/getAlarmListForApp";//获取报警通知
    public static final String ENTRYSTATIC_URL = TIANREN_URL + "detailedData/appStaticData";//获取静态表数据
    public static final String ENTRYREALDATA_URL = TIANREN_URL + "detailedData/appGetRealTimeData";//获取静态表数据

    public static final int MSG_LOGIN_SUCCESS = 4;
    public static final int MSG_LOGIN_FAILURE = 5;
    public static final int MSG_LOAD_IMAGE = 8;
    public static final int MSG_LOGIN_TIME_OUT = 9;
    public static final int TIME_OUT_LOGIN = 15000;//登录超时
}
