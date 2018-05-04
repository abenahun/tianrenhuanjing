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
    public WebTask<BaseResponse<List<CapacityBean>>> getCapacity() {
        Map<String, Object> p = new HashMap<>();
        return request(getApi().getCapacity(p), new TypeToken<BaseResponse<List<CapacityBean>>>() {
        }.getType());
    }

    /**
     * 获取收益
     *
     * @return
     */
    public WebTask<BaseResponse<List<ConsumptionBean>>> getConsumptionData() {
        Map<String, Object> p = new HashMap<>();
        return request(getApi().getCapacity(p), new TypeToken<BaseResponse<List<ConsumptionBean>>>() {
        }.getType());
    }
}
