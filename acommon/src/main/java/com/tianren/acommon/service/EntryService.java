package com.tianren.acommon.service;

import com.google.gson.reflect.TypeToken;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.CapacityBean;
import com.tianren.acommon.bean.ConsumptionBean;
import com.tianren.acommon.remote.BaseWebService;
import com.tianren.acommon.remote.WebTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Qiu
 * @date 2018/5/4
 */
public class EntryService extends BaseWebService {
    /**
     * 获取能耗
     *
     * @return
     */
    private String GET_CAPACITY = TIANREN_URL + "entry/getCapacity";

    public WebTask<BaseResponse<List<CapacityBean>>> getCapacity() {
        Map<String, Object> p = new HashMap<>();
        return request(GET_CAPACITY, p, new TypeToken<BaseResponse<List<CapacityBean>>>() {
        }.getType());
    }

    /**
     * 获取收益
     *
     * @return
     */
    private String GET_CONSUMPTIONDATA = TIANREN_URL + "entry/getConsumptionData";

    public WebTask<BaseResponse<List<ConsumptionBean>>> getConsumptionData() {
        Map<String, Object> p = new HashMap<>();
        return request(GET_CONSUMPTIONDATA, p, new TypeToken<BaseResponse<List<ConsumptionBean>>>() {
        }.getType());
    }

    /**
     * 获取历史数据
     *
     * @return
     */
    private String GET_HISTORICALDATA = TIANREN_URL + "entry/getHistoricalData";

    public WebTask<BaseResponse<List<String>>> getHistoricalData(String tableName, String columnName,
                                                                 String startTime, String endTime) {
        Map<String, Object> p = new HashMap<>();
        p.put("tableName", tableName);
        p.put("columnName", columnName);
        p.put("startTime", startTime);
        p.put("endTime", endTime);
        return request(GET_HISTORICALDATA, p, new TypeToken<BaseResponse<List<String>>>() {
        }.getType());
    }
}
