package com.tianren.acommon.service;

import com.google.gson.reflect.TypeToken;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.SensorBean;
import com.tianren.acommon.remote.BaseWebService;
import com.tianren.acommon.remote.WebTask;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Qiu
 * @date 2018/5/18
 */
public class DetailedDataService extends BaseWebService {

    /**
     * 获取静态表数据
     *
     * @return
     */
    private String APPSTATICDATA = TIANREN_URL + "detailedData/appStaticData";

    public WebTask<BaseResponse<Map<String, SensorBean>>> appStaticData() {
        Map<String, Object> p = new HashMap<>();
        return request(APPSTATICDATA, p, new TypeToken<BaseResponse<Map<String, SensorBean>>>() {
        }.getType());
    }

    /**
     * 获取真实数据（后面改成sip接口获取）
     *
     * @return
     */
    private String APPGETREALTIMEDATA = TIANREN_URL + "detailedData/appGetRealTimeData";

    public WebTask<BaseResponse<Map<String, String>>> appGetRealTimeData() {
        Map<String, Object> p = new HashMap<>();
        return request(APPGETREALTIMEDATA, p, new TypeToken<BaseResponse<Map<String, String>>>() {
        }.getType());
    }

}
