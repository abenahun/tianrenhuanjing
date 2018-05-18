package com.tianren.acommon.service;

import com.google.gson.reflect.TypeToken;
import com.tianren.acommon.BaseResponse;
import com.tianren.acommon.bean.AlarmBean;
import com.tianren.acommon.remote.BaseWebService;
import com.tianren.acommon.remote.WebTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class AlarmService extends BaseWebService {

    /**
     * 处理报警通知
     *
     * @return
     */
    private String HANDLE_ALARM = TIANREN_URL + "alarm/writeDataForApp";

    public WebTask<BaseResponse<Boolean>> handleAlarm(int alarmID, String dealTime, String dealMsg) {
        Map<String, Object> p = new HashMap<>();
        p.put("alarmDealId", alarmID);
        p.put("dealTime", dealTime);
        p.put("dealMsg", dealMsg);
        return request(HANDLE_ALARM, p, new TypeToken<BaseResponse<Boolean>>() {
        }.getType());
    }

    /**
     * 获取报警通知
     *
     * @return
     */
    private String GET_ALARMLISTFORAPP = TIANREN_URL + "alarm/getAlarmListForApp";

    public WebTask<BaseResponse<List<AlarmBean>>> getAlarmListForApp(int isDeal, int page) {
        Map<String, Object> p = new HashMap<>();
        p.put("isDeal", isDeal);
        p.put("pageNum", page);
        return request(GET_ALARMLISTFORAPP, p, new TypeToken<BaseResponse<List<AlarmBean>>>() {
        }.getType());
    }
}
