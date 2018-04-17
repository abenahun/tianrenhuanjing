package com.tianren.methane.constant;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Constant {
    //http://engineerlee.top:8080/entry/entryAnaerobicTankData?anaerobicTankData
//    public static final String BASE_URL = "http://engineerlee.top:8080" ;
    public static final String BASE_URL = "http://iot.tianren.com:8080";
    public static final int INTENT_TYPE_YANYANG = 1;//厌氧列表
    public static final int INTENT_TYPE_QIGUI = 2;//气柜列表

    public static final String ENTRYYANYANG_URL = "/tianren/entry/entryAnaerobicTankData";
    public static final String ENTRYCAPACITY_URL = "/tianren/entry/entryCapacity";
    public static final String ENTRYCONSUMPTION_URL = "/tianren/entry/entryConsumptionData";

    public static final String YANYANG_URL = "anaerobicTankData";//厌氧
    public static final String CAPACITY_URL = "capacity";//产气量
    public static final String CONSUMPTION_URL = "consumption";//消耗

    public static final String ALLDATA_URL = "/tianren/detailedData";
    public static final String REALDATANAME_URL = "appGetRealTimeData";//实时数据
    public static final String STATICDATANAME_URL = "appStaticData";//静态表数据


    public static final int MSG_LOGIN_SUCCESS = 4;
    public static final int MSG_LOGIN_FAILURE = 5;
    public static final int MSG_LOAD_IMAGE = 8;
    public static final int MSG_LOGIN_TIME_OUT = 9;
    public static final int TIME_OUT_LOGIN = 15000;//登录超时

}
