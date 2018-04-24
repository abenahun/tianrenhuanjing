package com.tianren.acommon;


import com.tianren.acommon.remote.ResultCheck;
import com.tianren.acommon.remote.ResultHandle;
import com.tianren.acommon.remote.WebServiceInfo;
import com.tianren.acommon.service.AlarmService;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class AppWsInfo extends WebServiceInfo {
    @Override
    public Class<?>[] getInterFaceClass() {
        return new Class<?>[]{
                AlarmService.class
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
                    ResultCheck.checkResult(((BaseResponse) t).getResult(), ((BaseResponse) t).getMessage());
                }
                return "操作成功！";
            }
        };
    }
}
