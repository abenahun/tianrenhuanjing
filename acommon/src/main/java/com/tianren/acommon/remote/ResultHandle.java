package com.tianren.acommon.remote;

/**
 * @author Mr.Qiu
 * @date 2018/4/24
 */
public interface ResultHandle {
    <T> String handle(T t);
}
