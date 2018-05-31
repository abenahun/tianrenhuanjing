package com.tianren.acommon.remote;

import android.content.Context;
import android.util.Log;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.exception.NovateException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class BaseWebService {
        public static final String BASE_URL = "http://192.168.2.250:8080/";
    public static final String TIANREN_URL = "";
//    public static final String BASE_URL = "http://engineerlee.top:8080/";
//    public static final String TIANREN_URL = "tianren/";
//    public static final String BASE_URL = "http://iot.tianren.com:8080/";

    private static final Integer TIME_OUT = 8;

    protected Context context;
    private Novate novate;

    public void setContext(Context context) {
        this.context = context;
        initNovate();
    }

    private ResultHandle resultHandle;

    public void setDefaultResultHandle(ResultHandle resultHandle) {
        this.resultHandle = resultHandle;
    }

    public BaseWebService() {
    }

    private void initNovate() {
        novate = new Novate.Builder(context)
                .connectTimeout(TIME_OUT)
                .baseUrl(BASE_URL)
                .addLog(true)
                .build();
    }

    public <ResultType> WebTask<ResultType> request(String url, Map<String, Object> o, final Type type) {
        return request(url, o, WebServiceManage.defaultSingleTask, type);
    }

    public <ResultType> WebTask<ResultType> requestUnSingle(String url, Map<String, Object> o, final Type type) {
        return request(url, o, false, type);
    }

    public <ResultType> WebTask<ResultType> request(String url, Map<String, Object> parameters, final boolean singleTask, final Type type) {
        final WebTask<ResultType> webTask = new WebTask();
        if (singleTask) {
            WebServiceManage.removeTask(url);
            WebServiceManage.addTask(url, webTask);
        }
        String rq = "";
        for (String s : parameters.keySet()) {
            rq += (s + ":" + parameters.get(s) + " ");
        }
        Log.e("BaseWebService", "request: " + rq);
        novate.post(url, parameters,
                new BaseSubscriber<ResponseBody>(context) {
                    @Override
                    public void onError(Throwable e) {
                        Throwable throwable = NovateException.handleException(e);
                        if (webTask.getCallback() != null) {
                            webTask.getCallback().callback(false, throwable.getMessage(), null);
                        }
                    }

                    @Override
                    public void onNext(ResponseBody res) {
                        try {
                            String str = new String(res.bytes());
                            ResultType r;
                            r = new JsonResponseParser().fromJson(str, type);
                            String msg = resultHandle.handle(r);
                            if (webTask.getCallback() != null) {
                                webTask.getCallback().callback(true, msg, r);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (singleTask) {
                            WebServiceManage.removeTask(webTask);
                        }
                    }
                });
//        novate.call(o, new BaseSubscriber<ResponseBody>() {
//            @Override
//            public void onNext(ResponseBody res) {
//                try {
//                    String str = new String(res.bytes());
//                    ResultType r;
//                    r = new JsonResponseParser().fromJson(str, type);
//                    String msg = resultHandle.handle(r);
//                    if (webTask.getCallback() != null) {
//                        webTask.getCallback().callback(true, msg, r);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Throwable throwable = NovateException.handleException(e);
//                if (webTask.getCallback() != null) {
//                    webTask.getCallback().callback(false, throwable.getMessage(), null);
//                }
//            }
//
//            @Override
//            public void onCompleted() {
//                if (singleTask) {
//                    WebServiceManage.removeTask(webTask);
//                }
//            }
//        });
        return webTask;
    }
}
