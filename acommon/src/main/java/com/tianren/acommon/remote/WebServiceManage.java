package com.tianren.acommon.remote;

import android.content.Context;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class WebServiceManage {
    private final static String TAG = "WebServiceManage";
    private static boolean inited = false;

    private WebServiceManage() {
    }

    /**
     * 所有网络任务
     */
    private static Map<String, WebTask> webTasks;
    /**
     * 所有接口
     */
    private static Map<Class<?>, BaseWebService> wservice;

    private static Map<String, String> defaultHeadFeild;

    public static Map<String, String> getDefaultHeadFeild() {
        return defaultHeadFeild;
    }

    public static boolean defaultSingleTask = false;

    public static synchronized void init(Context context, WebServiceInfo info) {
        if (!inited) {
            inited = true;
            wservice = new ConcurrentHashMap<>();
            webTasks = new ConcurrentHashMap<>();

            defaultHeadFeild = new ConcurrentHashMap<>();

            for (Class<?> clazz : info.getInterFaceClass()) {
                Object sw = null;
                try {
                    sw = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sw != null && sw instanceof BaseWebService) {
                    BaseWebService webService = (BaseWebService) sw;
                    webService.setContext(context);
                    webService.setDefaultResultHandle(info.getDefaultResultHandle());
                    wservice.put(clazz, webService);
                }

            }
        }
    }

    public static <T extends BaseWebService> T getService(Class<T> clazz) {
        Log.e(TAG, "getService: " + wservice);
        if (wservice.containsKey(clazz)) {
            return (T) wservice.get(clazz);
        } else {
            return null;
        }
    }

    protected static void addTask(String o, WebTask<?> task) {
        webTasks.put(o, task);
    }

    protected static WebTask<?> getTask(String url) {
        return webTasks.get(url);
    }

    protected static void removeTask(String o) {
        WebTask<?> webTask = webTasks.get(o);
        if (webTask != null) {
            webTask.cancelTask();
            webTask.setCallback(null).setXhttpTask(null);
            webTasks.remove(webTask);
        }
    }

    protected static void removeTask(WebTask<?> webTask) {
        if (webTask != null) {
            webTask.cancelTask();
            webTask.setCallback(null).setXhttpTask(null);
            webTasks.remove(webTask);
        }
    }

}
