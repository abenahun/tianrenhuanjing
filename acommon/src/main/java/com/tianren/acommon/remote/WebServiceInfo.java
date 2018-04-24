package com.tianren.acommon.remote;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public abstract class WebServiceInfo {
    public abstract Class<?>[] getInterFaceClass();

    public abstract ResultHandle getDefaultResultHandle();

}
