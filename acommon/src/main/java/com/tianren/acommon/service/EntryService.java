package com.tianren.acommon.service;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.CapacityBean;
import com.tianren.acommon.bean.ConsumptionBean;
import com.tianren.acommon.bean.ReportBean;
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
                                                                 String startTime, String endTime, String sort) {
        Map<String, Object> p = new HashMap<>();
        p.put("tableName", tableName);
        p.put("columnName", columnName);
        p.put("startTime", startTime);
        p.put("endTime", endTime);
        p.put("sort", sort);
        Log.e("syl", "历史数据查询参数===》" + p.toString());
        return request(GET_HISTORICALDATA, p, new TypeToken<BaseResponse<List<String>>>() {
        }.getType());
    }

    /**
     * 生产成本录入
     *
     * @return
     */
    private String ENTRYCONSUMPTIONDATA = TIANREN_URL + "entry/entryConsumptionData";

    public WebTask<BaseResponse<Boolean>> entryConsumptionData(String consumption) {
        Map<String, Object> p = new HashMap<>();
        p.put("consumption", consumption);
        return request(ENTRYCONSUMPTIONDATA, p, new TypeToken<BaseResponse<Boolean>>() {
        }.getType());
    }

    /**
     * 录入生产效益
     *
     * @return
     */
    private String ENTRYCAPACITY = TIANREN_URL + "entry/entryCapacity";

    public WebTask<BaseResponse<Boolean>> entryCapacity(String capacity) {
        Map<String, Object> p = new HashMap<>();
        p.put("capacity", capacity);
        return request(ENTRYCAPACITY, p, new TypeToken<BaseResponse<Boolean>>() {
        }.getType());
    }

    /**
     * 智能厌氧反应罐录入
     *
     * @return
     */
    private String ENTRYANAEROBICTANKDATA = TIANREN_URL + "entry/entryAnaerobicTankData";

    public WebTask<BaseResponse<Boolean>> entryAnaerobicTankData(String anaerobicTankData) {
        Map<String, Object> p = new HashMap<>();
        p.put("caanaerobicTankDatapacity", anaerobicTankData);
        return request(ENTRYANAEROBICTANKDATA, p, new TypeToken<BaseResponse<Boolean>>() {
        }.getType());
    }

    /**
     * 获取实验室数据
     *
     * @return
     */
    private String GETANAEROBICTANKDATA = TIANREN_URL + "entry/getAnaerobicTankData";

    public WebTask<BaseResponse<List<Map<String, String>>>> getAnaerobicTankData() {
        Map<String, Object> p = new HashMap<>();
        return request(GETANAEROBICTANKDATA, p, new TypeToken<BaseResponse<List<Map<String, String>>>>() {
        }.getType());
    }

    /**
     * 录入报表数据
     *
     * @return
     */
    private String ENTRYPRODATA = TIANREN_URL + "entry/entryProData";

    public WebTask<BaseResponse<Boolean>> entryProData(String report) {
        Map<String, Object> p = new HashMap<>();
        p.put("report", report);
        return request(ENTRYPRODATA, p, new TypeToken<BaseResponse<Boolean>>() {
        }.getType());
    }

    /**
     * 获取报表数据列表
     *
     * @return
     */
    private String GETPRODATA = TIANREN_URL + "entry/getProData";

    public WebTask<BaseResponse<List<ReportBean>>> getProData(int page) {
        Map<String, Object> p = new HashMap<>();
        p.put("startNum", page);
        p.put("sort", "desc");
        p.put("searchFields", "report_id,report_time");
        return request(GETPRODATA, p, new TypeToken<BaseResponse<List<ReportBean>>>() {
        }.getType());
    }

//    /**
//     * 获取某个报表详细数据
//     *
//     * @return
//     */
//    private String GETPRODATA = TIANREN_URL + "entry/getProData";
//
//    public WebTask<BaseResponse<List<ReportBean>>> getProData(Integer reportId) {
//        Map<String, Object> p = new HashMap<>();
//        p.put("reportId", reportId);
//        return request(GETPRODATA, p, new TypeToken<BaseResponse<List<ReportBean>>>() {
//        }.getType());
//    }

}
