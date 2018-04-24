package com.tianren.acommon.remote.callback;

/**
 * @author Mr.Qiu
 * @date 2018/4/22
 */
public interface SCallBack<T> {
    void callback(boolean isok, String msg, T res);
}
