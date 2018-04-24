package com.tianren.acommon;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public interface InterfaceApi {
    @FormUrlEncoded
    @POST("alarm/writeDataForApp")
    Observable<ResponseBody> handleAlarm(@FieldMap Map<String, Object> maps);
}
