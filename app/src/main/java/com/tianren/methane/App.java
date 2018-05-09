package com.tianren.methane;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tianren.acommon.AppWsInfo;
import com.tianren.acommon.remote.WebServiceManage;
import com.tianren.methane.utils.SharedPreferenceUtil;
import com.tianren.methane.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by edianzu on 2017/4/18.
 */

public class App extends Application {
    private static App app;

    public static Context getAppContext() {
        return app;
    }

    private List<Activity> activities;

    public void addActivities(Activity a) {
        activities.add(a);
    }

    public void removeActivities(Activity a) {
        activities.remove(a);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new ArrayList<>();
        Utils.init(this);
        app = this;
        WebServiceManage.init(this, new AppWsInfo());
        SharedPreferenceUtil.init(this);
    }

    public synchronized void closeAllActivity() {
        synchronized (activities) {
            Iterator<Activity> iter = activities.iterator();
            while (iter.hasNext()) {
                Activity activity = iter.next();
                iter.remove();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }
}
