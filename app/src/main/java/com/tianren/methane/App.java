package com.tianren.methane;

import android.app.Application;
import android.content.Context;

import com.blankj.aloglibrary.ALog;
import com.tianren.methane.utils.Utils;

/**
 * Created by edianzu on 2017/4/18.
 */

public class App extends Application {
    private static App app;
    public static Context getAppContext() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        app=this;

        ALog.Builder builder = new ALog.Builder(this);

    }
}
