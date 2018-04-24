package com.tianren.acommon.remote;


import com.tianren.acommon.remote.callback.SCallBack;

import retrofit2.Call;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class WebTask<T> {
    private SCallBack<T> callback;

    public SCallBack<T> getCallback() {
        return callback;
    }

    public WebTask setCallback(SCallBack<T> callback) {
        this.callback = callback;
        return this;
    }

    private Call<T> xhttpTask;

    public WebTask setXhttpTask(Call<T> xhttpTask) {
        this.xhttpTask = xhttpTask;
        return this;
    }

    public void cancelTask() {
        if (xhttpTask != null && !xhttpTask.isCanceled()) {
            xhttpTask.cancel();
            callback = null;
        }
    }
}
