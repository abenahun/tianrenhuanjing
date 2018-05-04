package com.tianren.acommon.remote;

import android.content.Context;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.exception.NovateException;
import com.tianren.acommon.InterfaceApi;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class BaseWebService {
    private static final String BASE_URL = "http://engineerlee.top:8080/";
    private static final Integer TIME_OUT = 8;

    protected Context context;
    private Novate novate;

    public void setContext(Context context) {
        this.context = context;
        initNovate();
    }

    public InterfaceApi getApi() {
        if (novate == null) {
            initNovate();
        }
        return novate.create(InterfaceApi.class);
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

    public <ResultType> WebTask<ResultType> request(Observable<ResponseBody> o, final Type type) {
        return request(o, WebServiceManage.defaultSingleTask, type);
    }

    public <ResultType> WebTask<ResultType> requestUnSingle(Observable<ResponseBody> o, final Type type) {
        return request(o, false, type);
    }

    public <ResultType> WebTask<ResultType> request(Observable<ResponseBody> o, final boolean singleTask, final Type type) {
        final WebTask<ResultType> webTask = new WebTask();
        if (singleTask) {
            WebServiceManage.removeTask(o);
            WebServiceManage.addTask(o, webTask);
        }
        novate.call(o, new BaseSubscriber<ResponseBody>() {
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
            public void onError(Throwable e) {
                Throwable throwable = NovateException.handleException(e);
                if (webTask.getCallback() != null) {
                    webTask.getCallback().callback(false, throwable.getMessage(), null);
                }
            }

            @Override
            public void onCompleted() {
                if (singleTask) {
                    WebServiceManage.removeTask(webTask);
                }
            }
        });
        return webTask;
    }
}
