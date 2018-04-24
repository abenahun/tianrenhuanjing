package com.tianren.acommon.remote.callback;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public interface SProgressCallback<T> extends SCallBack<T> {
    void onProgress(float progress);
}
