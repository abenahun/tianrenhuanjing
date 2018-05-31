package com.tianren.acommon;


import com.tianren.acommon.remote.ResultHandle;
import com.tianren.acommon.remote.WebServiceInfo;
import com.tianren.acommon.service.AlarmService;
import com.tianren.acommon.service.DetailedDataService;
import com.tianren.acommon.service.EntryService;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class AppWsInfo extends WebServiceInfo {
    @Override
    public Class<?>[] getInterFaceClass() {
        return new Class<?>[]{
                AlarmService.class,
                EntryService.class,
                DetailedDataService.class
        };
    }

    @Override
    public ResultHandle getDefaultResultHandle() {
        return new ResultHandle() {
            @Override
            public <T> String handle(T t) {
                if (t == null) {
                    return "服务器无法连接,请检查网络..";
                }
                if (t instanceof BaseResponse) {
//                    ResultCheck.checkResult(((BaseResponse) t).getResult(), ((BaseResponse) t).getMessage());
                    return ((BaseResponse) t).getMessage();
                } else {
                    return "返回类型不正确";
                }
            }
        };
    }
}
